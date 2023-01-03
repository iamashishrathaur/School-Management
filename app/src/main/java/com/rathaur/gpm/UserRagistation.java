package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.DataBase.Student;
import com.rathaur.gpm.DataBase.Teacher;

import java.util.Arrays;
import java.util.Locale;

public class UserRagistation extends AppCompatActivity {
 Spinner gender,profession,student_year;
 RelativeLayout select_year;
 TextInputEditText name,email,enrollment,password,confirmPass;
 String[] Gender={"Male","Female"};
 String [] Profession={"Student","Teacher"};
 String [] Year={"1st Year","2nd Year","3rd Year"};
  FirebaseDatabase firebaseDatabase;
  DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ragistation);
        getSupportActionBar().hide();
        gender=findViewById(R.id.user_gender);
        profession=findViewById(R.id.user_profession);
        name=findViewById(R.id.user_name);
        email=findViewById(R.id.user_email);
        enrollment=findViewById(R.id.user_enrollment);
        password=findViewById(R.id.user_password);
        student_year=findViewById(R.id.student_year);
        select_year=findViewById(R.id.select_year);
        confirmPass=findViewById(R.id.user_confirm_password);
        //select_year.setVisibility(View.GONE);
        ArrayAdapter<String> year=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,Year);
        year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        student_year.setAdapter(year);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,Profession);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profession.setAdapter(arrayAdapter);

        profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    select_year.setVisibility(View.VISIBLE);
                }
                else {
                    select_year.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    public void Register(View view) {
        SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
        String umobile= preferences.getString("mobile","");
        String uname = name.getText().toString().trim();
        String uemail = email.getText().toString().trim().toLowerCase(Locale.ROOT);
        String uenrollment = enrollment.getText().toString().trim();
        String upassword = password.getText().toString().trim();
        String uconfirmPass = confirmPass.getText().toString().trim();
        String ugender=gender.getSelectedItem().toString();
        String uprofession=profession.getSelectedItem().toString();
        String uyear=student_year.getSelectedItem().toString();
        if (!uname.isEmpty())
        {
            if (!uemail.isEmpty())
            {
                    if (!uenrollment.isEmpty())
                    {
                        if (!upassword.isEmpty())
                        {
                            if (!uconfirmPass.isEmpty())
                            {
                                if (upassword.equals(uconfirmPass))
                                {
                                    if (!uemail.matches("[a-zA-Z0-9]+@[a-z]+.[a-z]"))
                                    {
                                        if (uprofession.equals("Student")) {
                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            databaseReference = firebaseDatabase.getReference("student");
                                            Student student = new Student(uname, umobile, uemail, ugender, uprofession, uenrollment, upassword,uyear);
                                            databaseReference.child(uenrollment).setValue(student);
                                            startActivity(new Intent(getApplicationContext(),LogInPage.class));
                                            Toast.makeText(this, "Student Successful", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            databaseReference = firebaseDatabase.getReference("teacher");
                                            Teacher teacher = new Teacher(uname,umobile,uemail,ugender,uprofession,uenrollment,upassword);
                                            databaseReference.child(uenrollment).setValue(teacher);
                                            startActivity(new Intent(getApplicationContext(),LogInPage.class));
                                            Toast.makeText(this, "Teacher Successful", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        email.setError("fill");
                                        email.requestFocus();
                                    }
                                }
                                else {
                                    confirmPass.setError("wrong password");
                                    confirmPass.requestFocus();
                                }
                            }
                            else {
                                confirmPass.setError("fill");
                                confirmPass.requestFocus();
                            }
                        }
                        else {
                            password.setError("fill");
                            password.requestFocus();
                        }
                    }
                    else {
                        enrollment.setError("fill");
                        enrollment.requestFocus();
                    }
                }
                else {
                    email.setError("fill");
                    email.requestFocus();
                }
             }
            else {
                name.setError("fill");
                name.requestFocus();
            }
            }
    }
