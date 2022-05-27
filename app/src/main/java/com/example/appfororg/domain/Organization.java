package com.example.appfororg.domain;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.appfororg.fragment.SignInFragment;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private int id;
    private String name;
    private String pass;
    private String login;
    private String type;
    private byte[] photoOrg;
    private String description;
    private String address;
    private String needs;
    private String linkToWebsite;

    public Organization(String name, String login, String type,
                        String description, String address,
                        String needs, String linkToWebsite, String pass) {
        this.name = name;
        this.type = type;
        this.login = login;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("org_photo" + address, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            byteArray[i] = Byte.parseByte(stringArrayList.get(i));
        }
        this.photoOrg = byteArray;

        this.description = description;
        this.address = address;
        this.needs = needs;
        this.linkToWebsite = linkToWebsite;
        this.pass = pass;
    }

    public Organization(String name, String login, String type,
                        String description, String address, String needs, String pass) {
        this.name = name;
        this.type = type;
        this.login = login;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("org_photo" + address, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            try {
                byteArray[i] = Byte.parseByte(stringArrayList.get(i));
            }catch(Exception e){
                byteArray[i] = 0;
            }
        }
        this.photoOrg = byteArray;

        this.description = description;
        this.address = address;
        this.needs = needs;
        this.pass = pass;
    }

    public Organization(int id, String name, String login, String type,
                         String description, String address,
                        String needs, String linkToWebsite, String pass) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.login = login;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("org_photo" + address, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            try {
                byteArray[i] = Byte.parseByte(stringArrayList.get(i));
            }catch(Exception e){
                byteArray[i] = 0;
            }
        }
        this.photoOrg = byteArray;

        this.description = description;
        this.address = address;
        this.needs = needs;
        this.linkToWebsite = linkToWebsite;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public byte[] getPhotoOrg() {
        return photoOrg;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getNeeds() {
        return needs;
    }

    public String getLinkToWebsite() {
        return linkToWebsite;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {

        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", login='" + login + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", needs='" + needs + '\'' +
                ", linkToWebsite='" + linkToWebsite + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(pass, that.pass) &&
                Objects.equals(login, that.login) && Objects.equals(type, that.type) &&
                Arrays.equals(photoOrg, that.photoOrg) && Objects.equals(description, that.description)
                && Objects.equals(address, that.address) && Objects.equals(needs, that.needs) &&
                Objects.equals(linkToWebsite, that.linkToWebsite);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, pass, login, type, description, address, needs, linkToWebsite);
        result = 31 * result + Arrays.hashCode(photoOrg);
        return result;
    }

}
