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
import com.rathaur.gpm.Adepter.ComplaintsAdepter;
import com.rathaur.gpm.DataBaseModal.Student;

public class CompalintStudentFragment extends Fragment {
    ComplaintsAdepter adepter;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_compalint_student, container, false);
         recyclerView=view.findViewById(R.id.fragment_student_complaints_recycler);
         FirebaseRecyclerOptions<Student> student=new FirebaseRecyclerOptions.Builder<Student>().setQuery(FirebaseDatabase.getInstance().getReference("scomplaints").orderByValue(), Student.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         adepter=new ComplaintsAdepter(student);
         recyclerView.setAdapter(adepter);
        adepter.startListening();

        return view;
    }
}