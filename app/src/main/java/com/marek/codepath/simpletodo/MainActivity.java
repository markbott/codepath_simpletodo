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

import com.marek.codepath.simpletodo.db.TodoItem;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.marek.codepath.simpletodo.R.id.etNewItem;

public class MainActivity extends AppCompatActivity {
    List<TodoItem> items;
    TodoItemAdapter itemsAdapter;
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
        itemsAdapter = new TodoItemAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void readItems() {
        items = SQLite.select().from(TodoItem.class).queryList();
    }

    private void writeItems() {
        // todo -- not necessary anymore?
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String newItem = etNewItem.getText().toString();

        TodoItem todo = new TodoItem();
        todo.setDescription(newItem);
        todo.save();

        itemsAdapter.add(todo);

        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                TodoItem todoItem = items.get(pos);
                todoItem.delete();
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent edit = new Intent(MainActivity.this, EditItemActivity.class);
                edit.putExtra(EXTRA_ITEM_IDX, pos);
                edit.putExtra(EXTRA_ITEM_VALUE, items.get(pos).getDescription());
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
            TodoItem todoItem = items.get(idx);
            todoItem.setDescription(data.getStringExtra(EXTRA_ITEM_VALUE));
            todoItem.save();

            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Item updated", Toast.LENGTH_LONG).show();
        }
    }
}
