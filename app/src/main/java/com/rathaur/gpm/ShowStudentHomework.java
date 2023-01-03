package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.HomeworkAdepter;
import com.rathaur.gpm.DataBase.Homework;
import com.rathaur.gpm.DataBase.Student;

import java.util.ArrayList;

public class ShowStudentHomework extends AppCompatActivity {
  HomeworkAdepter adepter;
  String senrollment;
  RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_homework);
        getSupportActionBar().hide();
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        senrollment=sharedPreferences.getString("senrollment","");
        recyclerView=findViewById(R.id.recyclerView_show_homework_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Homework> homework= new FirebaseRecyclerOptions.Builder<Homework>()
                .setQuery(FirebaseDatabase.getInstance()
                        .getReference("shomework").child(senrollment).orderByValue(), Homework.class)
                .build();
        adepter=new HomeworkAdepter(homework);
        recyclerView.setAdapter(adepter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adepter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adepter.startListening();
    }

    public void homework(View view) {
        startActivity(new Intent(getApplicationContext(),StudentHomework.class));
    }
}