package com.example.holiday;
public class Passengers {
    private String name;
    private String bookingId;
    private String age;
    private String planeId;
    private int count;
    private String gender;
    private String starting;
    private String destination;
    private String image;
    private int res;
    private String seats;
    private String SeatId;
    private String BusId;
    public  Passengers(){

    }

    public Passengers(String name, String bookingId, String age, String planeId, int count, String gender,String starting,String destination
                                 ,String image,int res,String seats,String SeatId,String BusId) {
        this.name = name;
        this.bookingId = bookingId;
        this.age = age;
        this.planeId = planeId;
        this.count = count;
        this.gender = gender;
        this.starting=starting;
        this.destination=destination;
        this.image=image;
        this.res=res;
        this.seats=seats;
        this.SeatId=SeatId;
        this.BusId=BusId;
    }

    public String getBusId() {
        return BusId;
    }

    public void setBusId(String busId) {
        BusId = busId;
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

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
