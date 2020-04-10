package com.example.holiday;

import java.util.List;

public class AddBooking {
    String PlaneId;
    List<String> gender;
    List<String> Passengers;
    List<String> age;
    int count;
    public AddBooking() {

    }

    public int getCount() {
        return count;
    }

    public String getPlaneId() {
        return PlaneId;
    }

    public List<String> getPassengers() {
        return Passengers;
    }

    public List<String> getAge() {
        return age;
    }

    public List<String> getGender() {
        return gender;
    }

    public AddBooking(String planeId, List<String> gender, List<String> passengers, List<String> age, int count) {
        PlaneId = planeId;
        this.gender = gender;
        Passengers = passengers;
        this.age = age;
        this.count = count;
    }
}
