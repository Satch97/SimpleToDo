package com.example.satchinc.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_item);
        String itemName = getIntent().getStringExtra("itemName");
        pos = getIntent().getIntExtra("pos", 0);
        EditText EditItem = (EditText) findViewById(R.id.editText);
        EditItem.setText(itemName);

    }
    public void onSubmit(View v) {
        EditText etName = (EditText) findViewById(R.id.editText);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("name", etName.getText().toString());
        data.putExtra("pos", pos); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
