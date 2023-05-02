package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AttendanceMainActivity extends AppCompatActivity {
   TextView button;
   RelativeLayout  classS,subject;
    String classSelection;
    String firstSubSelection;
    String secondSubSelection;
    String sub;
    String cla;
    TextView subjectShow;
    TextView classShow;
    String thirdSubSelection;
    Dialog dialog;
     RelativeLayout back;
    final String [] classItem={"First Year", "Second Year","Third Year"};
    final String [] FirstSubject={"Communication Skill-I","Applied Mathematics-I","Applied Physics-I","Applied Chemistry","Fundamentals of Computer and Information Technology","Technical Drawing","Workshop Practice", "Applied Mathematics-II","Applied Physics-II","Basics of Electrical and Electronics Engineering","Multimedia & Animation","Concept of Programming Using C ","Office Automation Tools"};
    final String [] SecondSubject={"Applied Mathematics-III","Internet and Web Technology","Environmental Studies","Data Communication and Computer Networks","Data Structure Using C ","Digital Electronics ","Communication Skill-II ","Database Management System","E-Commerce and Digital Marketing","Energy Conservation","Universal Human Values "};
    final String[] ThirdSubject={"Software Engineering","Web Development using PHP","Computer Programming using Python","Computer Architecture and Hardware Maintenance","Internet of Things ","Development of Android Applications","Cloud Computing","Industrial Management and Entrepreneurship Development","Elective"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        button = findViewById(R.id.SelectSubmitButton);
        classS = findViewById(R.id.relativeClassChoose);
        subject = findViewById(R.id.relativeSubjectChoose);
        classShow = findViewById(R.id.choose_class_show_here);
        subjectShow = findViewById(R.id.choose_subject_show_here);
        back=findViewById(R.id.select_student_class_attendance_pressed);
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
    try {

        subject.setVisibility(View.GONE);
        classS.setOnClickListener(view -> showDialog());


        subject.setOnClickListener(view -> {
            if (classSelection.equals(classItem[0])) {
              //  Toast.makeText(AttendanceMainActivity.this, "first", Toast.LENGTH_SHORT).show();
                showFirstDialog();

            } else if (classSelection.equals(classItem[1])) {
               // Toast.makeText(AttendanceMainActivity.this, "second", Toast.LENGTH_SHORT).show();
                showSecondDialog();

            } else if (classSelection.equals(classItem[2])) {
              //  Toast.makeText(AttendanceMainActivity.this, "third", Toast.LENGTH_SHORT).show();
                showThirdDialog();
            } else {
                Toast.makeText(AttendanceMainActivity.this, "error ", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(view -> onBackPressed());

        button.setOnClickListener(view -> {
            if (sub!=null){
                if (cla!=null){
                    dialog.show();
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String sdate = dateFormat.format(date);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Attendance");
                    databaseReference.child(sub).child(sdate).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                               databaseReference.child(sub).child(sdate).setValue("").addOnSuccessListener(unused -> {
                                   dialog.dismiss();
                                   Intent intent=new Intent(getApplicationContext(), TeacherAttendance.class);
                                   intent.putExtra("subject",sub);
                                   intent.putExtra("class",cla);
                                   startActivity(intent);
                               }) ;
                            }
                            else {
                                dialog.dismiss();
                                Snackbar.make(view,"you are already exist",Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            dialog.dismiss();
                            Toast.makeText(AttendanceMainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(AttendanceMainActivity.this, "fill Subject", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(AttendanceMainActivity.this, "fill year", Toast.LENGTH_SHORT).show();
            }
        });
    }
    catch (RuntimeException e) {
        e.getMessage();
        throw e;
    }
        }
        public void showFirstDialog(){
            firstSubSelection = FirstSubject[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose the Subject");
            builder.setCancelable(false);
            builder.setSingleChoiceItems(FirstSubject, 0, (dialogInterface, i) -> firstSubSelection = FirstSubject[i]);
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                subjectShow.setText(firstSubSelection);
                sub=secondSubSelection;
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }
        public void showDialog () {
            classSelection = classItem[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose the class");
            builder.setCancelable(false);
            builder.setSingleChoiceItems(classItem, 0, (dialogInterface, i) -> classSelection = classItem[i]);
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                classShow.setText(classSelection);
                cla=classSelection;
                subject.setVisibility(View.VISIBLE);

            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }
        public void showSecondDialog () {
            secondSubSelection = SecondSubject[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose the Subject");
            builder.setCancelable(false);
            builder.setSingleChoiceItems(SecondSubject, 0, (dialogInterface, i) -> secondSubSelection = SecondSubject[i]);
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                sub=secondSubSelection;
                subjectShow.setText(secondSubSelection);
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }
        public void showThirdDialog () {
            thirdSubSelection = ThirdSubject[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose the Subject");
            builder.setCancelable(false);
            builder.setSingleChoiceItems(ThirdSubject, 0, (dialogInterface, i) -> thirdSubSelection = ThirdSubject[i]);
            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                subjectShow.setText(thirdSubSelection);
                sub=thirdSubSelection;
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();

    }
}