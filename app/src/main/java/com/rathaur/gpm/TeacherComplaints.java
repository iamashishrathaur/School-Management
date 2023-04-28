package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.TeacherComplaintsAdepter;
import com.rathaur.gpm.DataBaseModal.Teacher;

public class TeacherComplaints extends AppCompatActivity {



    RecyclerView recyclerView;
    TeacherComplaintsAdepter adepter;
    String tenrollment;

    FloatingActionButton floatingActionButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_complaints);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.teacher_complaints_showing_recyclerView);
        floatingActionButton=findViewById(R.id.teacher_add_complaints);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.teacher_complaints_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        tenrollment=sharedPreferences.getString("enrollment","");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FirebaseRecyclerOptions<Teacher> complaints =new FirebaseRecyclerOptions.Builder<Teacher>()
                .setQuery(FirebaseDatabase.getInstance()
                        .getReference("tcomplaints").orderByChild("tenrollment").equalTo(tenrollment), Teacher.class)
                .build();
        adepter=new TeacherComplaintsAdepter(complaints);
        recyclerView.setAdapter(adepter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TeacherAddComplaints.class));
            }
        });
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