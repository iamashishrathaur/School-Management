package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.RecycleAdepter;
import com.rathaur.gpm.DataBaseModal.Gallery;

public class StudentGelary extends AppCompatActivity {
FloatingActionButton open_gallery;
RecyclerView recyclerView;
RecycleAdepter adepter;
ImageView empty;
TextView emptyText;
Dialog dialogbox;

 @SuppressLint("MissingInflatedId")
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_gelary);
        getSupportActionBar().hide();
        open_gallery=findViewById(R.id.open_gallery);
        recyclerView=findViewById(R.id.recycle_image_view);
        emptyText=findViewById(R.id.gallery_empty_text);
        empty=findViewById(R.id.gallery_empty);
     dialogbox= new Dialog(this);
     dialogbox.setContentView(R.layout.progress_dialog);
     dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
     dialogbox.setCanceledOnTouchOutside(false);

     @SuppressLint({"MissingInflatedId", "LocalSuppress"})
     RelativeLayout back=findViewById(R.id.gelary_back_pressed);
     back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             onBackPressed();
         }
     });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        FirebaseRecyclerOptions<Gallery> option= new FirebaseRecyclerOptions.Builder<Gallery>()
                .setQuery(FirebaseDatabase.
                        getInstance().getReference().child("Gallery"),Gallery.class).build();
        adepter=new RecycleAdepter(option);
        recyclerView.setAdapter(adepter);
        open_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GetImage.class));
                finish();
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
                DatabaseReference databaseReference=FirebaseDatabase.
                        getInstance().getReference().child("Gallery");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                           dialogbox.dismiss();
                           emptyText.setVisibility(View.GONE);
                           empty.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);
                        }
                        else {
                            dialogbox.dismiss();
                            emptyText.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialogbox.dismiss();
                        Toast.makeText(StudentGelary.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
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