package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateBookingActivity extends AppCompatActivity {

    Bundle extras;

    private TextView tvDisplayDate, tvDisplayTime, tvBookingClientName, tvBookingClientAddress, tvBookingClientEmail;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private String clientName, clientAddress, clientEmail, uploadDate, uploadTime;
    private long uploadDateTime;
    Calendar cal;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                addAppointmentsToCalender(this, "Booking", "Booking with: " + clientName, clientAddress, 0, uploadDateTime, true, true, clientName, clientEmail);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

        extras = getIntent().getExtras();

        clientName = extras.getString("clientName");
        clientAddress = extras.getString("clientAddress");
        clientEmail = extras.getString("clientEmail");

        tvDisplayDate = findViewById(R.id.tvBookingCreateDate);
        tvDisplayTime = findViewById(R.id.tvBookingCreateTime);
        tvBookingClientName = findViewById(R.id.tvBookingClientName);
        tvBookingClientAddress = findViewById(R.id.tvBookingClientAddress);
        tvBookingClientEmail = findViewById(R.id.tvBookingClientEmail);

        tvBookingClientName.setText("Client Name: " + clientName);
        tvBookingClientAddress.setText("Client Address: " + clientAddress);
        tvBookingClientEmail.setText("Client Email: " + clientEmail);

        cal = Calendar.getInstance();

        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting current date to set as default when user opens the datePicker

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateBookingActivity.this,
                        android.R.style.Widget_Holo_Light,
                        mDateSetListener,
                        year, month, day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                uploadDate = "";
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                tvDisplayDate.setText(date);
                uploadDate = year + "/" + String.format("%02d", month) + "/" + String.format("%02d", dayOfMonth);

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }
        };

        tvDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting current time to set as default when user opens timePicker

                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CreateBookingActivity.this,
                        android.R.style.Widget_Holo_Light,
                        mTimeSetListener,
                        hour, min, false);
                timePickerDialog.show();
            }
        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                uploadTime = "";
                String time = String.format("%02d",hourOfDay)  + ":" + String.format("%02d", minute);
                tvDisplayTime.setText(time);
                uploadTime = time;

                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
            }
        };



    }

    public void btnUploadBooking(View view) {

        uploadDateTime = cal.getTimeInMillis();

        BookingClass bookingData = new BookingClass(uploadDateTime, clientName, clientAddress, clientEmail);

        if (checkSelfPermission(Manifest.permission.READ_CALENDAR) + checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 123);
        }else{
            addAppointmentsToCalender(this, "Appointment", "Booking with: " + clientName, clientAddress, 0, uploadDateTime, true, true, clientName, clientEmail);
        }

        String currentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Bookings")
                .child(currentDateTime).setValue(bookingData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(CreateBookingActivity.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(CreateBookingActivity.this, MainActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateBookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public long addAppointmentsToCalender(Activity curActivity, String title,
                                          String desc, String place, int status, long startDate,
                                          boolean needReminder, boolean needMailService, String clientName, String clientEmail) {
        long eventID = -1;
        try {
            String eventUriString = "content://com.android.calendar/events";
            ContentValues eventValues = new ContentValues();
            eventValues.put("calendar_id", 1); // id, We need to choose from
            // our mobile for primary its 1
            eventValues.put("title", title);
            eventValues.put("description", desc);
            eventValues.put("eventLocation", place);

            long endDate = startDate + 3600000L; // For next 10min
            eventValues.put("dtstart", startDate);
            eventValues.put("dtend", endDate);

            // values.put("allDay", 1); //If it is birthday alarm or such
            // kind (which should remind me for whole day) 0 for false, 1
            // for true
            eventValues.put("eventStatus", status); // This information is
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):
            eventValues.put("eventTimezone", "UTC/GMT +5:30");
            /*
             * Comment below visibility and transparency column to avoid
             * java.lang.IllegalArgumentException column visibility is invalid
             * error
             */
            // eventValues.put("allDay", 1);
            // eventValues.put("visibility", 0); // visibility to default (0),
            // confidential (1), private
            // (2), or public (3):
            // eventValues.put("transparency", 0); // You can control whether
            // an event consumes time
            // opaque (0) or transparent (1).

            eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

            Uri eventUri = curActivity.getApplicationContext()
                    .getContentResolver()
                    .insert(Uri.parse(eventUriString), eventValues);
            eventID = Long.parseLong(eventUri.getLastPathSegment());

            if (needReminder) {

                String reminderUriString = "content://com.android.calendar/reminders";
                ContentValues reminderValues = new ContentValues();
                reminderValues.put("event_id", eventID);
                reminderValues.put("minutes", 30); // Default value of the
                // system. Minutes is a integer
                reminderValues.put("method", 1); // Alert Methods: Default(0),
                // Alert(1), Email(2),SMS(3)

                Uri reminderUri = curActivity.getApplicationContext()
                        .getContentResolver()
                        .insert(Uri.parse(reminderUriString), reminderValues);
            }


            if (needMailService) {
                ContentValues attendeesValues = new ContentValues();
                attendeesValues.put("event_id", eventID);
                attendeesValues.put("attendeeName", clientName); // Attendees name
                attendeesValues.put("attendeeEmail", clientEmail);// Attendee Email
                attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
                // Relationship_None(0),
                // Organizer(2),
                // Performer(3),
                // Speaker(4)
                attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
                // Required(2),
                // Resource(3)
                attendeesValues.put("attendeeStatus", 0); // NOne(0),
                // Accepted(1),
                // Decline(2),
                // Invited(3),
                // Tentative(4)

                Uri eventsUri = Uri.parse("content://com.android.calendar/attendees");
                Uri url = curActivity.getApplicationContext()
                        .getContentResolver()
                        .insert(eventsUri, attendeesValues);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        startActivity(new Intent(CreateBookingActivity.this, MainActivity.class));

        return eventID;

    }
}