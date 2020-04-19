package com.example.holiday;

public class Buses {
    private String starting,destination;
    private int price;
    public Buses(){

    }

    public Buses(String starting, String destination, int price) {
        this.starting = starting;
        this.destination = destination;
        this.price = price;
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
