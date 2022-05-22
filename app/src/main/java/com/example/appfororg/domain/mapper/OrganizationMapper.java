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
//            List<String> stringArrayList = Arrays.asList(
//                    jsonObject.getString("organizationPhoto").split(" "));
//            byte[] byteArray = new byte[stringArrayList.size()];
//            try {
//                for (int i = 0; i < stringArrayList.size(); i++) {
//                    byteArray[i] = Byte.valueOf(stringArrayList.get(i));
//                }
//            }catch (Exception e){
//                Bitmap photoOrg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_channel);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
//                photoOrg.compress(imFor, 0, stream);
//                byteArray = stream.toByteArray();
//
//            }
//            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("org_photo" + jsonObject.getString("address"),
                    jsonObject.getString("organizationPhoto"));
            editor.commit();
            organization = new Organization(jsonObject.getInt("id"),
                    jsonObject.getString("name"), "", jsonObject.getString("type"),
                    jsonObject.getString("description"), jsonObject.getString("address"),
                    jsonObject.getString("needs"), jsonObject.getString("linkToWebsite"), ""
                    ) ;
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
