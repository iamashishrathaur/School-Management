package com.rathaur.gpm;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.ComplaintsAdepter;
import com.rathaur.gpm.DataBase.Student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StudentComplents extends AppCompatActivity {
    TextView cencel;
    TextView submit;
    EditText complaints;
    RecyclerView recyclerView;
    ComplaintsAdepter adepter;
    String senrollment,sname,semail,syear,smobile;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_complents);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.complaints_showing_recyclerView);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("scomplaints");
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        sname=sharedPreferences.getString("sname","");
        semail=sharedPreferences.getString("semail","");
        senrollment=sharedPreferences.getString("senrollment","");
        syear=sharedPreferences.getString("syear","");
        smobile=sharedPreferences.getString("smobile","");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Student> complaints=new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(FirebaseDatabase.getInstance()
                .getReference("scomplaints").orderByChild(senrollment), Student.class)
                .build();

        adepter=new ComplaintsAdepter(complaints);
        recyclerView.setAdapter(adepter);


    }

    public void fillComplent(View view) {
        Dialog dialog=new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.complaints_diolog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
         cencel= dialog.findViewById(R.id.dilog_cencel);
         submit=dialog.findViewById(R.id.dilog_submit);
         complaints=dialog.findViewById(R.id.dialog_complaints);
         cencel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               dialog.dismiss();
             }
         });
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String  scomplaints=complaints.getText().toString();
                 if (!scomplaints.trim().isEmpty())
                     {
                         reference.orderByChild("senrollment").equalTo(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 if (!snapshot.exists()){
                                     Date date= Calendar.getInstance().getTime();
                                     SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                     String sdate= dateFormat.format(date);
                                     Student complaints = new Student(sname, smobile, senrollment, syear, scomplaints,sdate);
                                     reference.child(senrollment).setValue(complaints);
                                     Toast.makeText(StudentComplents.this, "Complaints Successfully sent", Toast.LENGTH_SHORT).show();
                                     dialog.dismiss();
                                 }
                                 else {
                                     Toast.makeText(StudentComplents.this, "Your Previous Complaints Pending..", Toast.LENGTH_LONG).show();
                                     dialog.dismiss();
                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {
                                 Toast.makeText(StudentComplents.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         });
                     }
             }
         });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adepter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adepter.startListening();
    }
}