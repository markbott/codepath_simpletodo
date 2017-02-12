package com.marek.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.marek.codepath.simpletodo.R.id.etNewItem;

public class EditItemActivity extends AppCompatActivity {
    EditText etItemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText)findViewById(R.id.etItemValue);
        etItemValue.setText(getIntent().getStringExtra(MainActivity.EXTRA_ITEM_VALUE));
    }

    public void onSave(View v) {
        String newValue = etItemValue.getText().toString();

        Intent data = new Intent();
        data.putExtra(MainActivity.EXTRA_ITEM_IDX, getIntent().getIntExtra(MainActivity.EXTRA_ITEM_IDX, -1));
        data.putExtra(MainActivity.EXTRA_ITEM_VALUE, newValue);
        setResult(RESULT_OK, data);
        finish();
    }
}
