package com.marek.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.marek.codepath.simpletodo.R.id.etNewItem;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    private static final int REQUEST_CODE_SAVE = 1;

    public static final String EXTRA_ITEM_IDX = "itemIdx";
    public static final String EXTRA_ITEM_VALUE = "itemValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);

        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void readItems() {
        File todoFile = new File(getFilesDir(), "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch(IOException ioe) {
            items = new ArrayList<>();
            ioe.printStackTrace();
        }
    }

    private void writeItems() {
        File todoFile = new File(getFilesDir(), "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String newItem = etNewItem.getText().toString();
        itemsAdapter.add(newItem);
        writeItems();
        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent edit = new Intent(MainActivity.this, EditItemActivity.class);
                edit.putExtra(EXTRA_ITEM_IDX, pos);
                edit.putExtra(EXTRA_ITEM_VALUE, items.get(pos));
                startActivityForResult(edit, REQUEST_CODE_SAVE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode != REQUEST_CODE_SAVE) {
            // log?
            return;
        }

        if(resultCode == RESULT_OK) {
            int idx = data.getIntExtra(EXTRA_ITEM_IDX, -1);
            if(idx < 0) {
                // log?
                return;
            }
            items.remove(idx);
            items.add(idx, data.getStringExtra(EXTRA_ITEM_VALUE));
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            Toast.makeText(MainActivity.this, "Item updated", Toast.LENGTH_LONG).show();
        }
    }
}
