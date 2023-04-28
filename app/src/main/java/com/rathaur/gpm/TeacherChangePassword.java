package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TeacherChangePassword extends AppCompatActivity {
    TextInputEditText old,newPassword,confirm;
    TextView button;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_change_password);
        getSupportActionBar().hide();
        old=findViewById(R.id.teacher_old_password_change_password);
        newPassword=findViewById(R.id.teacher_new_password_change_password);
        confirm=findViewById(R.id.teacher_confirm_password_change_password);
        button=findViewById(R.id.teacher_add_password_submit);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.teacher_change_password_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String enrollment=sharedPreferences.getString("enrollment","");
        String password=sharedPreferences.getString("password","");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("teacher");

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        TextView dialogtext = dialog.findViewById(R.id.success_dialog_text);
        dialogtext.setText("Password Change successfully");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldp= Objects.requireNonNull(old.getText()).toString().trim();
                String newp= Objects.requireNonNull(newPassword.getText()).toString().trim();
                String confirmp= Objects.requireNonNull(confirm.getText()).toString().trim();

                if (!oldp.isEmpty()){
                    if (!newp.isEmpty()){
                        if (!confirmp.isEmpty()){
                            if (password.equals(oldp)){
                                if (newp.equals(confirmp)){
                                    final Map<String, Object> smap = new HashMap<>();
                                    smap.put("tpassword", newp);
                                    databaseReference.child("teacher").orderByChild(enrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            databaseReference.child(enrollment).updateChildren(smap);
                                            dialog.show();
                                           TextView dialogbutton = dialog.findViewById(R.id.success_dialog_button);
                                             dialogbutton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                                    editor.clear();
                                                    editor.apply();
                                                    Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                }
                                            });

                                           // Toast.makeText(getApplicationContext(), "Successfully update", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "new password and confirm password not same", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "wrong old password", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "enter confirm password", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "enter new password", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "end old password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}