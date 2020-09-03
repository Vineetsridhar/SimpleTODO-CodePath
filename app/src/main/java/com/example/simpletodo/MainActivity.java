package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ListItem> items = new ArrayList<>();
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;
    NumberPicker numberPicker;

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final String KEY_ITEM_PRIORITY = "item_prio";
    public static final int RESULT_CODE = 20;


    private void instantiateVariables() {
        //Link to XML
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);
        numberPicker = findViewById(R.id.numberPicker);

        loadItems();
    }

    private void populateList() {
        ItemsAdapter.ListListener listListener = new ItemsAdapter.ListListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), R.string.itemRemoved, Toast.LENGTH_SHORT).show();
                saveItems();
            }

            @Override
            public void onItemClicked(int position) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position).getText());
                i.putExtra(KEY_ITEM_POSITION, position);
                i.putExtra(KEY_ITEM_PRIORITY, items.get(position).getPrio());
                startActivityForResult(i, RESULT_CODE);
            }
        };
        //Link view to adapter
        itemsAdapter = new ItemsAdapter(items, listListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etItem.getText().toString();
                int prio = numberPicker.getValue();
                ListItem listItem = new ListItem(text, String.valueOf(prio));
                items.add(listItem);
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), R.string.itemAdded, Toast.LENGTH_SHORT).show();
                numberPicker.setValue(1);
                saveItems();
            }
        });
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiateVariables();
        populateList();
        setListeners();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null)
            return;
        int position = data.getIntExtra(KEY_ITEM_POSITION, -1);
        String priority = data.getStringExtra(KEY_ITEM_PRIORITY);
        String text = data.getStringExtra(KEY_ITEM_TEXT);
        Log.e("post", String.valueOf(position));
        if ( position >= 0) {
            items.set(position, new ListItem(text, priority));
            itemsAdapter.notifyItemChanged(position);
            saveItems();
            Toast.makeText(getApplicationContext(), R.string.itemChanged, Toast.LENGTH_SHORT).show();
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems() {
        try {
            ArrayList<String> temp = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            for(int i = 0; i < temp.size(); i++){
                items.add(new ListItem(temp.get(i), "1"));
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}