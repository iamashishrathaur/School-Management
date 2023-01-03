package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StudentEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_events);
        getSupportActionBar().hide();
    }
}