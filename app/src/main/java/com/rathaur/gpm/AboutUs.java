package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Objects.requireNonNull(getSupportActionBar()).hide();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.about_us_back_pressed);
        back.setOnClickListener(view -> onBackPressed());
    }
}