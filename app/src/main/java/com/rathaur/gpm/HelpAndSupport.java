package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.DataBaseModal.Help;

import java.util.Objects;

public class HelpAndSupport extends AppCompatActivity {
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    RelativeLayout other;
    String problem="";
    EditText editText;
    TextView cancel, submit;
    String enrollment;
    String name;
    TextView send;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);
        Objects.requireNonNull(getSupportActionBar()).hide();
        checkBox1 = findViewById(R.id.checkbox_help1);
        checkBox2 = findViewById(R.id.checkbox_help2);
        checkBox3 = findViewById(R.id.checkbox_help3);
        checkBox4 = findViewById(R.id.checkbox_help4);
        checkBox5 = findViewById(R.id.checkbox_help5);
        send = findViewById(R.id.send_report);
        other = findViewById(R.id.help_other);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.bootom_sheet);
        editText = dialog.findViewById(R.id.student_help_edittext);
        cancel = dialog.findViewById(R.id.student_help_cancel);
        submit = dialog.findViewById(R.id.student_help_submit);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.help_back_pressed);
        back.setOnClickListener(view -> onBackPressed());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Help");
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            name = sharedPreferences.getString("name", "");
           enrollment = sharedPreferences.getString("enrollment", "");
        Dialog dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.success_dialog);
        Objects.requireNonNull(dialog1.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog1.setCanceledOnTouchOutside(false);
        TextView text = dialog1.findViewById(R.id.success_dialog_text);
        text.setText("Report successfully saved");

        other.setOnClickListener(view -> {
             dialog.show();
              submit.setOnClickListener(view14 -> {
                  String help = editText.getText().toString();
                  if (!help.isEmpty()) {
                      Help helps = new Help(name, enrollment, help);
                      databaseReference.child(enrollment).child(String.valueOf(System.currentTimeMillis())).setValue(helps).addOnCompleteListener(task -> {
                          dialog1.show();
                          TextView button = dialog1.findViewById(R.id.success_dialog_button);
                          button.setOnClickListener(view12 -> onBackPressed());
                      });
           dialog.dismiss();
                  } else {
                      Toast.makeText(HelpAndSupport.this, "fill problem", Toast.LENGTH_SHORT).show();
                  }
              });
              cancel.setOnClickListener(view13 -> dialog.dismiss());
        });
        send.setOnClickListener(view -> {
            if (checkBox1.isChecked()){
                problem = problem+"1"+" ";
            }
            if (checkBox2.isChecked()){
                problem = problem+"2"+" ";
            }
            if (checkBox3.isChecked()){
                problem = problem+"3"+" ";
            }
            if (checkBox4.isChecked()){
                problem = problem+"4"+" ";
            }
            if (checkBox5.isChecked()){
                problem = problem+"5"+" ";
            }
            if (!problem.isEmpty()) {
                Help helps = new Help(name, enrollment, problem);
                databaseReference.child(enrollment).child(String.valueOf(System.currentTimeMillis())).setValue(helps).addOnCompleteListener(task -> {
                    dialog1.show();
                    TextView button = dialog1.findViewById(R.id.success_dialog_button);
                    button.setOnClickListener(view1 -> onBackPressed());
                });

            } else {
                Toast.makeText(HelpAndSupport.this, "Select any option", Toast.LENGTH_SHORT).show();
            }
        });
    }
}