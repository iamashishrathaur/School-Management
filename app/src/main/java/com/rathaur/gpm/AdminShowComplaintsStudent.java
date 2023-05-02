package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.ComplaintsAdepter;
import com.rathaur.gpm.DataBaseModal.Student;
import com.rathaur.gpm.databinding.ActivityAdminShowComplaintsStudentBinding;

import java.util.Objects;

public class AdminShowComplaintsStudent extends AppCompatActivity {
    ComplaintsAdepter adepter;
    ActivityAdminShowComplaintsStudentBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminShowComplaintsStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        FirebaseRecyclerOptions<Student> student=new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(FirebaseDatabase.getInstance().getReference("scomplaints")
                        .orderByValue(), Student.class).build();

        binding.adminShowStudentComplaintsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adepter=new ComplaintsAdepter(student);
        binding.adminShowStudentComplaintsRecyclerView.setAdapter(adepter);

        binding.showStudentComplaintsBackPress.setOnClickListener(new View.OnClickListener() {
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