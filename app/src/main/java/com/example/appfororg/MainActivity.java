package com.example.appfororg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appfororg.domain.Organization;
import com.example.appfororg.rest.AppApiVolley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AppApiVolley(this).fillChats();
        new AppApiVolley(this).fillMsg();

    }
}