package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rathaur.gpm.databinding.ActivityStudentEventsBinding;

import java.util.Objects;

public class StudentEvents extends AppCompatActivity {

    ActivityStudentEventsBinding binding;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStudentEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.eventBackPressed.setOnClickListener(view -> onBackPressed());

    }
}