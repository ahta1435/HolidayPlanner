package com.example.holiday;

public class TripAdderModel {
    String id;
    String Destination;
    String NumberOfPeople;
    String Duration;


    public String getId() {
        return id;
    }
    public TripAdderModel(String id, String destination, String numberOfPeople, String duration) {
        this.id = id;
        Destination = destination;
        NumberOfPeople = numberOfPeople;
        Duration = duration;
    }
    public String getDestination() {
        return Destination;
    }

    public String getNumberOfPeople() {
        return NumberOfPeople;
    }

    public String getDuration() {
        return Duration;
    }
    public TripAdderModel(){

    }
}
