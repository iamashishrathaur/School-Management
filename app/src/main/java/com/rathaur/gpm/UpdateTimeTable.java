package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class UpdateTimeTable extends AppCompatActivity {

    Dialog dialog;
    EditText editText;
    TextView cancel,submit;

    String timetable;
    String Year;
    MaterialTimePicker materialTimePicker;
    Calendar calendar;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    TextView Sun1,Sun2,Sun3,Sun4,Sun5,Sun6,Sun7,
            Mon1,Mon2,Mon3,Mon4,Mon5,Mon6,Mon7,Mon8,
            Tue1,Tue2,Tue3,Tue4,Tue5,Tue6,Tue7,Tue8,
            Wed1,Wed2,Wed3,Wed4,Wed5,Wed6,Wed7,Wed8,
            Thu1,Thu2,Thu3,Thu4,Thu5,Thu6,Thu7,Thu8,
            Fri1,Fri2,Fri3,Fri4,Fri5,Fri6,Fri7,Fri8,
            Sat1,Sat2,Sat3,Sat4,Sat5,Sat6,Sat7,Sat8;

    TextView monTeach1,monTeach2,monTeach3,monTeach4,monTeach5,monTeach6,monTeach7,monTeach8,
            tueTeach1,tueTeach2,tueTeach3,tueTeach4,tueTeach5,tueTeach6,tueTeach7,tueTeach8,
            wedTeach1,wedTeach2,wedTeach3,wedTeach4,wedTeach5,wedTeach6,wedTeach7,wedTeach8,
            thuTeach1,thuTeach2,thuTeach3,thuTeach4,thuTeach5,thuTeach6,thuTeach7,thuTeach8,
           friTeach1,friTeach2,friTeach3,friTeach4,friTeach5,friTeach6,friTeach7,friTeach8,
            satTeach1,satTeach2,satTeach3,satTeach4,satTeach5,satTeach6,satTeach7,satTeach8;


    TextView timeslot1,timeslot2,timeslot3,timeslot4,
            timeslot5,timeslot6,timeslot7,timeslot8,
            timeslot9,timeslot10,timeslot11,timeslot12,
            timeslot13,timeslot14,timeslot15,timeslot16;

    ImageView image1,image2,image3,image4,
            image5,image6,image7,image8,image9,image10,
            image11,image12,image13,image14,image15,image16;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time_table);
        Objects.requireNonNull(getSupportActionBar()).hide();
        image1=findViewById(R.id.timage1);
        image2=findViewById(R.id.timage2);
        image3=findViewById(R.id.timage3);
        image4=findViewById(R.id.timage4);
        image5=findViewById(R.id.timage5);
        image6=findViewById(R.id.timage6);
        image7=findViewById(R.id.timage7);
        image8=findViewById(R.id.timage8);
        image9=findViewById(R.id.timage9);
        image10=findViewById(R.id.timage10);
        image11=findViewById(R.id.timage11);
        image12=findViewById(R.id.timage12);
        image13=findViewById(R.id.timage13);
        image14=findViewById(R.id.timage14);
        image15=findViewById(R.id.timage15);
        image16=findViewById(R.id.timage16);

        timeslot1=findViewById(R.id.timeslot1);
        timeslot2=findViewById(R.id.timeslot2);
        timeslot3=findViewById(R.id.timeslot3);
        timeslot4=findViewById(R.id.timeslot4);
        timeslot5=findViewById(R.id.timeslot5);
        timeslot6=findViewById(R.id.timeslot6);
        timeslot7=findViewById(R.id.timeslot7);
        timeslot8=findViewById(R.id.timeslot8);
        timeslot9=findViewById(R.id.timeslot9);
        timeslot10=findViewById(R.id.timeslot10);
        timeslot11=findViewById(R.id.timeslot11);
        timeslot12=findViewById(R.id.timeslot12);
        timeslot13=findViewById(R.id.timeslot13);
        timeslot14=findViewById(R.id.timeslot14);
        timeslot15=findViewById(R.id.timeslot15);
        timeslot16=findViewById(R.id.timeslot16);

        Mon1=findViewById(R.id.monday_subject1);
        Mon2=findViewById(R.id.monday_subject2);
        Mon3=findViewById(R.id.monday_subject3);
        Mon4=findViewById(R.id.monday_subject4);
        Mon5=findViewById(R.id.monday_subject5);
        Mon6=findViewById(R.id.monday_subject6);
        Mon7=findViewById(R.id.monday_subject7);
        Mon8=findViewById(R.id.monday_subject8);
        Tue1=findViewById(R.id.tuesday_subject1);
        Tue2=findViewById(R.id.tuesday_subject2);
        Tue3=findViewById(R.id.tuesday_subject3);
        Tue4=findViewById(R.id.tuesday_subject4);
        Tue5=findViewById(R.id.tuesday_subject5);
        Tue6=findViewById(R.id.tuesday_subject6);
        Tue7=findViewById(R.id.tuesday_subject7);
        Tue8=findViewById(R.id.tuesday_subject8);
        Wed1=findViewById(R.id.wednesday_subject1);
        Wed2=findViewById(R.id.wednesday_subject2);
        Wed3=findViewById(R.id.wednesday_subject3);
        Wed4=findViewById(R.id.wednesday_subject4);
        Wed5=findViewById(R.id.wednesday_subject5);
        Wed6=findViewById(R.id.wednesday_subject6);
        Wed7=findViewById(R.id.wednesday_subject7);
        Wed8=findViewById(R.id.wednesday_subject8);
        Thu1=findViewById(R.id.thursday_subject1);
        Thu2=findViewById(R.id.thursday_subject2);
        Thu3=findViewById(R.id.thursday_subject3);
        Thu4=findViewById(R.id.thursday_subject4);
        Thu5=findViewById(R.id.thursday_subject5);
        Thu6=findViewById(R.id.thursday_subject6);
        Thu7=findViewById(R.id.thursday_subject7);
        Thu8=findViewById(R.id.thursday_subject8);
        Fri1=findViewById(R.id.friday_subject1);
        Fri2=findViewById(R.id.friday_subject2);
        Fri3=findViewById(R.id.friday_subject3);
        Fri4=findViewById(R.id.friday_subject4);
        Fri5=findViewById(R.id.friday_subject5);
        Fri6=findViewById(R.id.friday_subject6);
        Fri7=findViewById(R.id.friday_subject7);
        Fri8=findViewById(R.id.friday_subject8);
        Sat1=findViewById(R.id.saturday_subject1);
        Sat2=findViewById(R.id.saturday_subject2);
        Sat3=findViewById(R.id.saturday_subject3);
        Sat4=findViewById(R.id.saturday_subject4);
        Sat5=findViewById(R.id.saturday_subject5);
        Sat6=findViewById(R.id.saturday_subject6);
        Sat7=findViewById(R.id.saturday_subject7);
        Sat8=findViewById(R.id.saturday_subject8);

        monTeach1=findViewById(R.id.monday_teacher1);
        monTeach2=findViewById(R.id.monday_teacher2);
        monTeach3=findViewById(R.id.monday_teacher3);
        monTeach4=findViewById(R.id.monday_teacher4);
        monTeach5=findViewById(R.id.monday_teacher5);
        monTeach6=findViewById(R.id.monday_teacher6);
        monTeach7=findViewById(R.id.monday_teacher7);
        monTeach8=findViewById(R.id.monday_teacher8);
        tueTeach1=findViewById(R.id.tuesday_teacher1);
        tueTeach2=findViewById(R.id.tuesday_teacher2);
        tueTeach3=findViewById(R.id.tuesday_teacher3);
        tueTeach4=findViewById(R.id.tuesday_teacher4);
        tueTeach5=findViewById(R.id.tuesday_teacher5);
        tueTeach6=findViewById(R.id.tuesday_teacher6);
        tueTeach7=findViewById(R.id.tuesday_teacher7);
        tueTeach8=findViewById(R.id.tuesday_teacher8);
        wedTeach1=findViewById(R.id.wednesday_teacher1);
        wedTeach2=findViewById(R.id.wednesday_teacher2);
        wedTeach3=findViewById(R.id.wednesday_teacher3);
        wedTeach4=findViewById(R.id.wednesday_teacher4);
        wedTeach5=findViewById(R.id.wednesday_teacher5);
        wedTeach6=findViewById(R.id.wednesday_teacher6);
        wedTeach7=findViewById(R.id.wednesday_teacher7);
        wedTeach8=findViewById(R.id.wednesday_teacher8);
        thuTeach1=findViewById(R.id.thursday_teacher1);
        thuTeach2=findViewById(R.id.thursday_teacher2);
        thuTeach3=findViewById(R.id.thursday_teacher3);
        thuTeach4=findViewById(R.id.thursday_teacher4);
        thuTeach5=findViewById(R.id.thursday_teacher5);
        thuTeach6=findViewById(R.id.thursday_teacher6);
        thuTeach7=findViewById(R.id.thursday_teacher7);
        thuTeach8=findViewById(R.id.thursday_teacher8);
        friTeach1=findViewById(R.id.friday_teacher1);
        friTeach2=findViewById(R.id.friday_teacher2);
        friTeach3=findViewById(R.id.friday_teacher3);
        friTeach4=findViewById(R.id.friday_teacher4);
        friTeach5=findViewById(R.id.friday_teacher5);
        friTeach6=findViewById(R.id.friday_teacher6);
        friTeach7=findViewById(R.id.friday_teacher7);
        friTeach8=findViewById(R.id.friday_teacher8);
        satTeach1=findViewById(R.id.saturday_teacher1);
        satTeach2=findViewById(R.id.saturday_teacher2);
        satTeach3=findViewById(R.id.saturday_teacher3);
        satTeach4=findViewById(R.id.saturday_teacher4);
        satTeach5=findViewById(R.id.saturday_teacher5);
        satTeach6=findViewById(R.id.saturday_teacher6);
        satTeach7=findViewById(R.id.saturday_teacher7);
        satTeach8=findViewById(R.id.saturday_teacher8);

        Intent intent=getIntent();
        Year = intent.getStringExtra("timetable");
        //Toast.makeText(this, ""+Year, Toast.LENGTH_SHORT).show();
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("timeTable"+Year);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                   String time1=snapshot.child("time1").getValue(String.class);
                   String time2=snapshot.child("time2").getValue(String.class);
                   String time3=snapshot.child("time3").getValue(String.class);
                   String time4=snapshot.child("time4").getValue(String.class);
                   String time5=snapshot.child("time5").getValue(String.class);
                   String time6=snapshot.child("time6").getValue(String.class);
                   String time7=snapshot.child("time7").getValue(String.class);
                   String time8=snapshot.child("time8").getValue(String.class);
                   String time9=snapshot.child("time9").getValue(String.class);
                   String time10=snapshot.child("time10").getValue(String.class);
                   String time11=snapshot.child("time11").getValue(String.class);
                   String time12=snapshot.child("time12").getValue(String.class);
                   String time13=snapshot.child("time13").getValue(String.class);
                   String time14=snapshot.child("time14").getValue(String.class);
                   String time15=snapshot.child("time15").getValue(String.class);
                   String time16=snapshot.child("time16").getValue(String.class);

                   if(time1 != null){
                       image1.setVisibility(View.GONE);
                       timeslot1.setText(time1);
                   }
                   if (time2 !=null){
                       image2.setVisibility(View.GONE);
                       timeslot2.setText(time2);
                   }
                   if (time3 !=null){
                       image3.setVisibility(View.GONE);
                       timeslot3.setText(time3);
                   }
                   if (time4 !=null) {
                       image4.setVisibility(View.GONE);
                       timeslot4.setText(time4);
                   }
                   if (time5 !=null){
                       image5.setVisibility(View.GONE);
                       timeslot5.setText(time5);
                   }
                   if (time6 !=null){
                       image6.setVisibility(View.GONE);
                       timeslot6.setText(time6);
                   }
                   if (time7 !=null){
                       image7.setVisibility(View.GONE);
                       timeslot7.setText(time7);
                   }
                   if (time8 !=null){
                       image8.setVisibility(View.GONE);
                       timeslot8.setText(time8);
                   }
                   if (time9 !=null){
                       image9.setVisibility(View.GONE);
                       timeslot9.setText(time9);
                   }
                   if (time10 !=null){
                       image10.setVisibility(View.GONE);
                       timeslot10.setText(time10);
                   }
                   if (time11 !=null){
                       image11.setVisibility(View.GONE);
                       timeslot11.setText(time11);
                   }

                   if (time12 !=null){
                       image12.setVisibility(View.GONE);
                       timeslot12.setText(time12);
                   }
                   if (time13 !=null){
                       image13.setVisibility(View.GONE);
                       timeslot13.setText(time13);
                   }
                   if (time14 !=null){
                       image14.setVisibility(View.GONE);
                       timeslot14.setText(time14);
                   }
                   if (time15 !=null){
                       image15.setVisibility(View.GONE);
                       timeslot15.setText(time15);
                   }
                   if (time16 !=null){
                       image16.setVisibility(View.GONE);
                       timeslot16.setText(time16);
                   }

                   Mon1.setText(snapshot.child("Mon1").getValue(String.class));
                   Mon2.setText(snapshot.child("Mon2").getValue(String.class));
                   Mon3.setText(snapshot.child("Mon3").getValue(String.class));
                   Mon4.setText(snapshot.child("Mon4").getValue(String.class));
                   Mon5.setText(snapshot.child("Mon5").getValue(String.class));
                   Mon6.setText(snapshot.child("Mon6").getValue(String.class));
                   Mon7.setText(snapshot.child("Mon7").getValue(String.class));
                   Mon8.setText(snapshot.child("Mon8").getValue(String.class));
                   Tue1.setText(snapshot.child("Tue1").getValue(String.class));
                   Tue2.setText(snapshot.child("Tue2").getValue(String.class));
                   Tue3.setText(snapshot.child("Tue3").getValue(String.class));
                   Tue4.setText(snapshot.child("Tue4").getValue(String.class));
                   Tue5.setText(snapshot.child("Tue5").getValue(String.class));
                   Tue6.setText(snapshot.child("Tue6").getValue(String.class));
                   Tue7.setText(snapshot.child("Tue7").getValue(String.class));
                   Tue8.setText(snapshot.child("Tue8").getValue(String.class));
                   Wed1.setText(snapshot.child("Wed1").getValue(String.class));
                   Wed2.setText(snapshot.child("Wed2").getValue(String.class));
                   Wed3.setText(snapshot.child("Wed3").getValue(String.class));
                   Wed4.setText(snapshot.child("Wed4").getValue(String.class));
                   Wed5.setText(snapshot.child("Wed5").getValue(String.class));
                   Wed6.setText(snapshot.child("Wed6").getValue(String.class));
                   Wed7.setText(snapshot.child("Wed7").getValue(String.class));
                   Wed8.setText(snapshot.child("Wed8").getValue(String.class));
                   Thu1.setText(snapshot.child("Thu1").getValue(String.class));
                   Thu2.setText(snapshot.child("Thu2").getValue(String.class));
                   Thu3.setText(snapshot.child("Thu3").getValue(String.class));
                   Thu4.setText(snapshot.child("Thu4").getValue(String.class));
                   Thu5.setText(snapshot.child("Thu5").getValue(String.class));
                   Thu6.setText(snapshot.child("Thu6").getValue(String.class));
                   Thu7.setText(snapshot.child("Thu7").getValue(String.class));
                   Thu8.setText(snapshot.child("Thu8").getValue(String.class));
                   Fri1.setText(snapshot.child("Fri1").getValue(String.class));
                   Fri2.setText(snapshot.child("Fri2").getValue(String.class));
                   Fri3.setText(snapshot.child("Fri3").getValue(String.class));
                   Fri4.setText(snapshot.child("Fri4").getValue(String.class));
                   Fri5.setText(snapshot.child("Fri5").getValue(String.class));
                   Fri6.setText(snapshot.child("Fri6").getValue(String.class));
                   Fri7.setText(snapshot.child("Fri7").getValue(String.class));
                   Fri8.setText(snapshot.child("Fri8").getValue(String.class));
                   Sat1.setText(snapshot.child("Sat1").getValue(String.class));
                   Sat2.setText(snapshot.child("Sat2").getValue(String.class));
                   Sat3.setText(snapshot.child("Sat3").getValue(String.class));
                   Sat4.setText(snapshot.child("Sat4").getValue(String.class));
                   Sat5.setText(snapshot.child("Sat5").getValue(String.class));
                   Sat6.setText(snapshot.child("Sat6").getValue(String.class));
                   Sat7.setText(snapshot.child("Sat7").getValue(String.class));
                   Sat8.setText(snapshot.child("Sat8").getValue(String.class));


                   monTeach1.setText(snapshot.child("monTeach1").getValue(String.class));
                   monTeach2.setText(snapshot.child("monTeach2").getValue(String.class));
                   monTeach3.setText(snapshot.child("monTeach3").getValue(String.class));
                   monTeach4.setText(snapshot.child("monTeach4").getValue(String.class));
                   monTeach5.setText(snapshot.child("monTeach5").getValue(String.class));
                   monTeach6.setText(snapshot.child("monTeach6").getValue(String.class));
                   monTeach7.setText(snapshot.child("monTeach7").getValue(String.class));
                   monTeach8.setText(snapshot.child("monTeach8").getValue(String.class));
                   tueTeach1.setText(snapshot.child("tueTeach1").getValue(String.class));
                   tueTeach2.setText(snapshot.child("tueTeach2").getValue(String.class));
                   tueTeach3.setText(snapshot.child("tueTeach3").getValue(String.class));
                   tueTeach4.setText(snapshot.child("tueTeach4").getValue(String.class));
                   tueTeach5.setText(snapshot.child("tueTeach5").getValue(String.class));
                   tueTeach6.setText(snapshot.child("tueTeach6").getValue(String.class));
                   tueTeach7.setText(snapshot.child("tueTeach7").getValue(String.class));
                   tueTeach8.setText(snapshot.child("tueTeach8").getValue(String.class));
                   wedTeach1.setText(snapshot.child("wedTeach1").getValue(String.class));
                   wedTeach2.setText(snapshot.child("wedTeach2").getValue(String.class));
                   wedTeach3.setText(snapshot.child("wedTeach3").getValue(String.class));
                   wedTeach4.setText(snapshot.child("wedTeach4").getValue(String.class));
                   wedTeach5.setText(snapshot.child("wedTeach5").getValue(String.class));
                   wedTeach6.setText(snapshot.child("wedTeach6").getValue(String.class));
                   wedTeach7.setText(snapshot.child("wedTeach7").getValue(String.class));
                   wedTeach8.setText(snapshot.child("wedTeach8").getValue(String.class));
                   thuTeach1.setText(snapshot.child("thuTeach1").getValue(String.class));
                   thuTeach2.setText(snapshot.child("tueTeach2").getValue(String.class));
                   thuTeach3.setText(snapshot.child("thuTeach3").getValue(String.class));
                   thuTeach4.setText(snapshot.child("thuTeach4").getValue(String.class));
                   thuTeach5.setText(snapshot.child("thuTeach5").getValue(String.class));
                   thuTeach6.setText(snapshot.child("thuTeach6").getValue(String.class));
                   thuTeach7.setText(snapshot.child("thuTeach7").getValue(String.class));
                   thuTeach8.setText(snapshot.child("thuTeach8").getValue(String.class));
                   friTeach1.setText(snapshot.child("friTeach1").getValue(String.class));
                   friTeach2.setText(snapshot.child("friTeach2").getValue(String.class));
                   friTeach3.setText(snapshot.child("friTeach3").getValue(String.class));
                   friTeach4.setText(snapshot.child("friTeach4").getValue(String.class));
                   friTeach5.setText(snapshot.child("friTeach5").getValue(String.class));
                   friTeach6.setText(snapshot.child("friTeach6").getValue(String.class));
                   friTeach7.setText(snapshot.child("friTeach7").getValue(String.class));
                   friTeach8.setText(snapshot.child("friTeach8").getValue(String.class));
                   satTeach1.setText(snapshot.child("satTeach1").getValue(String.class));
                   satTeach2.setText(snapshot.child("satTeach2").getValue(String.class));
                   satTeach3.setText(snapshot.child("satTeach3").getValue(String.class));
                   satTeach4.setText(snapshot.child("satTeach4").getValue(String.class));
                   satTeach5.setText(snapshot.child("satTeach5").getValue(String.class));
                   satTeach6.setText(snapshot.child("satTeach6").getValue(String.class));
                   satTeach7.setText(snapshot.child("satTeach7").getValue(String.class));
                   satTeach8.setText(snapshot.child("satTeach8").getValue(String.class));
               }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(UpdateTimeTable.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
           }
       });

        //   dialog here




        dialog=new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_popup_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editText=dialog.findViewById(R.id.dialog_timetable);
        cancel=dialog.findViewById(R.id.timetable_cancel);
        submit=dialog.findViewById(R.id.timetable_submit);

       //  database

       // click here
        timeslot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time1").setValue(time);
                        timeslot1.setText(time);
                        image1.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time2").setValue(time);
                        timeslot2.setText(time);
                        image2.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time3").setValue(time);
                        timeslot3.setText(time);
                        image3.setVisibility(View.GONE);
                    }
                });
            }
        });

        timeslot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time4").setValue(time);
                        timeslot4.setText(time);
                        image4.setVisibility(View.GONE);
                    }
                });
            }
        });

        timeslot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time5").setValue(time);
                        timeslot5.setText(time);
                        image5.setVisibility(View.GONE);
                    }
                });
            }
        });

        timeslot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time6").setValue(time);
                        timeslot6.setText(time);
                        image6.setVisibility(View.GONE);
                    }
                });
            }
        });

        timeslot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time7").setValue(time);
                        timeslot7.setText(time);
                        image7.setVisibility(View.GONE);
                    }
                });
            }
        });

        timeslot8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time8").setValue(time);
                        timeslot8.setText(time);
                        image8.setVisibility(View.GONE);
                    }
                });
            }
        });

        timeslot9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time9").setValue(time);
                        timeslot9.setText(time);
                        image9.setVisibility(View.GONE);
                    }
                });
            }
        });

        timeslot10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();

                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time10").setValue(time);
                        timeslot10.setText(time);
                        image10.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time11").setValue(time);
                        timeslot11.setText(time);
                        image11.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time12").setValue(time);
                        timeslot12.setText(time);
                        image12.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time13").setValue(time);
                        timeslot13.setText(time);
                        image13.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time14").setValue(time);
                        timeslot14.setText(time);
                        image14.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR_OF_DAY)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time15").setValue(time);
                        timeslot15.setText(time);
                        image15.setVisibility(View.GONE);
                    }
                });
            }
        });
        timeslot16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                materialTimePicker =new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(calendar.get(Calendar.HOUR)).setMinute(calendar.get(Calendar.MINUTE)).build();
                materialTimePicker.show(getSupportFragmentManager(),"Time");
                materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int hour=materialTimePicker.getHour();
                        int min=materialTimePicker.getMinute();
                        String time=String.format(Locale.getDefault(),"%02d:%02d",hour,min);
                        databaseReference.child("time16").setValue(time);
                        timeslot16.setText(time);
                        image16.setVisibility(View.GONE);
                    }
                });
            }
        });
        Mon1.setOnClickListener(view -> {
            dialog.show();
            submit.setOnClickListener(view1 -> {
               timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
               databaseReference.child("Mon1").setValue(timetable);
               Mon1.setText(timetable);
               dialog.dismiss();
            });
            cancel.setOnClickListener(view12 -> dialog.dismiss());
        });
        Mon2.setOnClickListener(view -> {
            dialog.show();
            submit.setOnClickListener(view13 -> {
                timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                databaseReference.child("Mon2").setValue(timetable);
                Mon2.setText(timetable);
                dialog.dismiss();
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        });
        Mon2.setOnClickListener(view -> {
            dialog.show();
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                    databaseReference.child("Mon2").setValue(timetable);
                    Mon2.setText(timetable);
                    dialog.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        });
        Mon3.setOnClickListener(view -> {
            dialog.show();
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                    databaseReference.child("Mon3").setValue(timetable);
                    Mon3.setText(timetable);
                    dialog.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        });
        Mon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Mon4").setValue(timetable);
                        Mon4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Mon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Mon5").setValue(timetable);
                        Mon5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Mon6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Mon6").setValue(timetable);
                        Mon6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Mon7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Mon7").setValue(timetable);
                        Mon7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Mon8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Mon8").setValue(timetable);
                        Mon8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue1").setValue(timetable);
                        Tue1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue2").setValue(timetable);
                        Tue2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue3").setValue(timetable);
                        Tue3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue4").setValue(timetable);
                        Tue4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue5").setValue(timetable);
                        Tue5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue6").setValue(timetable);
                        Tue6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue7").setValue(timetable);
                        Tue7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Tue8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Tue8").setValue(timetable);
                        Tue8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        Wed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed1").setValue(timetable);
                        Wed1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Wed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed2").setValue(timetable);
                        Wed2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Wed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed3").setValue(timetable);
                        Wed3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Wed4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed4").setValue(timetable);
                        Wed4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Wed5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed5").setValue(timetable);
                        Wed5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Wed6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed6").setValue(timetable);
                        Wed6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Wed7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed7").setValue(timetable);
                        Wed7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Wed8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Wed8").setValue(timetable);
                        Wed8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        Thu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu1").setValue(timetable);
                        Thu1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Thu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu2").setValue(timetable);
                        Thu2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Thu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu3").setValue(timetable);
                        Thu3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Thu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu4").setValue(timetable);
                        Thu4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Thu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu5").setValue(timetable);
                        Thu5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Thu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu6").setValue(timetable);
                        Thu6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Thu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu7").setValue(timetable);
                        Thu7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Thu8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Thu8").setValue(timetable);
                        Thu8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        Fri1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri1").setValue(timetable);
                        Fri1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Fri2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri2").setValue(timetable);
                        Fri2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Fri3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri3").setValue(timetable);
                        Fri3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Fri4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri4").setValue(timetable);
                        Fri4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Fri5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri5").setValue(timetable);
                        Fri5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Fri6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri6").setValue(timetable);
                        Fri6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Fri7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri7").setValue(timetable);
                        Fri7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Fri8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Fri8").setValue(timetable);
                        Fri8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat1").setValue(timetable);
                        Sat1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat2").setValue(timetable);
                        Sat2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat3").setValue(timetable);
                        Sat3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat4").setValue(timetable);
                        Sat4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat5").setValue(timetable);
                        Sat5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat6").setValue(timetable);
                        Sat6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat7").setValue(timetable);
                        Sat7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        Sat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("Sat8").setValue(timetable);
                        Sat8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });




        // teacher    here

        monTeach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach1").setValue(timetable);
                        monTeach1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        monTeach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach2").setValue(timetable);
                        monTeach2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        monTeach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach3").setValue(timetable);
                        monTeach3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        monTeach4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach4").setValue(timetable);
                        monTeach4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        monTeach5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach5").setValue(timetable);
                        monTeach5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        monTeach6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach6").setValue(timetable);
                        monTeach6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        monTeach7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach7").setValue(timetable);
                        monTeach7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        monTeach8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("monTeach8").setValue(timetable);
                        monTeach8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach1").setValue(timetable);
                        tueTeach1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach2").setValue(timetable);
                        tueTeach2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach3").setValue(timetable);
                        tueTeach3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach4").setValue(timetable);
                        tueTeach4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach5").setValue(timetable);
                        tueTeach5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach6").setValue(timetable);
                        tueTeach6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach7").setValue(timetable);
                        tueTeach7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        tueTeach8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("tueTeach8").setValue(timetable);
                        tueTeach8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        wedTeach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach1").setValue(timetable);
                        wedTeach1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        wedTeach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach2").setValue(timetable);
                        wedTeach2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        wedTeach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach3").setValue(timetable);
                        wedTeach3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        wedTeach4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach4").setValue(timetable);
                        wedTeach4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        wedTeach5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach5").setValue(timetable);
                        wedTeach5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        wedTeach6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach6").setValue(timetable);
                        wedTeach6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        wedTeach7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach7").setValue(timetable);
                        wedTeach7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        wedTeach8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("wedTeach8").setValue(timetable);
                        wedTeach8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach1").setValue(timetable);
                        thuTeach1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach2").setValue(timetable);
                        thuTeach2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach3").setValue(timetable);
                        thuTeach3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach4").setValue(timetable);
                        thuTeach4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach5").setValue(timetable);
                        thuTeach5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach6").setValue(timetable);
                        thuTeach6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach7").setValue(timetable);
                        thuTeach7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        thuTeach8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("thuTeach8").setValue(timetable);
                        thuTeach8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        friTeach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach1").setValue(timetable);
                        friTeach1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        friTeach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach2").setValue(timetable);
                        friTeach2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        friTeach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach3").setValue(timetable);
                        friTeach3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        friTeach4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach4").setValue(timetable);
                        friTeach4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        friTeach5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach5").setValue(timetable);
                        friTeach5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        friTeach6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach6").setValue(timetable);
                        friTeach6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        friTeach7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach7").setValue(timetable);
                        friTeach7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        friTeach8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("friTeach8").setValue(timetable);
                        friTeach8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach1").setValue(timetable);
                        satTeach1.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach2").setValue(timetable);
                        satTeach2.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach3").setValue(timetable);
                        satTeach3.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach4").setValue(timetable);
                        satTeach4.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach5").setValue(timetable);
                        satTeach5.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach6").setValue(timetable);
                        satTeach6.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach7").setValue(timetable);
                        satTeach7.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        satTeach8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timetable=editText.getText().toString().toUpperCase(Locale.ROOT);
                        databaseReference.child("satTeach8").setValue(timetable);
                        satTeach8.setText(timetable);
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    public void pickTime(TextView textView,String slotname){
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                String time=String.format(Locale.getDefault(),"%02d:%02d",h,m);

            }
        },0,0,true);
        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}