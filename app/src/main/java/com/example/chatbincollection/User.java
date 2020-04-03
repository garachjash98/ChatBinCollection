package com.example.chatbincollection;

/**
 * Created by Belal on 4/15/2018.
 */

public class User {
    private String name, email;

    public User(){

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getStudentName() {

        return name;
    }

    public void setStudentName(String name) {

        this.name = name;
    }

    public String getStudentEmail() {

        return email;
    }

    public void setStudentEmail(String email) {

        this.email = email;
    }
}
