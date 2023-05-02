package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.ApplicationAdepter;
import com.rathaur.gpm.DataBaseModal.Applications;
import com.rathaur.gpm.databinding.ActivityAdminShowApplicationTeacherBinding;

import java.util.Objects;

public class AdminShowApplicationTeacher extends AppCompatActivity {
    ActivityAdminShowApplicationTeacherBinding binding;
    ApplicationAdepter adepter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminShowApplicationTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.adminShowTeacherApplicationBackPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FirebaseRecyclerOptions<Applications> teacher=new FirebaseRecyclerOptions.Builder<Applications>().setQuery(FirebaseDatabase.getInstance().getReference("tapplications").orderByValue(), Applications.class).build();
        binding.adminShowTeacherApplication.setLayoutManager(new LinearLayoutManager(this));
        adepter=new ApplicationAdepter(teacher);
        binding.adminShowTeacherApplication.setAdapter(adepter);
        adepter.startListening();
    }
}