package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.v1.TransactionOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class StudentIdcard extends AppCompatActivity {
    //private static final int PE
   DatabaseReference dpReference;
   StorageReference storageReference;
   ShapeableImageView student_image;
   int pdfHeight=300;
   int pdfWidth=450;
   public final int REQUEST_CODE=100;
   private PdfDocument document;
   Bitmap bitmap;
   LinearLayout idcard,print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_idcard);
        getSupportActionBar().hide();
        student_image=findViewById(R.id.idcard_student_image);
        print=findViewById(R.id.idcard_student_print);
        idcard=findViewById(R.id.student_idcard);
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        String sname=sharedPreferences.getString("sname","");
        String semail=sharedPreferences.getString("semail","");
          String senrollment=sharedPreferences.getString("senrollment","");
        String sprofession=sharedPreferences.getString("sprofession","");
        String smobile=sharedPreferences.getString("smobile","");
        dpReference= FirebaseDatabase.getInstance().getReference().child("student");
        storageReference= FirebaseStorage.getInstance().getReference();
        dpReference.child(senrollment);
        dpReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String image=snapshot.child(senrollment).child("simage").getValue(String.class);
                    Picasso.get().load(image).fit().into(student_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       print.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED ){
                   Log.d("size",""+idcard.getWidth()+ ""+idcard.getWidth());
                   bitmap=LoadBitmap(idcard,idcard.getWidth(),idcard.getHeight());
                   createpdf();


               }
               else {
                   requestAllPermissions();
               }
           }
       });

    }

    private Bitmap LoadBitmap(View view, int width, int height) {
        Bitmap bitmap =Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void requestAllPermissions() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void createpdf() {
        WindowManager windowManager=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width =displayMetrics.widthPixels;
        float height=displayMetrics.heightPixels;
        int convertWidth=(int)width,convertHeight=(int)height;

        PdfDocument pdfDocument=new PdfDocument();

        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(convertWidth,convertHeight,1).create();
        PdfDocument.Page page= pdfDocument.startPage(pageInfo);
        Canvas canvas =page.getCanvas();
        Paint paint=new Paint();
        canvas.drawPaint(paint);
        bitmap=Bitmap.createScaledBitmap(bitmap,convertWidth,convertHeight,true);
        canvas.drawBitmap(bitmap,0,0,null);
        pdfDocument.finishPage(page);
        String name= UUID.randomUUID().toString();
        File file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"GPM"+name+".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "Pdf successfully saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e){
            Toast.makeText(this, "faield", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        pdfDocument.close();

    }

}