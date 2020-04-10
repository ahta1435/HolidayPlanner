package com.example.holiday;

public class RetrieveTickets {

    /*List<Map<String,Object>>  Passenger;
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
    }*/

    private String age;
    private String gender;
    private String name;


    public RetrieveTickets() {
    }

    public RetrieveTickets(String age, String gender, String name) {
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
