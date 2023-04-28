package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.NoticeAdepter;
import com.rathaur.gpm.DataBaseModal.Notice;

public class StudentNotice extends AppCompatActivity {

    RecyclerView view;
    NoticeAdepter adepter;
    ImageView empty;
    TextView emptyText;
    Dialog dialogbox;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notice);
        getSupportActionBar().hide();
        view=findViewById(R.id.student_notice_recycler);
        emptyText=findViewById(R.id.student_notice_empty_text);
        empty=findViewById(R.id.student_notice_empty);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        view.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Notice> options=new FirebaseRecyclerOptions.Builder<Notice>().setQuery(FirebaseDatabase.getInstance().getReference("Notice").child("student"),Notice.class).build();
        adepter=new NoticeAdepter(options);
        view.setAdapter(adepter);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.student_notice_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialogbox.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adepter.startListening();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Notice");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            dialogbox.dismiss();
                        }
                        else {
                            dialogbox.dismiss();
                            empty.setVisibility(View.VISIBLE);
                            emptyText.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialogbox.dismiss();
                    }
                });
            }
        },1000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        dialogbox.dismiss();
        adepter.startListening();
    }
}