package com.rathaur.gpm;

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

public class ApplicationTeacherFragment extends Fragment {
    RecyclerView recyclerView;

    ApplicationAdepter adepter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_application_teacher, container, false);
        recyclerView=view.findViewById(R.id.teacher_application_fragment_recycler);
        FirebaseRecyclerOptions<Applications> teacher=new FirebaseRecyclerOptions.Builder<Applications>().setQuery(FirebaseDatabase.getInstance().getReference("tapplications").orderByValue(), Applications.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adepter=new ApplicationAdepter(teacher);
        recyclerView.setAdapter(adepter);
        adepter.startListening();
        return view;
    }
}