package com.example.holiday;

public class HistoryOfJourneyForPlane {
    private String name;
    private String bookingId;
    private String age;
    private String planeId;
    private int count;
    private String gender;
    private String starting;
    private String destination;
    private int res;
    private String image;
    private String date;
    public HistoryOfJourneyForPlane(){

    }

    public HistoryOfJourneyForPlane(String name, String bookingId, String age, String image, int count,
                                    String gender, String starting, String destination
            , int res,  String PlaneId, String date) {
        this.name = name;
        this.bookingId = bookingId;
        this.age = age;
        this.image=image;
        this.planeId = planeId;
        this.count = count;
        this.gender = gender;
        this.starting=starting;
        this.destination=destination;
        this.res=res;
        this.planeId=PlaneId;
        this.date=date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
