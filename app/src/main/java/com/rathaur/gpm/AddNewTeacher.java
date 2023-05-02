package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.zxing.WriterException;
import com.rathaur.gpm.databinding.ActivityAddNewTeacherBinding;

import java.util.BitSet;
import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AddNewTeacher extends AppCompatActivity {
    ActivityAddNewTeacherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddNewTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        binding.aadTeacherQrCodeBackPressed.setOnClickListener(v -> onBackPressed());
        QRGEncoder qrgEncoder=new QRGEncoder("teacher",null, QRGContents.Type.TEXT,1000);

        try {
            Bitmap bitmap=qrgEncoder.encodeAsBitmap();
            binding.aadTeacherQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}