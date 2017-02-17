package com.marek.codepath.simpletodo.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by marek on 2/14/2017.
 */

@Table(database = TodoDatabase.class)
public class TodoItem extends BaseModel implements Serializable {

    public TodoItem() {
        added = new Date();
    }

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    //@Unique
    Date added;

    @Column
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedDate() { return added; }
}