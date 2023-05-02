package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Users;

import java.util.Objects;

public class LogInPage extends AppCompatActivity {
    TextInputEditText enrollment,password;
    Dialog dialog;
    TextView student_login,forgate_password,term,privacy;
    DatabaseReference dr1;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        student_login =findViewById(R.id.student_login);
        forgate_password=findViewById(R.id.forgote_password);
        enrollment=findViewById(R.id.user_enrollment);
        password=findViewById(R.id.student_password);
        term=findViewById(R.id.tearm);
        privacy=findViewById(R.id.privacy);
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        dr1=FirebaseDatabase.getInstance().getReference("User");
        term.setOnClickListener(v -> {

        });
        privacy.setOnClickListener(v -> {

        });
        forgate_password.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ForgatePassword.class)));
        student_login.setOnClickListener(v -> {
            String userEnrollment = Objects.requireNonNull(enrollment.getText()).toString().trim();
            String userPassword = Objects.requireNonNull(password.getText()).toString().trim();
              if (!userEnrollment.isEmpty()) {
                  if (!userPassword.isEmpty()) {
                      dialog.show();
                      FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                      DatabaseReference databaseReference = firebaseDatabase.getReference("student");
                      Query checkEnrolment = databaseReference.orderByChild("senrollment").equalTo(userEnrollment);
                      checkEnrolment.addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                              if (snapshot.exists()) {
                                  String Password = snapshot.child(userEnrollment).child("spassword").getValue(String.class);
                                  if (userPassword.equals(Password)) {
                                      String name = snapshot.child(userEnrollment).child("sname").getValue(String.class);
                                      String password = snapshot.child(userEnrollment).child("spassword").getValue(String.class);
                                      String email = snapshot.child(userEnrollment).child("semail").getValue(String.class);
                                      String mobile = snapshot.child(userEnrollment).child("smobile").getValue(String.class);
                                      String profession = snapshot.child(userEnrollment).child("sprofession").getValue(String.class);
                                      String year = snapshot.child(userEnrollment).child("syear").getValue(String.class);
                                      String senrollment = snapshot.child(userEnrollment).child("senrollment").getValue(String.class);
                                      String image = snapshot.child(userEnrollment).child("simage").getValue(String.class);
                                      SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                      SharedPreferences.Editor editor = sharedPreferences.edit();
                                      editor.putString("name", name);
                                      editor.putString("mobile", mobile);
                                      editor.putString("email", email);
                                      editor.putString("profession", profession);
                                      editor.putString("year", year);
                                      editor.putString("password", password);
                                      editor.putString("enrollment", senrollment);
                                      editor.putString("image", image);
                                      editor.apply();
                                      Users user=new Users(name,senrollment);
                                      dr1.child(Objects.requireNonNull(senrollment)).addValueEventListener(new ValueEventListener(){
                                          @Override
                                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                                              if (!snapshot.exists()){
                                                  dr1.child(senrollment).setValue(user);
                                              }
                                          }

                                          @Override
                                          public void onCancelled(@NonNull DatabaseError error) {

                                          }
                                      });
                                      Intent intent = new Intent(getApplicationContext(), Navigation_drawer.class);
                                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                      startActivity(intent);
                                  } else {
                                      dialog.dismiss();
                                      Toast.makeText(LogInPage.this, "Wrong password try again", Toast.LENGTH_SHORT).show();
                                  }
                              } else {
                                  FirebaseDatabase database = FirebaseDatabase.getInstance();
                                  DatabaseReference reference = database.getReference("teacher");
                                  Query check_teacher_database = reference.orderByChild("tenrollment").equalTo(userEnrollment);
                                  check_teacher_database.addListenerForSingleValueEvent(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                          if (dataSnapshot.exists()) {
                                              String password = dataSnapshot.child(userEnrollment).child("tpassword").getValue(String.class);
                                              if (userPassword.equals(password)) {
                                                  String pass = dataSnapshot.child(userEnrollment).child("tpassword").getValue(String.class);
                                                  String name = dataSnapshot.child(userEnrollment).child("tname").getValue(String.class);
                                                  String email = dataSnapshot.child(userEnrollment).child("temail").getValue(String.class);
                                                  String mobile = dataSnapshot.child(userEnrollment).child("tmobile").getValue(String.class);
                                                  String profession = dataSnapshot.child(userEnrollment).child("tprofession").getValue(String.class);
                                                  String year = dataSnapshot.child(userEnrollment).child("tyear").getValue(String.class);
                                                  String image = dataSnapshot.child(userEnrollment).child("timage").getValue(String.class);
                                                  String tenrollment = dataSnapshot.child(userEnrollment).child("tenrollment").getValue(String.class);
                                                  SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                                  SharedPreferences.Editor editor = sharedPreferences.edit();
                                                  editor.putString("name", name);
                                                  editor.putString("mobile", mobile);
                                                  editor.putString("email", email);
                                                  editor.putString("profession", profession);
                                                  editor.putString("year", year);
                                                  editor.putString("password", pass);
                                                  editor.putString("enrollment", tenrollment);
                                                  editor.putString("image", image);
                                                  editor.apply();
                                                  Users user=new Users(name,tenrollment);
                                                  dr1.child(Objects.requireNonNull(tenrollment)).addValueEventListener(new ValueEventListener() {
                                                      @Override
                                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                          if (!snapshot.exists()){
                                                              dr1.child(tenrollment).setValue(user);
                                                          }
                                                      }

                                                      @Override
                                                      public void onCancelled(@NonNull DatabaseError error) {

                                                      }
                                                  });
                                                  Intent intent = new Intent(getApplicationContext(), NavigationDrawerTeacher.class);
                                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                  startActivity(intent);

                                              } else {
                                                  dialog.dismiss();
                                                  Toast.makeText(LogInPage.this, "wrong Password ", Toast.LENGTH_SHORT).show();
                                              }
                                          } else {
                                              DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("admin");
                                              databaseReference1.addValueEventListener(new ValueEventListener(){
                                                  @Override
                                                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                      if (snapshot.exists()) {
                                                          String admin = snapshot.child("id").getValue(String.class);
                                                          String pass = snapshot.child("password").getValue(String.class);
                                                          if (Objects.equals(admin, userEnrollment)) {
                                                              if (Objects.equals(pass, userPassword)) {
                                                                  Intent intent = new Intent(getApplicationContext(), AdministratorActivity.class);
                                                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                  startActivity(intent);
                                                              }
                                                              else {
                                                                  dialog.dismiss();
                                                                  Toast.makeText(LogInPage.this, "wrong Password ", Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                          else {
                                                              dialog.dismiss();
                                                              Toast.makeText(LogInPage.this, "user not exist", Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
                                               }

                                                  @Override
                                                  public void onCancelled(@NonNull DatabaseError error) {
                                                      dialog.dismiss();
                                                      Toast.makeText(LogInPage.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                                  }
                                              });

                                          }
                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError error) {
                                          dialog.dismiss();
                                          Toast.makeText(LogInPage.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                      }
                                  });
                              }
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError error) {
                              dialog.dismiss();
                              Toast.makeText(LogInPage.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                          }
                      });

                  } else {
                      Toast.makeText(LogInPage.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                  }
              }

              //
              else {
                  Toast.makeText(LogInPage.this, "please Enter Enrollment Number", Toast.LENGTH_SHORT).show();
              }
           });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( dialog!=null && dialog.isShowing() ){
            dialog.cancel();
        }

    }
}
