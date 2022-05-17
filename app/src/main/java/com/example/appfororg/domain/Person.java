package com.example.appfororg.domain;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class Person {
    private int id;
    private String telephone;
    private String email;
    private String name;
    private byte[] photoPer;
    private int age;
    private String dateOfBirth;
    private String city;

    public Person(int id, String data, String name,  Bitmap photoPer,
                  int age, String dateOfBirth, String city) {
        this.id = id;
        if(data.contains("@")) this.email = data;
        else this.telephone = data;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
        photoPer.compress(imFor, 0, stream);
        this.photoPer = stream.toByteArray();
        photoPer.recycle();
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
    }

    public Person(String data, String name, int age, Bitmap photoPer, String dateOfBirth,
                  String city) {
        if(data.contains("@")) this.email = data;
        else this.telephone = data;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
        photoPer.compress(imFor, 0, stream);
        this.photoPer = stream.toByteArray();
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
    }

    public int getId() {
        return id;
    }
    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public byte[] getPhotoPer() {
        return photoPer;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
