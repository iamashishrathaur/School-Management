package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Objects;

public class TeacherEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_events);
        Objects.requireNonNull(getSupportActionBar()).hide();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.teacher_event_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}