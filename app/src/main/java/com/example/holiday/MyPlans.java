package com.example.holiday;

public class MyPlans {
    private String starting;
    private String destination;
    private String duration;
    private String budget;
    private String image;
   public MyPlans(){

   }

    public MyPlans(String starting, String destination, String duration, String budget, String image) {
        this.starting = starting;
        this.destination = destination;
        this.duration = duration;
        this.budget = budget;
        this.image = image;
    }

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
