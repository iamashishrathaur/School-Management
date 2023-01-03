package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.TeacherAdepter;
import com.rathaur.gpm.DataBase.Teacher;

public class TeacherList extends AppCompatActivity {
RecyclerView recyclerView;
TeacherAdepter adepter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.Teacher_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Teacher> teacher=new FirebaseRecyclerOptions.Builder<Teacher>()
                .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("teacher"), Teacher.class)
                .build();
        adepter=new TeacherAdepter(teacher);
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
}