package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class EditActivity extends AppCompatActivity {
    EditText editValue;
    Button saveButton;
    String newText;
    NumberPicker numberPicker;
    int position;
    String prio;

    private void instantiateVariables(){
        editValue = findViewById(R.id.editValue);
        saveButton = findViewById(R.id.saveButton);
        numberPicker = findViewById(R.id.numberPicker);
        Intent i = getIntent();
        newText = i.getStringExtra(MainActivity.KEY_ITEM_TEXT);
        position = i.getIntExtra(MainActivity.KEY_ITEM_POSITION, -1);
        prio = i.getStringExtra(MainActivity.KEY_ITEM_PRIORITY);
    }

    private void setDefaultValue(){
        editValue.setText(newText);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setValue(Integer.parseInt(prio));
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
                i.putExtra(MainActivity.KEY_ITEM_PRIORITY, String.valueOf(numberPicker.getValue()));
                setResult(RESULT_OK, i);

                finish();
            }
        });

    }
}