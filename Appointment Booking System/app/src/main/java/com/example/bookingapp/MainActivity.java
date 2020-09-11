package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<BookingClass> bookingList;
    BookingClass bookingData;
    TextView tvNumOfBookings;

    private Query databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        tvNumOfBookings = findViewById(R.id.numOfBookings);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        bookingList = new ArrayList<>();

        final BookingAdapter adapter = new BookingAdapter(MainActivity.this, bookingList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Bookings").orderByChild("dateTime");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingList.clear();

                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()){
                    BookingClass bookingData = itemSnapShot.getValue(BookingClass.class);

                    bookingList.add(bookingData);
                }

                adapter.notifyDataSetChanged();
                tvNumOfBookings.setText("Number of Bookings: " + bookingList.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void newBooking(View view) {
        //fab
        startActivity(new Intent(MainActivity.this, UserListActivity.class));
    }
}