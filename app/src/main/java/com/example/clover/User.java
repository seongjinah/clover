package com.example.clover;

public class User {
    private String userEmail;

    User() {
        userEmail = "";
    }

    User(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
