package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rathaur.gpm.Adepter.SubjectAdepter;
import com.rathaur.gpm.DataBaseModal.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentSubject extends AppCompatActivity {
TextView year_name;
RelativeLayout back;
SubjectAdepter adepter;
final List<Subject> subjects=new ArrayList<>();
RecyclerView recyclerView;
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject);
        Objects.requireNonNull(getSupportActionBar()).hide();
        year_name=findViewById(R.id.year_name);
        back=findViewById(R.id.student_subject_back_pressed);
        recyclerView=findViewById(R.id.recycler_subject_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String year=sharedPreferences.getString("year","");

        if (year.equals("First Year")){
         subjects.add(new Subject("1","Communication Skills–I","T",""));
         subjects.add(new Subject("1","Applied Mathematics-I","T",""));
         subjects.add(new Subject("1","Applied Physics-I","T",""));
         subjects.add(new Subject("1","Applied Chemistry","T",""));
         subjects.add(new Subject("1","F.C.I.T.","T",""));
         subjects.add(new Subject("1","Technical Drawing","T",""));
         subjects.add(new Subject("1","Workshop Practice","",""));
         subjects.add(new Subject("2","Applied Mathematics-II","",""));
         subjects.add(new Subject("2","Applied Physics-II","",""));
         subjects.add(new Subject("2","B.E. And E.E","",""));
         subjects.add(new Subject("2","Concept of Programing C","",""));
         subjects.add(new Subject("2","Office Automation Tools","",""));
        }
        else if (year.equals("Second Year")){
         subjects.add(new Subject("3","Applied Mathematics-III","T",""));
         subjects.add(new Subject("3","Internet and Web technology","T",""));
         subjects.add(new Subject("3","Environment Studies","T",""));
         subjects.add(new Subject("3","Data Communication and Computer Networks","T",""));
         subjects.add(new Subject("3","Data Structure Using C","T",""));
         subjects.add(new Subject("3","Digital Electronics","T",""));
         subjects.add(new Subject("4","Communication Skills–II","T",""));
         subjects.add(new Subject("4","Database Management System","T",""));
         subjects.add(new Subject("4","OOPs Java ","T",""));
         subjects.add(new Subject("4","Operating System","T",""));
         subjects.add(new Subject("4","E-Commerce And Digital Marketing","T",""));
         subjects.add(new Subject("4","Energy Conservation","T",""));
         subjects.add(new Subject("4","Universal Human Values","T",""));
        }
        else{
         subjects.add(new Subject("5","Software Engineering","Theory",""));
         subjects.add(new Subject("5","Web Development Using Php","Theory",""));
         subjects.add(new Subject("5","Computer Programing Using Python","Theory",""));
         subjects.add(new Subject("5","C.A.H.M. ","Theory",""));
         subjects.add(new Subject("5","Internet Of Things","Theory",""));
         subjects.add(new Subject("5","Minor Project Work","Practical",""));
         subjects.add(new Subject("6","Development of Android Application","Theory",""));
         subjects.add(new Subject("6","Cloud Computing","Theory",""));
         subjects.add(new Subject("6","IM And Ed","Theory",""));
         subjects.add(new Subject("6","Advance Java & .Net & DS And ML ","Theory",""));
         subjects.add(new Subject("6","Major Project Work ","Practical",""));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adepter=new SubjectAdepter(subjects,StudentSubject.this);
                recyclerView.setAdapter(adepter);
            }
        },100);
        back.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}