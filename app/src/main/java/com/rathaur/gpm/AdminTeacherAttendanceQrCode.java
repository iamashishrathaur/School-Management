package com.rathaur.gpm;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;
import com.rathaur.gpm.databinding.ActivityAdminTeacherAttendanceQrCodeBinding;

import java.util.Objects;
import java.util.UUID;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AdminTeacherAttendanceQrCode extends AppCompatActivity {
     ActivityAdminTeacherAttendanceQrCodeBinding binding;
    QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminTeacherAttendanceQrCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.teacherQrAttendanceBackPressed.setOnClickListener(v -> onBackPressed());


    }

    @Override
    protected void onStart() {
        super.onStart();
        String data= UUID.randomUUID().toString();
        qrgEncoder=new QRGEncoder(data,null, QRGContents.Type.TEXT,1000);
        binding.teacherQrcodeProgressBar.setProgress(1000);
        binding.teacherQrcodeProgressBar.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("TeacherAttendanceCode");

        databaseReference.child("code").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                binding.teacherQrcodeProgressBar.setVisibility(View.GONE);
                binding.teacherQrcodeProgressBar.setProgress(0);
                try {
                    Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                    binding.teacherAttendanceGenerateGrCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}