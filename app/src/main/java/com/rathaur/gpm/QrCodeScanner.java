package com.rathaur.gpm;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

public class QrCodeScanner extends AppCompatActivity {
   private ScannerLiveView liveView;
    Dialog dialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scanner);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        liveView=findViewById(R.id.attendance_camView);
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
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
                Toast.makeText(QrCodeScanner.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                if (!data.trim().isEmpty()) {
                    liveView.stopScanner();
                    dialog.show();
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String sdate = dateFormat.format(date);
                    SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                    String enroll=sharedPreferences.getString("enrollment","");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Attendance");
                    databaseReference.child(data).child(sdate).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                databaseReference.child(data).child(sdate).child(enroll).setValue("present").addOnSuccessListener(unused -> {
                                   final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("AttendanceInfo");
                                   databaseReference1.child(enroll).addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                               Attendance attendance=new Attendance(enroll,data,true,sdate);
                                               databaseReference1.child(enroll).child(String.valueOf(System.currentTimeMillis())).setValue(attendance).addOnSuccessListener(unused1 -> {
                                                   dialog.dismiss();
                                                   Toast.makeText(QrCodeScanner.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                                                   onBackPressed();

                                               });
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError error) {
                                           dialog.dismiss();
                                           Toast.makeText(QrCodeScanner.this, "try again", Toast.LENGTH_SHORT).show();
                                       }
                                   });

                                });
                            }
                            else {
                                liveView.startScanner();
                                Toast.makeText(QrCodeScanner.this, "wrong this", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            dialog.dismiss();
                            liveView.startScanner();
                            Toast.makeText(QrCodeScanner.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
        dialog.dismiss();
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
        dialog.dismiss();
    }
}