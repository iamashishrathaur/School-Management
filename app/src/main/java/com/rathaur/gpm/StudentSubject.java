package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StudentSubject extends AppCompatActivity {
ImageView backPressed;
LinearLayout first,second,third;
TextView year_name;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject);
        getSupportActionBar().hide();
        first=findViewById(R.id.first_year_subject);
        second=findViewById(R.id.second_year_subject);
        third=findViewById(R.id.third_year_subject);
        year_name=findViewById(R.id.year_name);
        first.setVisibility(View.GONE);
        second.setVisibility(View.GONE);
        third.setVisibility(View.GONE);
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        String year=sharedPreferences.getString("syear","");
        if (year.equals("1st Year")){
            year_name.setText("First Year");
            first.setVisibility(View.VISIBLE);
        }
        else if (year.equals("2nd Year")){
            year_name.setText("Second Year");
            second.setVisibility(View.VISIBLE);
        }
        else{
            year_name.setText("Third Year");
            third.setVisibility(View.VISIBLE);
        }

    }
}