package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.DatePickerFragment;
import com.rathaur.gpm.DataBaseModal.Homework;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class StudentHomework extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    RelativeLayout calender;
    TextInputEditText subject,heading,content;
    String Date;
    String senrollment;
    TextView textView,submit;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homework);
        getSupportActionBar().hide();
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        TextView text = dialog.findViewById(R.id.success_dialog_text);
        text.setText("Homework successfully saved");
        TextView button = dialog.findViewById(R.id.success_dialog_button);
        Dialog dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(dialogbox.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        calender=findViewById(R.id.calender_picker);
        textView=findViewById(R.id.date_time_formate);
        submit=findViewById(R.id.student_homework_submit);
        subject=findViewById(R.id.student_homework_edittext_subject);
        heading=findViewById(R.id.student_homework_edittext_heading);
        content=findViewById(R.id.student_homework_edittext_content);
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        senrollment=sharedPreferences.getString("enrollment","");
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.homework_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                String uid= String.valueOf(calendar.getTimeInMillis());
                String s= Objects.requireNonNull(subject.getText()).toString();
                String h= Objects.requireNonNull(heading.getText()).toString();
                String c= Objects.requireNonNull(content.getText()).toString();
                if (!s.isEmpty()){
                    if (!h.isEmpty()){
                        if (!c.isEmpty()){
                            dialogbox.show();
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = firebaseDatabase.getReference("shomework");
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Homework homework1=new Homework(s,h,c,Date);
                                    databaseReference.child(senrollment).child(uid).setValue(homework1);
                                    dialogbox.dismiss();
                                    dialog.show();
                                    button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
//
                                            onBackPressed();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    dialogbox.dismiss();
                                }
                            });
                        }
                    }
                }
            }
        });

      calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(),"date picker");

            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DATE,i2);
        Date= DateFormat.getDateInstance().format(calendar.getTime());
        textView.setText(Date);
    }
}