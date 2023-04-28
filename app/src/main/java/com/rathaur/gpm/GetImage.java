package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.rathaur.gpm.DataBaseModal.Gallery;

import java.util.UUID;

public class GetImage extends AppCompatActivity {
   Uri imageUri;
   ImageView imageView;
   TextView button;
   ProgressBar progressBar;
   TextView parsent;
    Dialog dialogbox;
    final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Gallery");
    final StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);
        getSupportActionBar().hide();
        button=findViewById(R.id.get_image_post);
        imageView=findViewById(R.id.get_gallery_image);
        progressBar=findViewById(R.id.progressbar_upload_image);
        parsent=findViewById(R.id.progressbar_upload_text);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.success_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        TextView dialogtext = dialogbox.findViewById(R.id.success_dialog_text);
        dialogtext.setText("Image successfully saved");
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
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
        else {
           startActivity(new Intent(getApplicationContext(),StudentGelary.class));
           finish();
        }
    }

    private void uplodeImage(){
         progressBar.setVisibility(View.VISIBLE);
         parsent.setVisibility(View.VISIBLE);
         button.setVisibility(View.GONE);
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
                       progressBar.setProgress(0);
                       progressBar.setVisibility(View.GONE);
                       parsent.setVisibility(View.GONE);
                       dialogbox.show();

                       TextView dialogbutton = dialogbox.findViewById(R.id.success_dialog_button);
                       dialogbutton.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               startActivity(new Intent(getApplicationContext(),StudentGelary.class));
                                finish();
                           }
                       });
                   }
               });
          }
      }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              button.setVisibility(View.VISIBLE);
                Toast.makeText(GetImage.this, "try again", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            double progress=(100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
            progressBar.setProgress((int) progress);
            parsent.setText((int) progress+"%");
            button.setVisibility(View.GONE);
            }
        });
    }
}