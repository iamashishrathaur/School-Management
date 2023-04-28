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
import com.rathaur.gpm.Adepter.TeacherComplaintsAdepter;
import com.rathaur.gpm.DataBaseModal.Teacher;

public class CompalintTeachertFragment extends Fragment {

RecyclerView recyclerView;
TeacherComplaintsAdepter adepter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_compalint_teachert, container, false);
        recyclerView=view.findViewById(R.id.fragment_teacher_complaints_recycler);
        FirebaseRecyclerOptions<Teacher> teacher=new FirebaseRecyclerOptions.Builder<Teacher>().setQuery(FirebaseDatabase.getInstance().getReference("tcomplaints").orderByValue(), Teacher.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adepter=new TeacherComplaintsAdepter(teacher);
        recyclerView.setAdapter(adepter);
        adepter.startListening();
        return view;
    }
}