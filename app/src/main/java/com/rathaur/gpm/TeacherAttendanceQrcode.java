package com.rathaur.gpm;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Objects;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class TeacherAttendanceQrcode extends AppCompatActivity {
    private ScannerLiveView liveView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_qrcode);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        liveView=findViewById(R.id.teacher_attendance_camView);
        if (!checkPermission()) {
            requestPermission();
        }
        liveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {

            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {

            }

            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(TeacherAttendanceQrcode.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {

            }
        });
    }
    private boolean checkPermission(){
        int camera= ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int vibrate= ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        return camera== PackageManager.PERMISSION_GRANTED && vibrate==PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission(){
        int PERMISSION_CODE=200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA,VIBRATE},PERMISSION_CODE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        liveView.stopScanner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder zxDecoder=new ZXDecoder();
        zxDecoder.setScanAreaPercent(0.8);
        liveView.setDecoder(zxDecoder);
        liveView.startScanner();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liveView.stopScanner();

    }
}