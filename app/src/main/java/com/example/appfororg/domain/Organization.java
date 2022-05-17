package com.example.appfororg.domain;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

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

    public Organization(String name, String login, String type, Bitmap photoOrg,
                        String description, String address,
                        String needs, String linkToWebsite, String pass) {
        this.name = name;
        this.type = type;
        this.login = login;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
        photoOrg.compress(imFor, 0, stream);
        this.photoOrg = stream.toByteArray();
        photoOrg.recycle();
        this.description = description;
        this.address = address;
        this.needs = needs;
        this.linkToWebsite = linkToWebsite;
        this.pass = pass;
    }

    public Organization(String name, String login, String type, Bitmap photoOrg,
                        String description, String address, String needs, String pass) {
        this.name = name;
        this.type = type;
        this.login = login;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
        if(photoOrg!=null)
        photoOrg.compress(imFor, 0, stream);
        this.photoOrg = stream.toByteArray();
        if(photoOrg!=null)
        photoOrg.recycle();
        this.description = description;
        this.address = address;
        this.needs = needs;
        this.pass = pass;
    }

    public Organization(int id, String name, String login, String type,
                        Bitmap photoOrg, String description, String address,
                        String needs, String linkToWebsite, String pass) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.login = login;
        if(photoOrg != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
            photoOrg.compress(imFor, 0, stream);
            this.photoOrg = stream.toByteArray();
            photoOrg.recycle();
        }
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
}
