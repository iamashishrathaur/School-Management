package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.PdfAdepter;
import com.rathaur.gpm.DataBaseModal.PDF;

public class StudentStudyMaterials extends AppCompatActivity {
 FloatingActionButton floatingActionButton;
 RelativeLayout back;
 RecyclerView recyclerView;
 PdfAdepter adepter;
 ImageView empty;
 TextView emptyText;
    Dialog dialogbox;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_study_materials);
        getSupportActionBar().hide();
        floatingActionButton = findViewById(R.id.study_floatingActionButton);
        back=findViewById(R.id.student_study_back_pressed);
        recyclerView=findViewById(R.id.study_materials_recycler_view);
        emptyText=findViewById(R.id.study_empty_text);
        empty=findViewById(R.id.study_empty);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<PDF> pdf=new FirebaseRecyclerOptions.Builder<PDF>().
                setQuery(FirebaseDatabase.getInstance().getReference("studyMaterials"), PDF.class).build();
        
        adepter=new PdfAdepter(pdf);
        recyclerView.setAdapter(adepter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddStudyMaterials.class));
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
        dialogbox.show();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("studyMaterials");
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                adepter.startListening();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                           dialogbox.dismiss();
                            emptyText.setVisibility(View.GONE);
                            empty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else {
                            dialogbox.dismiss();
                            emptyText.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                      dialogbox.dismiss();
                        Toast.makeText(StudentStudyMaterials.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },1000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        dialogbox.dismiss();
        adepter.startListening();
    }
}