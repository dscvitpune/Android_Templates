package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // variables
    Button sqliteBtn;
    Button sharedPrefBtn;
    Button intStorageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing
        sqliteBtn = findViewById(R.id.sqlite_btn);
        sharedPrefBtn = findViewById(R.id.sharedpref_btn);
        intStorageBtn = findViewById(R.id.intstorage_btn);

        // passing sqliteIntent on clicking sqlite button
        sqliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sqliteIntent = new Intent(MainActivity.this, SqliteDemo.class);
                startActivity(sqliteIntent);
            }
        });

        // passing sharedprefIntent on clicking sharedpref button
        sharedPrefBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharedPrefIntent = new Intent(MainActivity.this, SharedPreferancesDemo.class);
                startActivity(sharedPrefIntent);
            }
        });

        // passing intstorageIntent on clicking intstorage button
        intStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intStorageIntent = new Intent(MainActivity.this, InternalStorageDemo.class);
                startActivity(intStorageIntent);
            }
        });
    }
}