package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.NoticeAdepter;
import com.rathaur.gpm.DataBaseModal.Notice;

public class TeacherNotice extends AppCompatActivity {
    RecyclerView view;
    NoticeAdepter adepter;
    RelativeLayout back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notice);
        getSupportActionBar().hide();
        view=findViewById(R.id.teacher_notice_recycler);
        back=findViewById(R.id.teacher_notice_back_pressed);
        view.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Notice> options=new FirebaseRecyclerOptions.Builder<Notice>().setQuery(FirebaseDatabase.getInstance().getReference("Notice").child("teacher"),Notice.class).build();
        adepter=new NoticeAdepter(options);
        view.setAdapter(adepter);
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