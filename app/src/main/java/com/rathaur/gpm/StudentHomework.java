package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.DatePickerFragment;
import com.rathaur.gpm.Adepter.HomeworkAdepter;
import com.rathaur.gpm.DataBase.Homework;

import org.checkerframework.checker.units.qual.C;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class StudentHomework extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
   RelativeLayout calender;
    TextInputEditText subject,heading,content;
    DatabaseReference dpReference;
    String Date;
    String senrollment;
   TextView textView,submit;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homework);
        getSupportActionBar().hide();
        calender=findViewById(R.id.calender_picker);
        textView=findViewById(R.id.date_time_formate);
        submit=findViewById(R.id.student_homework_submit);
        subject=findViewById(R.id.student_homework_edittext_subject);
        heading=findViewById(R.id.student_homework_edittext_heading);
        content=findViewById(R.id.student_homework_edittext_content);
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        senrollment=sharedPreferences.getString("senrollment","");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid= UUID.randomUUID().toString();
                String s=subject.getText().toString();
                String h=heading.getText().toString();
                String c=content.getText().toString();
                if (!s.isEmpty()){
                    if (!h.isEmpty()){
                        if (!c.isEmpty()){
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference("shomework");
                            Homework homework1=new Homework(s,h,c,Date);
                            databaseReference.child(senrollment).child(uid).setValue(homework1);
                            startActivity(new Intent(getApplicationContext(), ShowStudentHomework.class));
                        }
                    }
                }
            }
        });

                calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(),"date picker");

            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DATE,i2);
        Date= DateFormat.getDateInstance().format(calendar.getTime());
        textView.setText(Date);
    }
}