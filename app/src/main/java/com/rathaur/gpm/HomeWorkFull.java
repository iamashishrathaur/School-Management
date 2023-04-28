package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.Adepter.HomeworkAdepter;
import com.rathaur.gpm.Adepter.StudentVideo;

import org.w3c.dom.Text;

import java.util.Objects;

public class HomeWorkFull extends AppCompatActivity {
RelativeLayout back;
TextView sub,heading,content;
String senrollment;
RelativeLayout delete;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_full);
        Objects.requireNonNull(getSupportActionBar()).hide();
        back=findViewById(R.id.full_homework_back_pressed);
        sub=findViewById(R.id.show_subject_full);
        heading=findViewById(R.id.show_heading_full);
        content=findViewById(R.id.show_content_full);
        delete=findViewById(R.id.homework_delete);
        Intent intent=getIntent();
        String subject=intent.getStringExtra("subject");
        String hed=intent.getStringExtra("heading");
        String con=intent.getStringExtra("content");
        String t=intent.getStringExtra("time");
        String position=intent.getStringExtra("position");
        sub.setText(subject);
        heading.setText(hed);
        content.setText(con);
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Dialog dialogbox=new Dialog(this);
        dialogbox.setContentView(R.layout.success_dialog);
        dialogbox.setCanceledOnTouchOutside(false);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Dialog dialogp = new Dialog(this);
        dialogp.setContentView(R.layout.progress_dialog);
        dialogp.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogp.setCanceledOnTouchOutside(false);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        senrollment = sharedPreferences.getString("enrollment", "");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                TextView delete=dialog.findViewById(R.id.delete);
                TextView cancel=dialog.findViewById(R.id.delete_cancel);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dialogp.show();
                        FirebaseDatabase.getInstance().getReference().child("shomework").child(senrollment).child(position).removeValue(new DatabaseReference.CompletionListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                dialogp.dismiss();
                                dialogbox.show();
                                TextView ok=dialogbox.findViewById(R.id.success_dialog_button);
                                TextView text=dialogbox.findViewById(R.id.success_dialog_text);
                                text.setText("Homework successfully delete");
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                        dialogbox.dismiss();
                                    }
                                });
                            }
                        });
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}