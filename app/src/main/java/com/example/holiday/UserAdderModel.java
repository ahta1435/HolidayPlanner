package com.example.holiday;

public class UserAdderModel {
    String userId;
    String Username;
    String loginEmail;
     public UserAdderModel(){

     }
    public UserAdderModel(String userId, String username, String loginEmail) {
        this.userId = userId;
        Username = username;
        this.loginEmail = loginEmail;
    }

    public String getId() {
        return userId;
    }

    public String getUsername() {
        return Username;
    }

    public String getMail() {
        return loginEmail;
    }
}
