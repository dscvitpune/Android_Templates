package com.example.todolist.sqlitepackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    // constructor
    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // SQL query to initialize databse
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY, " + Params.KEY_NAME
                + " TEXT, " + Params.KEY_DESCRIPTION + " TEXT" + ")";
        Log.d("db", "Query: " + create);
        sqLiteDatabase.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // function to add task
    public void addTask(Task task){

        // Getting writable database as we want to add task
        SQLiteDatabase db = this.getWritableDatabase();

        // Initializing values object of class ContentValues and storing values in it
        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, task.getName());
        values.put(Params.KEY_DESCRIPTION, task.getDescription());

        // Inserting inside database
        db.insert(Params.TABLE_NAME, null, values);
        Log.d("db", "Successfully inserted!!!");
        db.close(); // closing databse
    }

    // function to retrieve all task
    public List<Task> getAllTasks() {

        // intializing arraylist of type Task
        List<Task> taskList = new ArrayList<>();

        // Getting readable database as we only want to view it
        SQLiteDatabase db = this.getReadableDatabase();

        //Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;

        // Using a cursor object
        Cursor cursor = db.rawQuery(select, null);

        //Looping through it now using the cursor as the end conditon
        if (cursor.moveToFirst()){
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                taskList.add(task);
            } while (cursor.moveToNext());

        }
        return taskList;
    }

    // functioon to update any entry
    public int updateTask(Task task){

        // Getting writable database as we want to update task
        SQLiteDatabase db = this.getWritableDatabase();

        // Initializing values object of class ContentValues and storing values in it
        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, task.getName());
        values.put(Params.KEY_DESCRIPTION, task.getDescription());

        // Updating
        return db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(task.getId())});

    }

    // function to delete task by id
    public void deleteTaskById(int id){
        // Getting writable database as we want to delete task
        SQLiteDatabase db = this.getWritableDatabase();
        // deleting
        db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(id)});
        // closing
        db.close();
    }

    // function to delete task
    public void deleteTask(Task task){
        // Getting writable database as we want to delete task
        SQLiteDatabase db = this.getWritableDatabase();
        // deleting
        db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(task.getId())});
        //closing
        db.close();
    }

    // function to get number of entries in the database
    public int getCount(){
        // SQL query
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        // Getting readable database as we are not changing anything
        SQLiteDatabase db = this.getReadableDatabase();
        // using cursor to iterate the query
        Cursor cursor = db.rawQuery(query, null);
        //return
        return cursor.getCount();
    }

}
