package com.example.appfororg.domain;

import android.content.SharedPreferences;

import com.example.appfororg.fragment.SignInFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("per_photo" + name, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            byteArray[i] = Byte.parseByte(stringArrayList.get(i));
        }
        this.photoPer = byteArray;

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
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("per_photo" + name, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            byteArray[i] = Byte.parseByte(stringArrayList.get(i));
        }
        this.photoPer = byteArray;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(telephone, person.telephone) && Objects.equals(email, person.email) && Objects.equals(name, person.name) && Arrays.equals(photoPer, person.photoPer) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(city, person.city);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(telephone, email, name, age, dateOfBirth, city);
        result = 31 * result + Arrays.hashCode(photoPer);
        return result;
    }
}
