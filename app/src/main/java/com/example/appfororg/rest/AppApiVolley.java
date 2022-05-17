package com.example.appfororg.rest;

import android.content.Context;
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
import com.example.appfororg.domain.Organization;
import com.example.appfororg.domain.mapper.OrganizationMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AppApiVolley implements AppApi{

    public static final String API_TEST = "API_TEST";
    private final Context context;
    public static final String BASE_URL = "http://192.168.1.45:8081";
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
                    StringBuilder stringBuilder = new StringBuilder();
                    Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(
                            organization.getPhotoOrg(), 0, organization.getPhotoOrg().length),
                            200, 200, false);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
            bitmap.compress(imFor, 0, stream);

                    for (int i = 0; i < stream.toByteArray().length - 1; i++) {
                        stringBuilder.append(String.valueOf(stream.toByteArray()[i])).append(" ");
                    }
                    stringBuilder.append(String.valueOf(
                            stream.toByteArray()[stream.toByteArray().length - 1]));
                    params.put("organizationPhoto", stringBuilder.toString());
                    params.put("description", organization.getDescription());
                    params.put("address", organization.getAddress());
                    params.put("needs", organization.getNeeds());
                    params.put("linkToWebsite", organization.getLinkToWebsite());
            bitmap.recycle();
        } catch (JSONException e) {
            Log.e("API_TASK", e.getMessage());
        }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    OpenHelper openHelper = new OpenHelper(context,
                            "op", null, OpenHelper.VERSION);
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
        String url = BASE_URL + "/organization/" + 15;
        Log.e("iu", ""+id+" "+name+login+type+description+address+needs+linkToWebsite+pass);
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
                        Log.d(API_TEST, response);
                    }
                },
                errorListener){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("type", type);
                StringBuilder stringBuilder = new StringBuilder();
                Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(
                        photoOrg, 0, photoOrg.length),
                        200, 200, false);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
                bitmap.compress(imFor, 0, stream);
                for (int i = 0; i < stream.toByteArray().length - 1; i++) {
                    stringBuilder.append(String.valueOf(stream.toByteArray()[i])).append(" ");
                }
                stringBuilder.append(String.valueOf(
                        stream.toByteArray()[stream.toByteArray().length - 1]));
                params.put("organizationPhoto", stringBuilder.toString());
                params.put("description", description);
                params.put("address", address);
                params.put("needs", needs);
                params.put("linkToWebsite", linkToWebsite);
                return params;
            }
        };
        referenceQueue.add(stringRequest);



    }
}
