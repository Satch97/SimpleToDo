package com.example.satchinc.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int REQUEST_CODE = 9;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

    }
public void onAddItem(View v){//When user clicks Add New Item
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    String itemText = etNewItem.getText().toString();//find out what etNewItem
    itemsAdapter.add(itemText);
    etNewItem.setText("");
    writeItems();
}
private void setupListViewListener(){
    lvItems.setOnItemLongClickListener(
        new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter,
                                           View item, int pos, long id){//new method for long press
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });//long hold method
    lvItems.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id){


            Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                i.putExtra("itemName",(String) lvItems.getItemAtPosition(pos));//getItema
                i.putExtra("pos", pos);
                startActivityForResult(i,REQUEST_CODE);

        }

    });
    }//end setupListView

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("name");
            int pos = data.getExtras().getInt("pos");
            items.set(pos, name);
            itemsAdapter.notifyDataSetChanged();
            writeItems();

       // }
    }

    private void readItems(){
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try{
        items = new ArrayList<String>(FileUtils.readLines(todoFile));
    }catch(IOException e){
        items = new ArrayList<String>();
    }
}    //end of readItems
private void writeItems(){
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try{
        FileUtils.writeLines(todoFile, items);
    }catch (IOException e){
        e.printStackTrace();
    }
}//end writeItems



}//final close

