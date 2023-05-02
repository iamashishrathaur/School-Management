package com.rathaur.gpm;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Attendance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                String enroll=sharedPreferences.getString("enrollment","");
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String sdate = dateFormat.format(date);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("TeacherAttendanceCode");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String fdata=snapshot.child("code").getValue(String.class);
                            Toast.makeText(TeacherAttendanceQrcode.this, ""+data, Toast.LENGTH_SHORT).show();
                            if (Objects.equals(fdata, data)){
                                Attendance attendance=new Attendance(enroll,"",true,sdate);
                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("TeacherAttendance");
                                databaseReference.child(enroll).push().setValue(attendance).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                       onBackPressed();
                                    }
                                });
                            }
                            else {
                                Toast.makeText(TeacherAttendanceQrcode.this, "wrong qr code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TeacherAttendanceQrcode.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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