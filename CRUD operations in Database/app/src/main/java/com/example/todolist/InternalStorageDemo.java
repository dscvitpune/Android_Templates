package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InternalStorageDemo extends AppCompatActivity {

    // variables
    public static final String FILE_NAME = "defaultTask.txt";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage_demo);

        // initializing
        editText = findViewById(R.id.edit_text);
    }

    public void save(View v){
        // Retrieving text from edit text
        String text = editText.getText().toString();

        // Initializing a fos object
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE); // Setting filename and mode
            fos.write(text.getBytes()); // writing file
            editText.getText().clear(); // Clearing text after saving it
            Toast.makeText(this, "Task Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fos!=null){
                try {
                    fos.close(); // closing fos
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void load(View v){

        // Initializing fis object
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME); // giving file name
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            // Reading file contents
            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            // Setting text after reading completion
            editText.setText(sb.toString());

            //Toast message
            Toast.makeText(this, "Loaded!!!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fis!=null){
                try {
                    fis.close(); // closing fis
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
