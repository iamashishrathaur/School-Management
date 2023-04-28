package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.ApplicationAdepter;
import com.rathaur.gpm.DataBaseModal.Applications;

public class ApplicationStudentFragment extends Fragment {
  RecyclerView recyclerView;

  ApplicationAdepter adepter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_application_student, container, false);
        recyclerView=view.findViewById(R.id.student_application_fragment_recycler);
        FirebaseRecyclerOptions<Applications> student=new FirebaseRecyclerOptions.Builder<Applications>().setQuery(FirebaseDatabase.getInstance().getReference("sapplications").orderByValue(), Applications.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adepter=new ApplicationAdepter(student);
        recyclerView.setAdapter(adepter);
        adepter.startListening();
        return view;
    }
}