package com.example.holiday;

import java.util.List;
import java.util.Map;

public class RetrieveTickets {

    List<Map<String,Object>>  Passenger;
    int count;
    public RetrieveTickets() {

    }

    public RetrieveTickets(List<Map<String, Object>> passenger, int count) {
        Passenger = passenger;
        this.count = count;
    }

    public List<Map<String, Object>> getPassenger() {
        return Passenger;
    }

    public void setPassenger(List<Map<String, Object>> passenger) {
        Passenger = passenger;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
