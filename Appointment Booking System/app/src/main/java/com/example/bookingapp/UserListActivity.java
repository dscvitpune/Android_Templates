package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    RecyclerView clientRecyclerView;
    ArrayList<ClientClass> clientList;

    private Query databaseReference;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Menu Code - For testing purposes only

        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.user_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addClient){
            startActivity(new Intent(UserListActivity.this, CreateClientActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        clientRecyclerView = findViewById(R.id.clientRecyclerView);
        clientList = new ArrayList<>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(UserListActivity.this, 1);
        clientRecyclerView.setLayoutManager(gridLayoutManager);

        final ClientAdapter clientAdapter = new ClientAdapter(UserListActivity.this, clientList);
        clientRecyclerView.setAdapter(clientAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Clients").orderByChild("clientName");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientList.clear();

                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()){
                    ClientClass clientData = itemSnapShot.getValue(ClientClass.class);

                    clientList.add(clientData);
                }

                clientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}