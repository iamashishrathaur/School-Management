package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.zxing.WriterException;
import com.rathaur.gpm.databinding.ActivityAddNewStudentBinding;

import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class AddNewStudent extends AppCompatActivity {

    ActivityAddNewStudentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddNewStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        binding.aadStudentQrCodeBackPressed.setOnClickListener(v -> onBackPressed());
        QRGEncoder qrgEncoder=new QRGEncoder("student",null, QRGContents.Type.TEXT,1000);
        try {
            Bitmap bitmap=qrgEncoder.encodeAsBitmap();
            binding.aadStudentQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}