package com.example.holiday;

import com.google.firebase.Timestamp;

public class Planes {
    private String Starting,Destination;
    private int price;
    private String date;
    private Timestamp dateAndtime;
   public Planes(){

   }
    public Planes(String starting, String destination, int price,String date,Timestamp dateAndtime) {
        this.Starting = starting;
        this.Destination = destination;
        this.price = price;
        this.date=date;
        this.dateAndtime=dateAndtime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Timestamp getDateAndTime() {
        return dateAndtime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndtime = dateAndTime;
    }

    public String getStarting() {
        return Starting;
    }

    public void setStarting(String starting) {
        Starting = starting;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
