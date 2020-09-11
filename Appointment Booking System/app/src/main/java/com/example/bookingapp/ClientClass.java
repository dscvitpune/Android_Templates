package com.example.bookingapp;

/*Model class for clients*/

public class ClientClass {

    private String clientName, clientAddress, clientEmail;

    public ClientClass(){

    }

    public ClientClass(String clientName, String clientAddress, String clientEmail) {
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.clientEmail = clientEmail;
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
