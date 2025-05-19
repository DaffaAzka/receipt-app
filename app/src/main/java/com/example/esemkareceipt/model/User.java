package com.example.esemkareceipt.model;

import java.util.Date;

public class User {

    Integer id;
    String username;
    String fullName;
    String image;
    String password;
    Date dateOfBirth;

    public User(Integer id, String username, String fullName, String image, String password) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.image = image;
        this.password = password;
//        this.dateOfBirth = dateOfBirth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", image='" + image + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
