package com.example.holiday;

import java.util.List;

public class BookedSeats {
    private List<String> Seat;
    public BookedSeats(){

    }

    public BookedSeats(List<String> seat) {
        Seat = seat;
    }

    public List<String> getSeat() {
        return Seat;
    }

    public void setSeat(List<String> seat) {
        Seat = seat;
    }
}
