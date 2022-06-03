package com.example.appfororg.domain.mapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.appfororg.R;
import com.example.appfororg.domain.Organization;
import com.example.appfororg.fragment.SignInFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class OrganizationMapper {

    public static Organization organizationFromJson(JSONObject jsonObject, Context context) {
        Organization organization = null;
        try {

            SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("org_photo" + jsonObject.getString("address"),
                    jsonObject.getString("organizationPhoto"));
            editor.commit();
            organization = new Organization(jsonObject.getInt("id"),
                    jsonObject.getString("name"), jsonObject.getString("login")
                    , jsonObject.getString("type"),
                    jsonObject.getString("description"), jsonObject.getString("address"),
                    jsonObject.getString("needs"), jsonObject.getString("linkToWebsite"),
                    jsonObject.getString("password"));
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return organization;
    }
    public static Organization organizationFromChatJson(JSONObject jsonObject, Context context) {
        Organization organization = null;
        try {
            organization = organizationFromJson(jsonObject.getJSONObject("organizationDto"), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return organization;
    }


}