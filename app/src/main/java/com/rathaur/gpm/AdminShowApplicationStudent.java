package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.ApplicationAdepter;
import com.rathaur.gpm.DataBaseModal.Applications;
import com.rathaur.gpm.databinding.ActivityAdminShowApplicationStudentBinding;

import java.util.Objects;

public class AdminShowApplicationStudent extends AppCompatActivity {
    ActivityAdminShowApplicationStudentBinding binding;
    ApplicationAdepter adepter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminShowApplicationStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        FirebaseRecyclerOptions<Applications> student=new FirebaseRecyclerOptions.Builder<Applications>().setQuery(FirebaseDatabase.getInstance().getReference("sapplications").orderByValue(), Applications.class).build();
        binding.adminShowStudentApplicationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adepter=new ApplicationAdepter(student);
        binding.adminShowStudentApplicationRecyclerView.setAdapter(adepter);
        adepter.startListening();
        binding.showStudentApplicationBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}