package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SharedPreferancesDemo extends AppCompatActivity {

    // variables
    Button enterBtn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferances_demo);

        //initializing
        enterBtn = findViewById(R.id.button_enter);
        editText = findViewById(R.id.edit_text);

        // Calling oon click listener
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // retrieving text from edit text and saving it to msg
                String msg = editText.getText().toString();

                // Setting name and mode
                SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);

                // Creating an editor
                final SharedPreferences.Editor editor = shrd.edit();

                // Putting string and then applying
                editor.putString("str", msg);
                editor.apply();

                // Keeping the text for display as it is
                editText.setText(msg);

                // Toast message
                Toast.makeText(SharedPreferancesDemo.this, "Text is Entered!!!", Toast.LENGTH_SHORT).show();
            }
        });


        // Get the value back, setting name and mode
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);

        // getting string back and setting the value
        String value = getShared.getString("str", "");
        editText.setText(value);

    }
}