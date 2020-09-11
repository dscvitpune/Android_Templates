package com.example.bookingapp;

/*Model class for bookings*/

public class BookingClass {

    private String clientName, clientAddress, clientEmail;
    private long dateTime;

    public BookingClass() {

    }

    public BookingClass(long dateTime, String clientName, String clientAddress, String clientEmail) {
        this.dateTime = dateTime;

        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientEmail = clientEmail;
    }

    public long getDateTime() {
        return dateTime;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getClientEmail() {
        return clientEmail;
    }
}
