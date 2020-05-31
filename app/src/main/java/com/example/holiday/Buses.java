package com.example.holiday;

import com.google.firebase.Timestamp;

public class Buses {
    private String starting,destination;
    private int price;
    private String time;
    private Timestamp dateAndTime;
    public Buses(){

    }

    public Buses(String starting, String destination, int price,String time,Timestamp dateAndTime) {
        this.starting = starting;
        this.destination = destination;
        this.price = price;
        this.time=time;
        this.dateAndTime=dateAndTime;
    }

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getStarting() {
        return starting;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
