package com.example.appfororg.domain;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.appfororg.fragment.SignInFragment;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class Person {
    private int id;
    private String telephone;
    private String email;
    private String name;
    private byte[] photoPer;
    private int age;
    private String dateOfBirth;
    private String city;

    public Person(int id, String data, String name,
                  int age, String dateOfBirth, String city) {
        this.id = id;
        if(data.contains("@")) this.email = data;
        else this.telephone = data;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("IsPrefNull", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("per_photo" + name, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            byteArray[i] = Byte.parseByte(stringArrayList.get(i));
        }
        this.photoPer = byteArray;

        Log.e("PERSON_PHOTO_IS_NULL", (photoPer == null) + "");
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
    }

    public Person(String data, String name, int age, String dateOfBirth,
                  String city) {
        if(data.contains("@")) this.email = data;
        else this.telephone = data;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("IsPrefNull", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("per_photo" + name, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            byteArray[i] = Byte.parseByte(stringArrayList.get(i));
        }
        this.photoPer = byteArray;

        Log.e("PERSON_PHOTO_IS_NULL", (photoPer == null) + "");
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
