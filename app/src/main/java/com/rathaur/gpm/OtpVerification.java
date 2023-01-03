package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {
    String getotp;
    TextView mobile_number,submit,resend_otp,otp_timer;
    ProgressBar progressBar;
    EditText otp1, otp2,otp3,otp4,otp5,otp6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        getSupportActionBar().hide();
        otp_timer =findViewById(R.id.otp_timer);
        otp1=findViewById(R.id.otp1);
        otp2=findViewById(R.id.otp2);
        otp3=findViewById(R.id.otp3);
        otp4=findViewById(R.id.otp4);
        otp5=findViewById(R.id.otp5);
        otp6=findViewById(R.id.otp6);
        resend_otp=findViewById(R.id.resend_otp);
        mobile_number=findViewById(R.id.backend_mobile_number);
        submit=findViewById(R.id.submit_otp);
     progressBar=findViewById(R.id.otp_confirm_progressbar);
        progressBar.setVisibility(View.GONE);
        resend_otp.setVisibility(View.GONE);
        String mobile= getIntent().getStringExtra("umobile");
        mobile_number.setText(mobile);
         getotp=getIntent().getStringExtra("backendotp");
                 submit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         if (!otp1.getText().toString().trim().isEmpty() && !otp2.getText().toString().trim().isEmpty() && !otp3.getText().toString().trim().isEmpty() && !otp4.getText().toString().trim().isEmpty() && !otp5.getText().toString().trim().isEmpty() && !otp6.getText().toString().trim().isEmpty()) {
                             String allotp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString() + otp5.getText().toString() + otp6.getText().toString();
                             if (getotp != null) {
                                 progressBar.setVisibility(View.VISIBLE);
                                 submit.setVisibility(View.GONE);
                                 PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotp, allotp);
                                 FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                     @Override
                                     public void onComplete(@NonNull Task<AuthResult> task) {
                                         if (task.isSuccessful()) {
                                             progressBar.setVisibility(View.GONE);
                                             submit.setVisibility(View.VISIBLE);
                                             Intent intent = new Intent(getApplicationContext(), WellcomeMyschool.class);
                                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent);
                                         } else {
                                             progressBar.setVisibility(View.GONE);
                                             submit.setVisibility(View.VISIBLE);
                                             Toast.makeText(OtpVerification.this, "Enter the Correct otp", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 });
                             } else {
                                 progressBar.setVisibility(View.GONE);
                                 submit.setVisibility(View.VISIBLE);
                                 Toast.makeText(OtpVerification.this, "Please check internet connection ", Toast.LENGTH_SHORT).show();
                             }
                         } else {
                             Toast.makeText(OtpVerification.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
        new CountDownTimer(30000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                otp_timer.setText("00:"+millisUntilFinished/1000+"s");
            }
            @Override
            public void onFinish() {
                resend_otp.setVisibility(View.VISIBLE);
                otp_timer.setVisibility(View.GONE);


                 resend_otp.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("umobile"), 30, TimeUnit.SECONDS, OtpVerification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                         {
                             @Override
                             public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                             }
                             @Override
                             public void onVerificationFailed(@NonNull FirebaseException e) {
                                 Toast.makeText(OtpVerification.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                             @Override
                             public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                             {
                                 resend_otp.setVisibility(View.GONE);
                                 otp_timer.setVisibility(View.VISIBLE);
                                 getotp=newotp;
                                 Toast.makeText(OtpVerification.this, "sent otp", Toast.LENGTH_SHORT).show();
                                 new CountDownTimer(30000, 1) {
                                     @Override
                                     public void onTick(long millisUntilFinished) {
                                         otp_timer.setText("00:"+millisUntilFinished/1000+"s");
                                     }
                                     @Override
                                     public void onFinish() {
                                         resend_otp.setVisibility(View.VISIBLE);
                                         otp_timer.setVisibility(View.GONE);
                                     }
                                 }.start();
                             }
                         });
                     }
                 });
            }
        }.start();
                 numbermove();
    }
    private void numbermove() {
        otp1.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }
         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
         if (!s.toString().trim().isEmpty()){
             otp2.requestFocus();
         }
         }
         @Override
         public void afterTextChanged(Editable s) {

         }
     });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp3.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}