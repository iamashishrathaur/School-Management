package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Notice;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class AdminAddNoticeStudent extends AppCompatActivity {
    TextInputEditText subject,topic;
    TextView button;
    Dialog pdialog,sdialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        Objects.requireNonNull(getSupportActionBar()).hide();
        subject=findViewById(R.id.notice_subject);
        topic=findViewById(R.id.notice_topic);
        button=findViewById(R.id.submit_notice);
        pdialog=new Dialog(this);
        sdialog=new Dialog(this);
        pdialog.setContentView(R.layout.progress_dialog);
        sdialog.setContentView(R.layout.success_dialog);
        Objects.requireNonNull(pdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        Objects.requireNonNull(sdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        pdialog.setCanceledOnTouchOutside(false);
        sdialog.setCanceledOnTouchOutside(false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.add_notice_back_pressed);
        back.setOnClickListener(view -> onBackPressed());
        button.setOnClickListener(view -> {
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=firebaseDatabase.getReference("Notice");
            String sub= Objects.requireNonNull(subject.getText()).toString();
            String top= Objects.requireNonNull(topic.getText()).toString();
            Date date= Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String sdate= dateFormat.format(date);
            Notice notice=new Notice(sdate,sub,top);
            String uuid= UUID.randomUUID().toString();
            if (!sub.isEmpty()){
               if (!top.isEmpty()) {
                   pdialog.show();
                   databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                       @SuppressLint("SetTextI18n")
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           databaseReference.child("student").child(uuid).setValue(notice).addOnSuccessListener(unused -> {
                               pdialog.dismiss();
                               sdialog.show();
                               TextView textView=sdialog.findViewById(R.id.success_dialog_text);
                               textView.setText("Notice successfully saved");
                               TextView button=sdialog.findViewById(R.id.success_dialog_button);
                               button.setOnClickListener(v -> onBackPressed());
                           });
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {
                           Toast.makeText(AdminAddNoticeStudent.this, "try again", Toast.LENGTH_SHORT).show();
                       }
                   });
               }
               else {
                   Toast.makeText(AdminAddNoticeStudent.this, "fill notice topic", Toast.LENGTH_SHORT).show();
               }

            }else {
                Toast.makeText(AdminAddNoticeStudent.this, "fill notice subject", Toast.LENGTH_SHORT).show();
            }

        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        pdialog.dismiss();
        sdialog.dismiss();
    }
}