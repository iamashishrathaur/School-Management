package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
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
import com.rathaur.gpm.Adepter.AssignmentAdepter;
import com.rathaur.gpm.DataBaseModal.Assignment;

import java.util.Objects;

public class StudentAssignment extends AppCompatActivity {
   RecyclerView recyclerView;
   AssignmentAdepter adepter;
   String year;
    ImageView empty;
    TextView emptyText;
    Dialog dialogbox;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_assignment);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView=findViewById(R.id.assignment_recyclerView);
        emptyText=findViewById(R.id.student_assignment_empty_text);
        empty=findViewById(R.id.student_assignment_empty);

        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(dialogbox.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.student_assignment_back_pressed);
        back.setOnClickListener(view -> onBackPressed());
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        year = sharedPreferences.getString("year", "");
        FirebaseRecyclerOptions<Assignment> assignment=new FirebaseRecyclerOptions.
                Builder<Assignment>().setQuery(FirebaseDatabase.
                getInstance().getReference("assignment").child(year), Assignment.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adepter=new AssignmentAdepter(assignment);
        recyclerView.setAdapter(adepter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialogbox.show();
        new Handler().postDelayed(() -> {
            adepter.startListening();
           DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("assignment").child(year);
            databaseReference.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                dialogbox.dismiss();
                                empty.setVisibility(View.GONE);
                                emptyText.setVisibility(View.GONE);
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
                    }
            );
        },1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialogbox.dismiss();
        adepter.startListening();
    }
}