package com.example.todolist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.SqliteDemo;
import com.example.todolist.sqlitepackage.MyDbHandler;
import com.example.todolist.sqlitepackage.Task;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    // variables
    private Context context;
    private List<Task> taskList;

    // constructor
    public RecyclerViewAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    // on create view holder
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_layout, parent, false);
        return new ViewHolder(view);
    }

    // on bind view holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        // variable of type Task
        final Task task = taskList.get(position);

        // setting text
        holder.taskName.setText(task.getName());
        holder.taskDescription.setText(task.getDescription());

        // setting on click listener
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // setting up new dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Do you want to delete " + task.getName() + " ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // deleting that task
                        MyDbHandler db = new MyDbHandler(context);
                        db.deleteTaskById(task.getId());

                        // reseting entries
                        ((SqliteDemo)context).initRecyclerView();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

        Log.d("check","BindHolder");
    }


    // gets count
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // implementing on click listener
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // variables
        public TextView taskName;
        public TextView taskDescription;
        public ImageButton delete_btn;

        // simple viewholder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            taskName = itemView.findViewById(R.id.text_taskname);
            taskDescription = itemView.findViewById(R.id.text_taskdescription);
            delete_btn = itemView.findViewById(R.id.btn_delete);
        }

        // onclick function
        @Override
        public void onClick(View view) {

            // gets position and then uses it to get corresponding task
            final int position = this.getAdapterPosition();
            final Task task = taskList.get(position);

            // new dialog box
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mView = inflater.inflate(R.layout.cudialog, null);
            final EditText task_name = mView.findViewById(R.id.d_task);
            final EditText task_description = mView.findViewById(R.id.d_description);

            // setting text
            task_name.setText(task.getName());
            task_description.setText(task.getDescription());

            builder.setView(mView);
            builder.setCancelable(false);

            // pos btn
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    // retrieving text
                    String name = task_name.getText().toString();
                    String description = task_description.getText().toString();

                    // updating
                    MyDbHandler db = new MyDbHandler(context);
                    task.setId(task.getId());
                    task.setName(name);
                    task.setDescription(description);

                    // returns number of values changed, here, it will return only one
                    int affectedRows = db.updateTask(task);

                    // resetting recycler view
                    ((SqliteDemo)context).initRecyclerView();

                }
            });
            builder.setNegativeButton("Discard", null);
            builder.show();
        }

    }


}