package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Notice;
import com.rathaur.gpm.databinding.ActivityAdminAddNoticeTeacherBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class AdminAddNoticeTeacher extends AppCompatActivity {

    ActivityAdminAddNoticeTeacherBinding binding;
    Dialog pdialog,sdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddNoticeTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        pdialog=new Dialog(this);
        sdialog=new Dialog(this);
        pdialog.setContentView(R.layout.progress_dialog);
        sdialog.setContentView(R.layout.success_dialog);
        Objects.requireNonNull(pdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        Objects.requireNonNull(sdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        pdialog.setCanceledOnTouchOutside(false);
        sdialog.setCanceledOnTouchOutside(false);
        binding.addTeacherNoticeBackPressed.setOnClickListener(v -> onBackPressed());
        binding.teacherNoticeSubmit.setOnClickListener(view -> {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Notice");
            String sub = Objects.requireNonNull(binding.teacherNoticeSubject.getText()).toString();
            String top = Objects.requireNonNull(binding.teachetNoticeTopic.getText()).toString();
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String sdate = dateFormat.format(date);
            Notice notice = new Notice(sdate, sub, top);
            String uuid = UUID.randomUUID().toString();
            if (!sub.isEmpty()) {
                if (!top.isEmpty()) {
                    pdialog.show();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("teacher").child(uuid).setValue(notice).addOnSuccessListener(unused -> {
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
                            Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                            pdialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "fill notice topic", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "fill notice subject", Toast.LENGTH_SHORT).show();
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