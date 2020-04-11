package com.example.holiday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveTickets {
    private String age;
    private String gender;
    private String name;
   private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public RetrieveTickets() {
    }

    public RetrieveTickets(int count,String age, String gender, String name) {
        this.count=count;
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

}
