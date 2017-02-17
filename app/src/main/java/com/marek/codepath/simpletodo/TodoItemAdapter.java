package com.marek.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.marek.codepath.simpletodo.db.TodoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marek on 2/16/2017.
 */

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemAdapter(Context context, List<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        // Lookup view for data population
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        TextView tvAddedDate = (TextView) convertView.findViewById(R.id.tvAddedDate);
        // Populate the data into the template view using the data object
        tvDescription.setText(item.getDescription());
        tvAddedDate.setText(item.getAddedDate().toString());
        // Return the completed view to render on screen
        return convertView;
    }

}
