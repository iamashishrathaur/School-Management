package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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
import com.rathaur.gpm.DataBaseModal.Teacher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TeacherAddComplaints extends AppCompatActivity {
    TextInputEditText editText;
    TextView button;
    DatabaseReference reference;
    RelativeLayout back;
    String tenrollment,tname,temail,tmobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_complaints);
        Objects.requireNonNull(getSupportActionBar()).hide();
        editText = findViewById(R.id.teacher_add_complaints_edittext);
        button = findViewById(R.id.teacher_add_complaints_data);
        back=findViewById(R.id.teacher_add_complaints_back_pressed);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("tcomplaints");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tcomplaints = editText.getText().toString();
                if (!tcomplaints.trim().isEmpty()) {

                    SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                    tname = sharedPreferences.getString("name", "");
                    temail = sharedPreferences.getString("email", "");
                    tenrollment = sharedPreferences.getString("enrollment", "");
                    tmobile = sharedPreferences.getString("mobile", "");
                    reference.orderByChild("tenrollment").equalTo(tenrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                Date date = Calendar.getInstance().getTime();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String tdate = dateFormat.format(date);
                                Teacher complaints = new Teacher(tname, tmobile, tenrollment, tcomplaints, tdate);
                                reference.child(tenrollment).setValue(complaints);
                                Toast.makeText(getApplicationContext(), "Complaints Successfully sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Your Previous Complaints Pending..", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}