package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StudentAddNewStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_new_student);
        getSupportActionBar().hide();

    }
}