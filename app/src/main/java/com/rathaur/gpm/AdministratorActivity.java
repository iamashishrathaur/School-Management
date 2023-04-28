package com.rathaur.gpm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class AdministratorActivity extends AppCompatActivity {
    RelativeLayout back;
    String classSelection;
    final String [] classItem={"First Year", "Second Year","Third Year"};
    RelativeLayout notice,complaints,timetable,application,addStudent;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        getSupportActionBar().hide();
        notice=findViewById(R.id.card_administor_notice);
        complaints=findViewById(R.id.card_admistor_complaints);
        timetable=findViewById(R.id.card_admistor_timetable);
        application=findViewById(R.id.card_admistor_application);
        addStudent=findViewById(R.id.card_admistor_student);
        back=findViewById(R.id.admin_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), AddNotice.class));
            }
        });
        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           startActivity(new Intent(getApplicationContext(),ShowComplaints.class));
            }
        });
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            startActivity(new Intent(getApplicationContext(),TimetableYear.class));
//            finish();
                showDialog();
            }
        });
        application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), ShowApplication.class));
            }
        });
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void showDialog () {
        classSelection = classItem[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the class");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(classItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                classSelection = classItem[i];
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getApplicationContext(),UpdateTimeTable.class);
                intent.putExtra("timetable",classSelection);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();

    }

}