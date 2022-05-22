package com.example.appfororg.domain.mapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.appfororg.R;
import com.example.appfororg.domain.Person;
import com.example.appfororg.fragment.SignInFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PersonMapper {
    public static Person personFromJson(JSONObject jsonObject, Context context) {
        Person person = null;
        try {
            String data = jsonObject.getString("telephone").isEmpty() ?
                    jsonObject.getString("email") : jsonObject.getString("telephone");
//            List<String> stringArrayList = Arrays.asList(
//                    jsonObject.getString("photo").split(" "));
//            byte[] byteArray = new byte[stringArrayList.size()];
//            try {
//                for (int i = 0; i < stringArrayList.size(); i++) {
//                    byteArray[i] = Byte.valueOf(stringArrayList.get(i));
//                }
//            }catch (Exception e){
//                Bitmap photoPer = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_channel);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
//                photoPer.compress(imFor, 0, stream);
//                byteArray = stream.toByteArray();
//
//            }
//            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("per_photo" + jsonObject.getString("name"),
                    jsonObject.getString("photo"));
            editor.commit();
            person = new Person(jsonObject.getInt("id"), data,
                    jsonObject.getString("name"), jsonObject.getInt("age"),
                    jsonObject.getString("date_of_birth"),
                    jsonObject.getString("city"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static Person personFromChatJson(JSONObject jsonObject, Context context) {
        Person person = null;
        try {
            person = personFromJson(jsonObject.getJSONObject("personDto"), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }
}
