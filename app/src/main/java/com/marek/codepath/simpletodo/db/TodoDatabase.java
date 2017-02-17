package com.marek.codepath.simpletodo.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by marek on 2/14/2017.
 */

@Database(name = TodoDatabase.NAME, version = TodoDatabase.VERSION)
public class TodoDatabase {

    public static final String NAME = "TodoDatabase"; // we will add the .db extension

    public static final int VERSION = 1;
}

