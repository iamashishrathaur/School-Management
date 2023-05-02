package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.AllotSubjectAdepter;
import com.rathaur.gpm.DataBaseModal.AllotSubjectModal;
import com.rathaur.gpm.databinding.ActivityTeacherAllotSubjectShowingBinding;

import java.util.Objects;

public class TeacherAllotSubjectShowing extends AppCompatActivity {

    ActivityTeacherAllotSubjectShowingBinding binding;
    AllotSubjectAdepter adepter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTeacherAllotSubjectShowingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.adminAllotSubjectBackPressed.setOnClickListener(view ->onBackPressed());
        binding.adminAllotSubjectButton.setOnClickListener(view ->startActivity(new Intent(getApplicationContext(),TeacherAllotSubject.class)));
        binding.adminAllotSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<AllotSubjectModal> modalFirebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<AllotSubjectModal>()
                .setQuery(FirebaseDatabase.getInstance().getReference("allotsubject").orderByValue(), AllotSubjectModal.class).build();
        adepter=new AllotSubjectAdepter(modalFirebaseRecyclerOptions);
        binding.adminAllotSubjectRecyclerView.setAdapter(adepter);
        adepter.startListening();

    }
}