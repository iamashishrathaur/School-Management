package com.rathaur.gpm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.ApplicationAdepter;
import com.rathaur.gpm.DataBase.Applications;

public class StudentApplicationShow extends AppCompatActivity {
  RecyclerView recyclerView;
  String senrollment;
  ShimmerFrameLayout shimmerFrameLayout;
    ApplicationAdepter adepter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_application_show);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.student_application_showing);
        shimmerFrameLayout=findViewById(R.id.shimmer_effect);
        shimmerFrameLayout.startShimmer();
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        senrollment=sharedPreferences.getString("senrollment","");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Applications> firebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<Applications>()
                .setQuery(FirebaseDatabase.getInstance()
                        .getReference("sapplications").orderByChild(senrollment), Applications.class).build();

         adepter=new ApplicationAdepter(firebaseRecyclerOptions);
         recyclerView.setAdapter(adepter);
          shimmerFrameLayout.stopShimmer();
          shimmerFrameLayout.setVisibility(View.GONE);
          recyclerView.setVisibility(View.VISIBLE);
    }


    public void addApplication(View view) {
        startActivity(new Intent(getApplicationContext(),StudentApplication.class));
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