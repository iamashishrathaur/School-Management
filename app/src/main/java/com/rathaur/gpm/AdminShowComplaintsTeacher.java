package com.rathaur.gpm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.TeacherComplaintsAdepter;
import com.rathaur.gpm.DataBaseModal.Teacher;
import com.rathaur.gpm.databinding.ActivityAdminShowComplaintsTeacherBinding;

import java.util.Objects;

public class AdminShowComplaintsTeacher extends AppCompatActivity {

    ActivityAdminShowComplaintsTeacherBinding binding;
    TeacherComplaintsAdepter adepter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAdminShowComplaintsTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.adminShowTeacherComplaintsBackPressed.setOnClickListener(v -> onBackPressed());
        FirebaseRecyclerOptions<Teacher> teacher=new FirebaseRecyclerOptions.Builder<Teacher>().setQuery(FirebaseDatabase.getInstance().getReference("tcomplaints").orderByValue(), Teacher.class).build();
        binding.adminShowTeacherComplaints.setLayoutManager(new LinearLayoutManager(this));
        adepter=new TeacherComplaintsAdepter(teacher);
        binding.adminShowTeacherComplaints.setAdapter(adepter);
        adepter.startListening();
    }
}