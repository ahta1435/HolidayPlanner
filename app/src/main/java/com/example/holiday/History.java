package com.example.holiday;

public class History {
    private String name;
    private String bookingId;
    private String age;
    private String planeId;
    private int count;
    private String busId;
    private String gender;
    private String starting;
    private String destination;
    private int res;
    private String image;
    private String seats;
    private String SeatId;
    private String date;
    private String BusId;
    public History(){

    }
    public History(String name, String bookingId, String age, String planeId, int count, String busId, String gender, String starting,
                   String destination, int res, String image, String seats, String seatId, String date, String busId1) {
        this.name = name;
        this.bookingId = bookingId;
        this.age = age;
        this.planeId = planeId;
        this.count = count;
        this.busId = busId;
        this.gender = gender;
        this.starting = starting;
        this.destination = destination;
        this.res = res;
        this.image = image;
        this.seats = seats;
        SeatId = seatId;
        this.date = date;
        BusId = busId1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlaneId() {
        return planeId;
    }

    public void setPlaneId(String planeId) {
        this.planeId = planeId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getSeatId() {
        return SeatId;
    }

    public void setSeatId(String seatId) {
        SeatId = seatId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
