package com.example.holiday;

public class MyPlans {
    private String destination;
    private String duration;
    private String numberOfPeople;
   public MyPlans(){

   }
    public MyPlans(String destination, String duration, String numberOfPeople) {
        this.destination = destination;
        this.duration = duration;
        this.numberOfPeople = numberOfPeople;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
