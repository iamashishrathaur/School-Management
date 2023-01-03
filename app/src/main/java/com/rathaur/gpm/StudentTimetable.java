package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;

public class StudentTimetable extends AppCompatActivity {
 View first,second,third;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable);
        first=findViewById(R.id.first_year_time_table);
        second=findViewById(R.id.second_year_timetable);
        third=findViewById(R.id.third_year_timetable);
        getSupportActionBar().hide();
        first.setVisibility(View.VISIBLE);
        back=findViewById(R.id.timetable_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}