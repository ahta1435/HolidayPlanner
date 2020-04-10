package com.example.holiday;

public class Planes {
    private String Starting,Destination;
    private int price;
   public Planes(){

   }

    public Planes(String starting, String destination, int price) {
        this.Starting = starting;
        this.Destination = destination;
        this.price = price;
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
