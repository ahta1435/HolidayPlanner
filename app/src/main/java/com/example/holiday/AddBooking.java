package com.example.holiday;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBooking  {
    private String Name;
    private String bookingId;
    private String Age;
    private String planeId;
    private int Count;
    private String Gender;
    private String starting;
    private String destination;
    private String image;
    private int res;
    public AddBooking(){

    }

    public AddBooking(String name, String bookingId, String age, String planeId, String gender,int count,String starting,String destination,
                                String image,int res) {
        Name = name;
        this.bookingId = bookingId;
        Age = age;
        this.planeId = planeId;
        Gender = gender;
        this.Count=count;
        this.starting=starting;
        this.destination=destination;
        this.image=image;
        this.res=res;
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

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
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

    public String getPlaneId() {
        return planeId;
    }

    public void setPlaneId(String planeId) {
        this.planeId = planeId;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
