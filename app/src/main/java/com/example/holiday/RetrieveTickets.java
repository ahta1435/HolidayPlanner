package com.example.holiday;

import java.util.List;

public class RetrieveTickets {
    List<String> gender;
    List<String> Passengers;
    List<String> age;
    int count;
    public RetrieveTickets() {

    }
    public int getCount() {
        return count;
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
    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public void setPassengers(List<String> passengers) {
        Passengers = passengers;
    }

    public void setAge(List<String> age) {
        this.age = age;
    }
}
