package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.StudentVideo;
import com.rathaur.gpm.DataBaseModal.Video;

public class VideoMaterials extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentVideo studentVideo;
   FloatingActionButton addVideo;
   ImageView empty;
   TextView emptyText;
    Dialog dialogbox;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_video_materials);
        recyclerView=findViewById(R.id.video_recyclerView);
        addVideo=findViewById(R.id.add_video);
        emptyText=findViewById(R.id.video_empty_text);
        empty=findViewById(R.id.video_empty);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.video_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Video> videoFirebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<Video>().setQuery(FirebaseDatabase.getInstance().getReference("video"), Video.class).build();
        studentVideo=new StudentVideo(videoFirebaseRecyclerOptions);
        recyclerView.setAdapter(studentVideo);

        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddVideo.class);
                startActivity(intent);
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
                studentVideo.startListening();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("video");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            dialogbox.dismiss();
                        }else {
                          dialogbox.dismiss();
                          emptyText.setVisibility(View.VISIBLE);
                          empty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialogbox.dismiss();
                        Toast.makeText(VideoMaterials.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },1000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        dialogbox.dismiss();
        studentVideo.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}