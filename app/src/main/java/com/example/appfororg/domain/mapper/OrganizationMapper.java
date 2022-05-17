package com.example.appfororg.domain.mapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.appfororg.domain.Organization;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class OrganizationMapper {
    public static Organization organizationFromJson(JSONObject jsonObject) {
        Organization organization = null;
        try {
            List<String> stringArrayList = Arrays.asList(
                    jsonObject.getString("organizationPhoto").split(" "));
            byte[] byteArray = new byte[stringArrayList.size()];
            for (int i = 0; i < stringArrayList.size(); i++) {
                byteArray[i] = Byte.valueOf(stringArrayList.get(i));
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            organization = new Organization(jsonObject.getInt("id"),
                    jsonObject.getString("name"), "", jsonObject.getString("type"),
                    bitmap, jsonObject.getString("description"), jsonObject.getString("address"),
                    jsonObject.getString("needs"), jsonObject.getString("linkToWebsite"), ""
                    ) ;
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return organization;
    }
    public static Organization organizationFromChatJson(JSONObject jsonObject) {
        Organization organization = null;
        try {
            organization = organizationFromJson(jsonObject.getJSONObject("organizationDto"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return organization;
    }

}
