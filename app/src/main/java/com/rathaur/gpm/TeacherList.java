package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.TeacherAdepter;
import com.rathaur.gpm.DataBaseModal.Teacher;

public class TeacherList extends AppCompatActivity {
RecyclerView recyclerView;
TeacherAdepter adepter;
Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.Teacher_list_recyclerView);
        dialog =new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.teacher_list_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adepter.startListening();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance()
                        .getReference().child("teacher");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            dialog.dismiss();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(TeacherList.this, "teacher not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                    }
                });
            }
        },1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
        adepter.startListening();
    }
}