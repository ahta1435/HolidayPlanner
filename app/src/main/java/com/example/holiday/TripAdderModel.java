package com.example.holiday;

public class TripAdderModel {
    String id;
    String starting;
    String destination;
    String duration;
    String budget;
    String image;
    public  TripAdderModel(){

    }


    public TripAdderModel(String id,  String starting, String destination, String duration, String budget, String image) {
        this.id = id;
        this.starting = starting;
        this.destination = destination;
        this.duration = duration;
        this.budget = budget;
        this.image = image;
    }
    public String getId() {
        return id;
    }

    public String getStarting() {
        return starting;
    }

    public String getDestination() {
        return destination;
    }

    public String getDuration() {
        return duration;
    }

    public String getBudget() {
        return budget;
    }

    public String getImage() {
        return image;
    }

}
