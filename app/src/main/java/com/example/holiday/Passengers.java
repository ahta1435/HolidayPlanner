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
    public  Passengers(){

    }

    public Passengers(String name, String bookingId, String age, String planeId, int count, String gender,String starting,String destination
                                 ,String image) {
        this.name = name;
        this.bookingId = bookingId;
        this.age = age;
        this.planeId = planeId;
        this.count = count;
        this.gender = gender;
        this.starting=starting;
        this.destination=destination;
        this.image=image;
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
