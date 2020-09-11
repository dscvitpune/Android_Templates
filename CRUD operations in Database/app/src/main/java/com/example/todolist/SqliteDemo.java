package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.todolist.adapter.RecyclerViewAdapter;
import com.example.todolist.sqlitepackage.MyDbHandler;
import com.example.todolist.sqlitepackage.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SqliteDemo extends AppCompatActivity{

    // variables
   private RecyclerView recyclerView;
   private RecyclerViewAdapter recyclerViewAdapter;
   private ArrayList<Task> taskArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_demo);

        // creating mydbhandler object
        final MyDbHandler db = new MyDbHandler(SqliteDemo.this);

        //Recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Init recyclerview
        initRecyclerView();

        // setting onclicklistener on fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initializing a dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(SqliteDemo.this);
                View mView = getLayoutInflater().inflate(R.layout.cudialog, null);

                // declaring edit text
                final EditText task_name = mView.findViewById(R.id.d_task);
                final EditText task_description = mView.findViewById(R.id.d_description);

                // setting view
                builder.setView(mView);

                // prevents off screen touches
                builder.setCancelable(false);

                // setting pos btn
                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // retrieving text from edit text
                        String name = task_name.getText().toString();
                        String description = task_description.getText().toString();

                        // creating new task object and adding it
                        Task task = new Task();
                        task.setName(name);
                        task.setDescription(description);
                        db.addTask(task);

                        // resetting
                        initRecyclerView();
                    }
                });

                // setting neg btn
                builder.setNegativeButton("Discard", null);

                //show
                builder.show();
            }
        });
    }

    // init
    public void initRecyclerView() {

        // Creating MyDbHandler object
        MyDbHandler db = new MyDbHandler(SqliteDemo.this);

        // new arraylist
        taskArrayList = new ArrayList<>();

        // storing all values in the tasklist
        final List<Task> taskList = db.getAllTasks();

        // adding it to the arraylist step by step
        for(Task task: taskList){
            taskArrayList.add(task);
        }

        // setting adapter
        recyclerViewAdapter = new RecyclerViewAdapter(SqliteDemo.this, taskArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

    }


}
