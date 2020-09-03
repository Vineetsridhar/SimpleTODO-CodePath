package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText editValue;
    Button saveButton;
    String newText;
    int position;
    int prio;

    private void instantiateVariables(){
        editValue = findViewById(R.id.editValue);
        saveButton = findViewById(R.id.saveButton);
        Intent i = getIntent();
        newText = i.getStringExtra(MainActivity.KEY_ITEM_TEXT);
        position = i.getIntExtra(MainActivity.KEY_ITEM_POSITION, -1);
        prio = i.getIntExtra(MainActivity.KEY_ITEM_PRIORITY, -1);
    }

    private void setDefaultValue(){
        editValue.setText(newText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        instantiateVariables();
        setDefaultValue();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MainActivity.KEY_ITEM_TEXT, editValue.getText().toString());
                i.putExtra(MainActivity.KEY_ITEM_POSITION, position);
                i.putExtra(MainActivity.KEY_ITEM_PRIORITY, prio);
                setResult(RESULT_OK, i);

                finish();
            }
        });

    }
}