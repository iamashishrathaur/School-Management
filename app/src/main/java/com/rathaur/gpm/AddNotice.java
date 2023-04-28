package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.UUID;

public class AddNotice extends AppCompatActivity {
    Spinner spinner;
    TextInputEditText subject,topic;
    TextView button;
    final String[] type={"teacher","student"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        getSupportActionBar().hide();
        spinner=findViewById(R.id.notice_spinner);
        subject=findViewById(R.id.notice_subject);
        topic=findViewById(R.id.notice_topic);
        button=findViewById(R.id.submit_notice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.add_notice_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference("Notice");
                String sub=subject.getText().toString();
                String top=topic.getText().toString();

                String spi=spinner.getSelectedItem().toString();
                Date date= Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String sdate= dateFormat.format(date);
                Notice notice=new Notice(spi,sdate,sub,top);
                String uuid= UUID.randomUUID().toString();
                if (!sub.isEmpty()){
                   if (!top.isEmpty()) {
                       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               databaseReference.child(spi).child(uuid).setValue(notice);
                               startActivity(new Intent(getApplicationContext(),AdministratorActivity.class));
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {
                               Toast.makeText(AddNotice.this, "try again", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
                   else {
                       Toast.makeText(AddNotice.this, "fill notice topic", Toast.LENGTH_SHORT).show();
                   }

                }else {
                    Toast.makeText(AddNotice.this, "fill notice subject", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}