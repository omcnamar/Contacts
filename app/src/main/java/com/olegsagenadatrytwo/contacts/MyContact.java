package com.olegsagenadatrytwo.contacts;

import android.graphics.Bitmap;

/**
 * Created by omcna on 8/7/2017.
 */

public class MyContact {

    private int id;
    private String name;
    private String number;
    private Bitmap image;
    private String address;
    private String email;

    public MyContact(String name, String number, Bitmap image, String address, String email) {
        this.name = name;
        this.number = number;
        this.image = image;
        this.address = address;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "Id: " + id +
                "| Name: " + name +
                "| Number: " + number +
                "| Address: " + address +
                "| Email: " + email;
    }
}
