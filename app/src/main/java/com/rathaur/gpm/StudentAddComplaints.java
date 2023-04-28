package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Student;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StudentAddComplaints extends AppCompatActivity {
  TextView button;
  TextInputEditText complaints;
    DatabaseReference reference;
    RelativeLayout back;
    String senrollment,sname,semail,syear,smobile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_complaints);
        getSupportActionBar().hide();
        button=findViewById(R.id.student_add_complaints);
        complaints=findViewById(R.id.student_add_complaints_edittext);
        back=findViewById(R.id.student_add_complaints_back_pressed);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("scomplaints");
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        sname=sharedPreferences.getString("name","");
        semail=sharedPreferences.getString("email","");
        senrollment=sharedPreferences.getString("enrollment","");
        syear=sharedPreferences.getString("year","");
        smobile=sharedPreferences.getString("mobile","");
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);

        /// progress dialog
        Dialog pdialog=new Dialog(this);
        pdialog.setContentView(R.layout.progress_dialog);
        pdialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        pdialog.setCanceledOnTouchOutside(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!complaints.getText().toString().trim().isEmpty()){
                      String scomplaints=complaints.getText().toString();
                      reference.orderByChild("senrollment").equalTo(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                pdialog.show();
                                Date date= Calendar.getInstance().getTime();
                                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String sdate= dateFormat.format(date);
                                Student complaints = new Student(sname, smobile, senrollment, syear, scomplaints,sdate);
                                reference.child(senrollment).setValue(complaints).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        pdialog.dismiss();
                                        dialog.show();
                                        TextView dialogbutton = dialog.findViewById(R.id.success_dialog_button);
                                        dialogbutton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                                onBackPressed();
                                            }
                                        });
                                    }
                                });
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Your Previous Complaints Pending..", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
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