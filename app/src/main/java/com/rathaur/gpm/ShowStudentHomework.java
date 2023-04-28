package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.HomeworkAdepter;
import com.rathaur.gpm.DataBaseModal.Homework;

import java.util.Objects;

public class ShowStudentHomework extends AppCompatActivity {
  HomeworkAdepter adepter;
  String senrollment;
    TextView textEmpty;
    ImageView empty;
    RecyclerView recyclerView;
    DatabaseReference reference;
    Dialog dialogbox;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_homework);
        Objects.requireNonNull(getSupportActionBar()).hide();
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        senrollment = sharedPreferences.getString("enrollment", "");
        recyclerView = findViewById(R.id.recyclerView_show_homework_list);
        empty = findViewById(R.id.homework_empty);
        textEmpty = findViewById(R.id.homework_empty_text);
        dialogbox= new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);

        reference = FirebaseDatabase.getInstance().getReference("shomework");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back = findViewById(R.id.show_homework_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        FirebaseRecyclerOptions<Homework> homework = new FirebaseRecyclerOptions.Builder<Homework>()
                .setQuery(FirebaseDatabase.getInstance()
                        .getReference("shomework").child(senrollment).orderByValue(), Homework.class)
                .build();

        adepter = new HomeworkAdepter(homework);
        recyclerView.setAdapter(adepter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialogbox.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adepter.startListening();
                reference.child(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            dialogbox.dismiss();
                            empty.setVisibility(View.GONE);
                            textEmpty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            dialogbox.dismiss();
                            textEmpty.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialogbox.dismiss();
                        Toast.makeText(ShowStudentHomework.this, "try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },1000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        adepter.startListening();
        dialogbox.dismiss();
    }

    public void homework(View view) {
        startActivity(new Intent(getApplicationContext(),StudentHomework.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}