package com.example.appfororg.rest;

import android.graphics.Bitmap;

import com.example.appfororg.domain.Message;
import com.example.appfororg.domain.Organization;

public interface AppApi {
    void addOrganization(Organization organization);
    void updateOrganization(int id, String name, String login, String type,
                            byte[] photoOrg, String description, String address,
                            String needs, String linkToWebsite, String pass);
    void fillChats();
    void deleteChatById(int id);
//    void fillMessageByChatId(int chat_id);
    void fillMsg();
    void addMessages(Message message);

}
