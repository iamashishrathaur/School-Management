package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogInPage extends AppCompatActivity {
    TextInputEditText enrollment,password;
    TextView student_login,forgate_password,term,privacy;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        student_login =findViewById(R.id.student_login);
        forgate_password=findViewById(R.id.forgote_password);
        enrollment=findViewById(R.id.user_enrollment);
        password=findViewById(R.id.student_password);
        term=findViewById(R.id.tearm);
        privacy=findViewById(R.id.privacy);
        getSupportActionBar().hide();
        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        forgate_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEnrollment = enrollment.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                  if (!userEnrollment.isEmpty()){
                      FirebaseDatabase  firebaseDatabase = FirebaseDatabase.getInstance();
                      DatabaseReference databaseReference = firebaseDatabase.getReference("student");
                      Query checkEnrolment = databaseReference.orderByChild("senrollment").equalTo(userEnrollment);
                      checkEnrolment.addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if (snapshot.exists())
                               {
                                  String Password=snapshot.child(userEnrollment).child("spassword").getValue(String.class);
                                  if (userPassword.equals(Password)){
//                                      FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
//                                      StorageReference storageReference=firebaseStorage.getReference("StudentImage");
                                      String name=snapshot.child(userEnrollment).child("sname").getValue(String.class);
                                      String email=snapshot.child(userEnrollment).child("semail").getValue(String.class);
                                      String mobile=snapshot.child(userEnrollment).child("smobile").getValue(String.class);
                                      String profession=snapshot.child(userEnrollment).child("sprofession").getValue(String.class);
                                      String year=snapshot.child(userEnrollment).child("syear").getValue(String.class);
                                      String senrollment=snapshot.child(userEnrollment).child("senrollment").getValue(String.class);
                                      SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
                                      SharedPreferences.Editor editor=sharedPreferences.edit();
                                      editor.putString("sname",name);
                                      editor.putString("smobile",mobile);
                                      editor.putString("semail",email);
                                      editor.putString("sprofession",profession);
                                      editor.putString("syear",year);
                                      editor.putString("senrollment",senrollment);
                                      editor.apply();
                                      startActivity(new Intent(getApplicationContext(),Navigation_drawer.class));
                                  }
                                  else {
                                      Toast.makeText(LogInPage.this, "Wrong password try again", Toast.LENGTH_SHORT).show();
                                  }
                              }
                               else{
                                  FirebaseDatabase database=FirebaseDatabase.getInstance();
                                  DatabaseReference reference=database.getReference("teacher");
                                  Query check_teacher_database =  reference.orderByChild("tenrollment").equalTo(userEnrollment);
                                  check_teacher_database.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       if (dataSnapshot.exists()){
                                           String password=dataSnapshot.child(userEnrollment).child("tpassword").getValue(String.class);
                                           if (userPassword.equals(password)){
                                               startActivity(new Intent(LogInPage.this,NavigationDrawerTeacher.class));
                                           }
                                           else {
                                               Toast.makeText(LogInPage.this, "wrong Password ", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                       else {
                                           Toast.makeText(LogInPage.this, "user not exist", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {
                                       Toast.makeText(LogInPage.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               });
                              }
                           }
                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {
                              Toast.makeText(LogInPage.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                          }
                      });
                  }
                  else {
                      Toast.makeText(LogInPage.this, "please Enter Enrollment Number", Toast.LENGTH_SHORT).show();
                  }
               }
           });
    }
}
