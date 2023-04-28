package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.InputStream;
import java.util.Objects;

public class StudentSyllabus extends AppCompatActivity {
        PDFView pdfView;
        ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_student_syllabus);
         Objects.requireNonNull(getSupportActionBar()).hide();
         pdfView=findViewById(R.id.syllabus_pdf);
         progressBar=findViewById(R.id.progressBar);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.syllabus_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        StorageReference storageReference;
         FirebaseApp firebaseApp;
         FirebaseStorage firebaseStorage;
         firebaseApp=FirebaseApp.getInstance();
         firebaseStorage=FirebaseStorage.getInstance(firebaseApp);
         storageReference=firebaseStorage.getReference().child("pdf/syllabus.pdf");
         storageReference.getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {

                onPostExecute(taskSnapshot.getStream());
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(StudentSyllabus.this, "Please try again", Toast.LENGTH_LONG).show();
             }
         });

    }
    protected void onPostExecute(InputStream inputStream) {

       pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                progressBar.setVisibility(View.GONE);
            }
        }).defaultPage(0).scrollHandle(new DefaultScrollHandle(this)).spacing(10).load();

    }

}