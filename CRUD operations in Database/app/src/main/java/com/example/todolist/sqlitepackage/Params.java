package com.example.todolist.sqlitepackage;

// Parameter class for MyDbHandler

public class Params {

    //initializing some keywords for our table
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "task_db";
    public static final String TABLE_NAME = "tasks_table";

    //keys of our table in db
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "task_name";
    public static final String KEY_DESCRIPTION = "description";

}
