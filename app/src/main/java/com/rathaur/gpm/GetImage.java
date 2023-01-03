package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rathaur.gpm.DataBase.Gallery;

import java.util.UUID;

public class GetImage extends AppCompatActivity {
   Uri imageUri;
   ImageView imageView;
   TextView button;
    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Gallery");
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);
        getSupportActionBar().hide();
        button=findViewById(R.id.get_image_post);
        imageView=findViewById(R.id.get_gallery_image);
        selectImage();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uplodeImage();
            }
        });

    }
    private void selectImage() {

       Intent intent=new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
           imageUri=data.getData();
           imageView.setImageURI(imageUri);
    }
    }

    private void uplodeImage(){
        String uid= UUID.randomUUID().toString();
       StorageReference file=storageReference.child("Gallery/"+uid);
        file.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Gallery gallery=new Gallery(uri.toString()) ;
                       String smodal =reference.push().getKey();
                       reference.child(smodal).setValue(gallery);
                       Toast.makeText(getApplicationContext(), "Sucessfull", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(),StudentGelary.class));
                   }
               });
          }
      }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
    }
}