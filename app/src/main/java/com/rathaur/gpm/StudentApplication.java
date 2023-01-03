package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBase.Applications;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StudentApplication extends AppCompatActivity {
    TextInputEditText subject,resions;
    String senrollment,sname,smobile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_application);
        getSupportActionBar().hide();
        subject=findViewById(R.id.application_subject_edittext);
        resions=findViewById(R.id.application_resion_edittext);
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
         sname=sharedPreferences.getString("sname","");
         senrollment=sharedPreferences.getString("senrollment","");
         smobile=sharedPreferences.getString("smobile","");

    }

    public void applicatioSubmit(View view) {
        String s=subject.getText().toString();
        String r=resions.getText().toString();
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String sdate= dateFormat.format(date);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("sapplications");
        Applications applications=new Applications(sname,smobile,senrollment,s,r,sdate);
        databaseReference.orderByChild("enrollment").equalTo(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    databaseReference.child(senrollment).setValue(applications);
                }else {
                    Toast.makeText(StudentApplication.this, "Your Applications already Sent ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentApplication.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}