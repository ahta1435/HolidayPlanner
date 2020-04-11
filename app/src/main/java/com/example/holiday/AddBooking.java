package com.example.holiday;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBooking {
    List<Map<String,Object>>  Passenger;
    String PlaneId;
    String BookingId;
    int count;
   public AddBooking(){

   }

    public AddBooking(String bookingId,List<Map<String, Object>> passenger, String planeId, int count) {
        Passenger = passenger;
        PlaneId = planeId;
        BookingId = bookingId;
        this.count = count;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public List<Map<String, Object>> getPassenger() {
        return Passenger;
    }

    public void setPassenger(List<Map<String, Object>> passenger) {
        Passenger = passenger;
    }

    public String getPlaneId() {
        return PlaneId;
    }

    public void setPlaneId(String planeId) {
        PlaneId = planeId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
