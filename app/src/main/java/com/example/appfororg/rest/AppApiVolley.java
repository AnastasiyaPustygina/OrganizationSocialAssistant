package com.example.appfororg.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfororg.MainActivity;
import com.example.appfororg.OpenHelper;
import com.example.appfororg.domain.Chat;
import com.example.appfororg.domain.Message;
import com.example.appfororg.domain.Organization;
import com.example.appfororg.domain.mapper.ChatMapper;
import com.example.appfororg.domain.mapper.MessageMapper;
import com.example.appfororg.domain.mapper.OrganizationMapper;
import com.example.appfororg.fragment.ChatFragment;
import com.example.appfororg.fragment.ListOfChatsFragment;
import com.example.appfororg.fragment.SignInFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppApiVolley implements AppApi {

    public static final String API_TEST = "API_TEST";
    private final Context context;
    public static final String BASE_URL = "http://192.168.88.19:8081";
    private Response.ErrorListener errorListener;


    public AppApiVolley(Context context) {
        this.context = context;
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(API_TEST, error.toString());
                error.printStackTrace();
            }
        };
    }

    @Override
    public void addOrganization(Organization organization) {
        String url = BASE_URL + "/organization";

        JSONObject params = new JSONObject();
        try {
            params.put("id", organization.getId());
            params.put("name", organization.getName());
            params.put("type", organization.getType());
            SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;

            params.put("organizationPhoto", sharedPreferences.getString("org_photo" +
                    organization.getAddress(), "CANNOT_FIND_ORG_PHOTO_PREF"));
            params.put("description", organization.getDescription());
            params.put("address", organization.getAddress());
            params.put("needs", organization.getNeeds());
            params.put("linkToWebsite", organization.getLinkToWebsite());
        } catch (JSONException e) {
            Log.e("API_TASK", e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                OpenHelper openHelper = new OpenHelper(context,
                        "op", null, OpenHelper.VERSION);
                if (!openHelper.findAllOrganizations().contains(organization))
                    openHelper.insertOrg(organization);
                Log.d(API_TEST, response.toString());
            }
        }, errorListener
        );
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(jsonObjectRequest);
    }


    @Override
    public void updateOrganization(int id, String name, String login, String type,
                                   byte[] photoOrg, String description, String address, String needs,
                                   String linkToWebsite, String pass) {
        String url = BASE_URL + "/organization/" + id;
        Log.e("UPDATE_ORG", "" + id + " " + name + login + type + description + address + needs + linkToWebsite + pass);
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        OpenHelper openHelper = new OpenHelper(context,
                                "op", null, OpenHelper.VERSION);
                        openHelper.changeDescByLog(login, description);
                        openHelper.changeNeedsByLog(login, needs);
                        Log.e("UPDATE_ORG_PHOTO", Arrays.toString(photoOrg));
                        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
                        String photo = sharedPreferences.getString("org_photo" + address,
                                "notOrgPhotoInPref");


                        Log.e("AFTER_UPDATE_ORG_PHOTO", photo);
                    }
                },
                errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("type", type);
                SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
                params.put("organizationPhoto", sharedPreferences.getString("org_photo" +
                        address, "CANNOT_FIND_ORG_PHOTO_PREF"));
                params.put("description", description);
                params.put("address", address);
                params.put("needs", needs);
                params.put("linkToWebsite", linkToWebsite);
                return params;
            }
        };
        referenceQueue.add(stringRequest);


    }

    @Override
    public void fillChats() {
        String url = BASE_URL + "/chat";
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        OpenHelper openHelper = new OpenHelper(context,
                                "op", null, OpenHelper.VERSION);
                        openHelper.deleteAllChat();
                        openHelper.deleteAllPeople();
                        Log.e("API_TEST_FILL_CHAT", response.length() + "");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Chat chat = ChatMapper.chatFromJson(jsonObject, context);
                                openHelper.insertChat(chat);
                                Log.e("FILL CHAT", chat.toString());
                                try {
                                    Log.e("AFTER FILL CHAT", openHelper.findAllChatId().toString());
                                } catch (Exception e) {
                                    Log.e("AFTER FILL CHAT", e.getMessage());
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("API_TEST", e.getMessage());
                        }
                    }
                },
                errorListener);
        referenceQueue.add(jsonArrayRequest);
    }

    @Override
    public void deleteChatById(int id) {
        String url = BASE_URL + "/chat/" + id;
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        fillChats();
                        Log.d(API_TEST, response);
                    }
                },
                errorListener);
        referenceQueue.add(stringRequest);
    }

    @Override
    public void fillMsg() {
        String url = BASE_URL + "/message";
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        OpenHelper openHelper = new OpenHelper(context,
                                "op", null, OpenHelper.VERSION);
                        openHelper.deleteAllMessage();
                        Log.e("API_TEST_FILL_MSG", response.length() + "");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Message message = MessageMapper.messageFromJson(jsonObject, context);
                                openHelper.insertMsg(message);
                                Log.e("FILL MSG", message.toString());
                                try {
                                    Log.e("AFTER FILL MSG", openHelper.findAllMsgVal().toString());
                                } catch (Exception e) {
                                    Log.e("AFTER FILL MSG", e.getMessage());
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("API_TEST", e.getMessage());
                        }
                    }
                },
                errorListener);
        referenceQueue.add(jsonArrayRequest);
    }

    @Override
    public void addMessages(Message message) {
        String url = BASE_URL + "/message";
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API_TEST_ADD_MSG", response);
                    }
                },
                errorListener) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                OpenHelper openHelper = new OpenHelper(
                        context, "op", null, OpenHelper.VERSION);
                Chat chat = openHelper.findChatById(message.getChat_id());

                SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
                params.put("id", message.getId() + "");
                params.put("whose", message.getWhose());
                params.put("value", message.getValues());
                params.put("time", message.getTime());
                params.put("idChat", message.getChat_id() + "");
                params.put("idPerson", chat.getPerson().getId() + "");
                params.put("namePerson", chat.getPerson().getName());

                if (chat.getPerson().getTelephone() != null)
                    params.put("telephonePerson", chat.getPerson().getTelephone());
                else params.put("telephonePerson", "");

                if (chat.getPerson().getEmail() != null)
                    params.put("emailPerson", chat.getPerson().getEmail());
                else params.put("emailPerson", "");

                params.put("cityPerson", chat.getPerson().getCity());
                params.put("photoPerson", sharedPreferences.getString("per_photo" +
                        chat.getPerson().getName(), "notPerPhotoInPref"));
                params.put("dateOfBirthPerson", chat.getPerson().getDateOfBirth());
                params.put("agePerson", chat.getPerson().getAge() + "");
                params.put("idOrganization", chat.getOrganization().getId() + "");
                params.put("nameOrganization", chat.getOrganization().getName());
                params.put("typeOrganization", chat.getOrganization().getType());
                params.put("photoOrganization", sharedPreferences.getString("org_photo" +
                        chat.getOrganization().getAddress(), "notOrgPhotoInPref"));
                params.put("descriptionOrganization", chat.getOrganization().getDescription());
                params.put("addressOrganization", chat.getOrganization().getAddress());
                params.put("needsOrganization", chat.getOrganization().getNeeds());
                params.put("linkToWebsiteOrganization", chat.getOrganization().getLinkToWebsite());
                return params;
            }
        };
        referenceQueue.add(stringRequest);
    }

    @Override
    public void checkNewMsg() {
        String url = BASE_URL + "/message/size";
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int size = Integer.parseInt(response);
                OpenHelper openHelper = new OpenHelper(context, "op",
                        null, OpenHelper.VERSION);
                if(openHelper.findAllMsg().size() != size){
                    fillMsg();

                }
            }
        }, errorListener);
        referenceQueue.add(stringRequest);
    }
}