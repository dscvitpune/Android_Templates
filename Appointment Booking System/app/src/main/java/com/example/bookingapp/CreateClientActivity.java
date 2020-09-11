package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateClientActivity extends AppCompatActivity {

    String clientName, clientAddress, clientEmail;
    EditText etClientName, etClientAddress, etClientEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

        etClientName = findViewById(R.id.editTextClientName);
        etClientAddress = findViewById(R.id.editTextClientAddress);
        etClientEmail = findViewById(R.id.editTextClientEmail);

    }

    public void btnUploadClient(View view) {

        clientName = etClientName.getText().toString();
        clientAddress = etClientAddress.getText().toString();
        clientEmail = etClientEmail.getText().toString();

        String currentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        ClientClass clientData =  new ClientClass(clientName, clientAddress, clientEmail);

        //Uploading data under child of current date and time

        FirebaseDatabase.getInstance().getReference("Clients")
                .child(currentDateTime).setValue(clientData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(CreateClientActivity.this, "Client Created", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateClientActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}