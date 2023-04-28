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

public class StudentApplication extends AppCompatActivity {
    TextInputEditText subject,resions;
    String senrollment,sname,smobile;
    Dialog dialogbox;
    Dialog dialog;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_application);
        getSupportActionBar().hide();
        subject = findViewById(R.id.application_subject_edittext);
        resions = findViewById(R.id.application_resion_edittext);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        sname = sharedPreferences.getString("name", "");
        senrollment = sharedPreferences.getString("enrollment", "");
        smobile = sharedPreferences.getString("mobile", "");
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.success_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        dialog= new Dialog(this);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        TextView dialogtext = dialogbox.findViewById(R.id.success_dialog_text);
        dialogtext.setText("Application successfully saved");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back = findViewById(R.id.student_application_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void applicatioSubmit(View view) {
        dialog.show();
        String s=subject.getText().toString();
        String r=resions.getText().toString();
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String sdate= dateFormat.format(date);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("sapplications");
        Applications applications=new Applications(sname,smobile,senrollment,s,r,sdate);
        databaseReference.orderByChild("enrollment").equalTo(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    databaseReference.child(senrollment).setValue(applications);
                    dialog.dismiss();
                    dialogbox.show();
                    TextView dialogbutton = dialogbox.findViewById(R.id.success_dialog_button);
                    dialogbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogbox.dismiss();
                            onBackPressed();
                        }
                    });
                }else {
                    dialog.dismiss();
                    Toast.makeText(StudentApplication.this, "Your Applications already Sent ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentApplication.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}