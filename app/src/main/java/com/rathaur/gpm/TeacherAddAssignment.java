package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.DatePickerFragment;
import com.rathaur.gpm.DataBaseModal.Assignment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TeacherAddAssignment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Spinner spinner;
    TextInputEditText subject,topic;
    TextView button,lastdate;
    String lastDate;
    final String [] Year={"First Year","Second Year","Third Year"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_assignment);
        Objects.requireNonNull(getSupportActionBar()).hide();
        spinner=findViewById(R.id.assignment_year);
        subject=findViewById(R.id.teacher_assignment_subject_edittext);
        topic=findViewById(R.id.teacher_assignment_topic_edittext);
        button=findViewById(R.id.teacher_add_assignment);
        lastdate=findViewById(R.id.last_time_assignment);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.teacher_assignment_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ArrayAdapter<String> year=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,Year);
        year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(year);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String years=spinner.getSelectedItem().toString();
                String sub= Objects.requireNonNull(subject.getText()).toString();
                String topics= Objects.requireNonNull(topic.getText()).toString();
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                String tname=sharedPreferences.getString("name","");
                String temail=sharedPreferences.getString("email","");
                String tenrollment=sharedPreferences.getString("enrollment","");
                String tprofession=sharedPreferences.getString("profession","");
                Date date= Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String sdate= dateFormat.format(date);
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference("assignment");
                Assignment assignment=new Assignment(tname,sub,years,topics,sdate,lastDate);
                Calendar calendar = Calendar.getInstance();
                String uid= String.valueOf(calendar.getTimeInMillis());
                databaseReference.child(years).child(uid).setValue(assignment);
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DATE,i2);
        lastDate = DateFormat.getDateInstance().format(calendar.getTime());
         lastdate.setText(lastDate);
    }

    public void chooseLastDate(View view) {
        DatePickerFragment datePickerFragment=new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(),"date picker");
    }
}