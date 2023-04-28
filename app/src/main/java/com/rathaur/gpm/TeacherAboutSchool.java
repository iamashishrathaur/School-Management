package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class TeacherAboutSchool extends AppCompatActivity {
    RelativeLayout edit;
    RoundedImageView imageView;
    TextView open,close,about,name1,mobile1,email1,name2,mobile2,email2;
    ShimmerFrameLayout shimmerFrameLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_about_school);
        Objects.requireNonNull(getSupportActionBar()).hide();
        edit=findViewById(R.id.edit_about_school);
        open=findViewById(R.id.t_open_time_show);
        close=findViewById(R.id.t_close_time_show);
        about=findViewById(R.id.t_about_school_show);
        name1=findViewById(R.id.t_contract_name1_show);
        mobile1=findViewById(R.id.t_contract_mobile1_show);
        email1=findViewById(R.id.t_contract_email1_show);
        name2=findViewById(R.id.t_contract_name2_show);
        mobile2=findViewById(R.id.t_contract_mobile2_show);
        email2=findViewById(R.id.t_contract_email2_show);
        imageView=findViewById(R.id.t_about_school_logo_add_show);
        shimmerFrameLayout=findViewById(R.id.t_about_school_logo_add_show_effect);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.t_about_school_back_pressed);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddAboutSchool.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        shimmerFrameLayout.startShimmer();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("schoolInformation");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    about.setText(snapshot.child("about").getValue(String.class));
                    open.setText(snapshot.child("open").getValue(String.class));
                    close.setText(snapshot.child("close").getValue(String.class));
                    name1.setText("( "+snapshot.child("name1").getValue(String.class)+")");
                    name2.setText("( "+snapshot.child("name2").getValue(String.class)+")");
                    mobile1.setText(snapshot.child("mobile1").getValue(String.class));
                    mobile2.setText(snapshot.child("mobile2").getValue(String.class));
                    email1.setText(snapshot.child("email1").getValue(String.class));
                    email2.setText(snapshot.child("email2").getValue(String.class));
                    Picasso.get().load(snapshot.child("uri").getValue(String.class)).fit().into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            shimmerFrameLayout.stopShimmer();
                        }

                        @Override
                        public void onError(Exception e) {
                            shimmerFrameLayout.stopShimmer();
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherAboutSchool.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}