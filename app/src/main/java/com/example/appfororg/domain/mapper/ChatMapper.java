package com.example.appfororg.domain.mapper;

import android.content.Context;

import com.example.appfororg.OpenHelper;
import com.example.appfororg.domain.Chat;
import com.example.appfororg.domain.Person;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatMapper {
    public static Chat chatFromJson(JSONObject jsonObject, Context context) {
        Chat chat = null;
        Person person  =PersonMapper.personFromChatJson(jsonObject, context);
        OpenHelper openHelper = new OpenHelper(context,
                "op", null, OpenHelper.VERSION);
        openHelper.insertPerson(person);
        try {
            chat = new Chat(jsonObject.getInt("id"),
                    person,
                    OrganizationMapper.organizationFromChatJson(jsonObject, context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chat;
    }
    public static Chat chatFromMsgJson(JSONObject jsonObject, Context context) {
        Chat chat = null;
        try {
            chat = chatFromJson(jsonObject.getJSONObject("chatDto"), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chat;
    }

}
