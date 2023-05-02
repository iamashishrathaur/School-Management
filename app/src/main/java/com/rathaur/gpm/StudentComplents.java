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
import com.rathaur.gpm.Adepter.ComplaintsAdepter;
import com.rathaur.gpm.DataBaseModal.Student;

import java.util.Objects;

public class StudentComplents extends AppCompatActivity {
    ImageView empty;
    TextView emptyText;
    RecyclerView recyclerView;
    ComplaintsAdepter adepter;
    String senrollment,sname,semail,syear,smobile;
    DatabaseReference reference;
    Dialog dialogbox;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complents);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView=findViewById(R.id.complaints_showing_recyclerView);
        empty=findViewById(R.id.complaints_empty);
        emptyText=findViewById(R.id.complaints_empty_text);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("scomplaints");
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        sname=sharedPreferences.getString("name","");
        semail=sharedPreferences.getString("email","");
        senrollment=sharedPreferences.getString("enrollment","");
        syear=sharedPreferences.getString("year","");
        smobile=sharedPreferences.getString("mobile","");
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(dialogbox.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.student_complaints_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Student> complaints=new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(FirebaseDatabase.getInstance()
                .getReference("scomplaints").orderByChild("senrollment").equalTo(senrollment), Student.class)
                .build();

        adepter=new ComplaintsAdepter(complaints);
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
                            emptyText.setVisibility(View.GONE);
                            empty.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            dialogbox.dismiss();
                        } else {
                            dialogbox.dismiss();
                            emptyText.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialogbox.dismiss();
                        Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },1000);
    }

    @Override
    protected void onStop() {
        dialogbox.dismiss();
        super.onStop();
        adepter.startListening();
    }

    public void fillComplaints(View view) {
        startActivity(new Intent(getApplicationContext(), StudentAddComplaints.class));
    }
}