package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

public class StudentSyllabus extends AppCompatActivity {
        ImageView syllabus_back;
        PDFView pdfView;
        ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_student_syllabus);
         getSupportActionBar().hide();
         syllabus_back=findViewById(R.id.syllabus_back_pressed);
         pdfView=findViewById(R.id.syllabus_pdf);
         progressBar=findViewById(R.id.progressBar);
         progressBar.setVisibility(View.GONE);
         StorageReference storageReference;
         FirebaseApp firebaseApp;
         FirebaseStorage firebaseStorage;
         storageReference=FirebaseStorage.getInstance().getReference();
         firebaseApp=FirebaseApp.getInstance();
         firebaseStorage=FirebaseStorage.getInstance(firebaseApp);
         storageReference=firebaseStorage.getReference().child("pdf/syllabus.pdf");
         storageReference.getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {
               pdfView.fromStream(taskSnapshot.getStream()).load();
               progressBar.setVisibility(View.GONE);
               pdfView.setVisibility(View.VISIBLE);
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(StudentSyllabus.this, "Please try again", Toast.LENGTH_LONG).show();
             }
         }).addOnProgressListener(new OnProgressListener<StreamDownloadTask.TaskSnapshot>() {
             @Override
             public void onProgress(@NonNull StreamDownloadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
             }
         });

    }
}