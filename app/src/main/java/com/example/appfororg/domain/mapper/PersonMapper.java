package com.example.appfororg.domain.mapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.appfororg.domain.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PersonMapper {
    public static Person personFromJson(JSONObject jsonObject) {
        Person person = null;
        try {
            String data = jsonObject.getString("telephone").isEmpty() ?
                    jsonObject.getString("email") : jsonObject.getString("telephone");
            List<String> stringArrayList = Arrays.asList(
                    jsonObject.getString("photo").split(" "));
            byte[] byteArray = new byte[stringArrayList.size()];
            for (int i = 0; i < stringArrayList.size(); i++) {
                byteArray[i] = Byte.valueOf(stringArrayList.get(i));
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            person = new Person(jsonObject.getInt("id"), data,
                    jsonObject.getString("name"), bitmap, jsonObject.getInt("age"),
                    jsonObject.getString("date_of_birth"),
                    jsonObject.getString("city"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static Person personFromChatJson(JSONObject jsonObject) {
        Person person = null;
        try {
            person = personFromJson(jsonObject.getJSONObject("personDto"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }
}
