package com.example.holiday;

public class AddBusBooking {
    private String Name;
    private String bookingId;
    private String Age;
    private String BusId;
    private int Count;
    private String Gender;
    private String starting;
    private String destination;
    private String image;
    private String seats;
    private int res;
    private String SeatId;
    private String date;
    public AddBusBooking(){

    }
    public AddBusBooking(String name, String bookingId, String age,
                         String busId, int count, String gender, String starting,
                         String destination, String image, String seats,int res,String SeatId,String date) {
        Name = name;
        this.bookingId = bookingId;
        Age = age;
        BusId = busId;
        Count = count;
        Gender = gender;
        this.starting = starting;
        this.destination = destination;
        this.image = image;
        this.seats = seats;
        this.res=res;
        this.SeatId=SeatId;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeatId() {
        return SeatId;
    }

    public void setSeatId(String seatId) {
        SeatId = seatId;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBusId() {
        return BusId;
    }

    public void setBusId(String busId) {
        BusId = busId;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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
}
