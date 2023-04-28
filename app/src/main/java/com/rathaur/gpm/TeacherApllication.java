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
import com.rathaur.gpm.Adepter.ApplicationAdepter;
import com.rathaur.gpm.DataBaseModal.Applications;

public class TeacherApllication extends AppCompatActivity {
    RecyclerView recyclerView;
    ApplicationAdepter adepter;
    String tenrollment;
    RelativeLayout back;
    FloatingActionButton floatingActionButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_apllication);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.teacher_application_showing);
        floatingActionButton=findViewById(R.id.teacher_add_application);
        back=findViewById(R.id.teacher_application_back_pressed);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        tenrollment = sharedPreferences.getString("enrollment", "");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Applications> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Applications>()
                .setQuery(FirebaseDatabase.getInstance()
                        .getReference("tapplications").orderByChild("enrollment").equalTo(tenrollment), Applications.class).build();
        adepter = new ApplicationAdepter(firebaseRecyclerOptions);
        recyclerView.setAdapter(adepter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TeacherAddApplication.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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