package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Applications;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TeacherAddApplication extends AppCompatActivity {

    TextInputEditText subject,resions;
    String tenrollment,tname,tmobile;
    RelativeLayout back;

    TextView button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_application);
        getSupportActionBar().hide();
        subject=findViewById(R.id.teacher_application_subject_edittext);
        resions=findViewById(R.id.teacher_application_resion_edittext);
        button=findViewById(R.id.teacherAddApplication);
        back=findViewById(R.id.teacher_add_application_back_pressed);
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        tname=sharedPreferences.getString("name","");
        tenrollment=sharedPreferences.getString("enrollment","");
        tmobile=sharedPreferences.getString("mobile","");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=subject.getText().toString();
                String r=resions.getText().toString();
                Date date= Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String sdate= dateFormat.format(date);
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("tapplications");
                Applications applications=new Applications(tname,tmobile,tenrollment,s,r,sdate);
                databaseReference.orderByChild("enrollment").equalTo(tenrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()){
                            databaseReference.child(tenrollment).setValue(applications);
                        }else {
                            Toast.makeText(TeacherAddApplication.this, "Your Applications already Sent ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TeacherAddApplication.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}