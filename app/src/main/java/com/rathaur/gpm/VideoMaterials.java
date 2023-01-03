package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rathaur.gpm.Adepter.StudentVideo;
import com.rathaur.gpm.DataBase.Gallery;
import com.rathaur.gpm.DataBase.Video;

import java.util.UUID;

public class VideoMaterials extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentVideo studentVideo;
   ImageView addVideo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_video_materials);
        recyclerView=findViewById(R.id.video_recyclerView);

        addVideo=findViewById(R.id.add_video);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Video> videoFirebaseRecyclerOptions=new FirebaseRecyclerOptions.Builder<Video>().setQuery(FirebaseDatabase.getInstance().getReference("video"), Video.class).build();
        studentVideo=new StudentVideo(videoFirebaseRecyclerOptions);
        recyclerView.setAdapter(studentVideo);

        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddVideo.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        studentVideo.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        studentVideo.startListening();
    }
}