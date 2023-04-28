package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Student;
import com.rathaur.gpm.DataBaseModal.Teacher;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRagistation extends AppCompatActivity {
 Spinner gender,profession,student_year;
 RelativeLayout select_year;
 TextInputEditText name,email,enrollment,password,confirmPass;
 final String[] Gender={"Male","Female"};
 final String [] Profession={"Student","Teacher"};
 final String [] Year={"First Year","Second Year","Third Year"};
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
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})

        RelativeLayout back=findViewById(R.id.user_ragistation_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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
                        if (!upassword.isEmpty()) {

                            if (password.getText().toString().length() >=8 ) {

                                if (!uconfirmPass.isEmpty()) {
                                    if (upassword.equals(uconfirmPass)) {
                                        if (!uemail.matches("[a-zA-Z0-9]+@[a-z]+.[a-z]")) {
                                            if (uprofession.equals("Student")) {
                                                Dialog dialog = new Dialog(this);
                                                dialog.setCanceledOnTouchOutside(false);
                                                dialog.setContentView(R.layout.progress_dialog);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                                dialog.show();
                                                firebaseDatabase = FirebaseDatabase.getInstance();
                                                databaseReference = firebaseDatabase.getReference("student");
                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Student student = new Student(uname, umobile, uemail, ugender, uprofession, uenrollment, upassword, uyear);
                                                        databaseReference.child(uenrollment).setValue(student);
                                                        Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UserRagistation.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            } else {
                                                firebaseDatabase = FirebaseDatabase.getInstance();
                                                Dialog dialog = new Dialog(this);
                                                dialog.setCanceledOnTouchOutside(false);
                                                dialog.setContentView(R.layout.progress_dialog);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                                dialog.show();
                                                databaseReference = firebaseDatabase.getReference("teacher");
                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Teacher teacher = new Teacher(uname, umobile, uemail, ugender, uprofession, uenrollment, upassword);
                                                        databaseReference.child(uenrollment).setValue(teacher);
                                                        Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        dialog.dismiss();
                                                        Toast.makeText(UserRagistation.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        } else {
                                            email.setError("fill");
                                            email.requestFocus();
                                        }
                                    } else {
                                        confirmPass.setError("wrong password");
                                        confirmPass.requestFocus();
                                    }
                                } else {
                                    confirmPass.setError("fill");
                                    confirmPass.requestFocus();
                                }
                            }
                            else {
                                Toast.makeText(this, "enter strong password", Toast.LENGTH_SHORT).show();
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
    private boolean isPasswordValidMethod(String pass) {

        String yourString = Objects.requireNonNull(password.getText()).toString();

        System.out.println("yourString is =" + yourString);

        boolean isValid = false;

        // ^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$
        // ^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$

        String expression = "^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{8,}$";
        CharSequence inputStr = password.getText().toString();

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            System.out.println("if");
            isValid = true;
        }else{
            System.out.println("else");
        }
        return isValid;
    }
    }
