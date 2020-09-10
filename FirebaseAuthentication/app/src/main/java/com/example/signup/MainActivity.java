//To choose to register or login
package com.example.signup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button_register is the variable for the button to Register
        Button buttonRG = findViewById(R.id.button_register);
        buttonRG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RG();
            }
        });
        //button_login is the variable for the button to Login
        Button buttonLG =findViewById(R.id.button_login);
        buttonLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LG();
            }
        });
    }
    private void RG() {
        //To start the activity Register
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
    private void LG(){
        //To start the activity Login
        Intent intent = new Intent(this, Login.class );
        startActivity(intent);
    }
}

