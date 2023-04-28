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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.ApplicationAdepter;
import com.rathaur.gpm.DataBaseModal.Applications;

public class StudentApplicationShow extends AppCompatActivity {
  RecyclerView recyclerView;
  String senrollment;
  TextView textEmpty;
  ImageView empty;
  FloatingActionButton button;
  ApplicationAdepter adepter;
  DatabaseReference reference;
    Dialog dialogbox;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_application_show);
        getSupportActionBar().hide();
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        senrollment = sharedPreferences.getString("enrollment", "");
        recyclerView = findViewById(R.id.student_application_showing);
        button=findViewById(R.id.student_add_application);
        empty=findViewById(R.id.application_empty);
        textEmpty=findViewById(R.id.application_empty_text);
        reference= FirebaseDatabase.getInstance().getReference("sapplications");
       dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.student_application_show_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            FirebaseRecyclerOptions<Applications> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Applications>()
                    .setQuery(FirebaseDatabase.getInstance()
                            .getReference("sapplications").orderByChild("enrollment").equalTo(senrollment), Applications.class).build();

            adepter = new ApplicationAdepter(firebaseRecyclerOptions);
            recyclerView.setAdapter(adepter);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),StudentApplication.class));
                }
            });

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


                        } else {
                            dialogbox.dismiss();
                            textEmpty.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialogbox.dismiss();
                        Toast.makeText(StudentApplicationShow.this, "try again", Toast.LENGTH_SHORT).show();
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