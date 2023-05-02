package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kevinschildhorn.otpview.OTPView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {
    String getotp;
    TextView mobile_number,submit,resend_otp,otp_timer;
     OTPView otpView;
    PhoneAuthCredential phoneAuthCredential;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Objects.requireNonNull(getSupportActionBar()).hide();
        otp_timer = findViewById(R.id.otp_timer);
        otpView = findViewById(R.id.otp_view);
        resend_otp = findViewById(R.id.resend_otp);
        mobile_number = findViewById(R.id.backend_mobile_number);
        submit = findViewById(R.id.submit_otp);
        Dialog dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(dialogbox.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        resend_otp.setVisibility(View.GONE);
        String mobile = getIntent().getStringExtra("umobile");
        mobile_number.setText(mobile);
        getotp = getIntent().getStringExtra("backendotp");
//        PhoneAuthCredential phoneAuthCredential1=PhoneAuthProvider.getCredential("","");
//        final String code = phoneAuthCredential.getSmsCode();
//        if (code != null) {
//            otpView.setText(code);
//        }
        submit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         String allotp=otpView.getStringFromFields();
                         if (!allotp.isEmpty()) {
                             if (getotp != null) {
                                 dialogbox.show();
                                 phoneAuthCredential = PhoneAuthProvider.getCredential(getotp, allotp);
                                 FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                     @Override
                                     public void onComplete(@NonNull Task<AuthResult> task) {
                                         if (task.isSuccessful()) {
                                             dialogbox.dismiss();
                                             Intent intent = new Intent(getApplicationContext(), WellcomeMyschool.class);
                                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent);
                                         } else {
                                             dialogbox.dismiss();
                                             Toast.makeText(OtpVerification.this, "Enter the Correct otp", Toast.LENGTH_SHORT).show();
                                         }
                                     }
                                 });
                             } else {
                                 dialogbox.dismiss();
                                 Toast.makeText(OtpVerification.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                             }


                         } else {
                             Toast.makeText(OtpVerification.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
        new CountDownTimer(30000, 1) {
            @SuppressLint("SetTextI18n")
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
                         dialogbox.show();
                         PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("umobile"), 30, TimeUnit.SECONDS, OtpVerification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                         {
                             @Override
                             public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                dialogbox.dismiss();
                             }
                             @Override
                             public void onVerificationFailed(@NonNull FirebaseException e) {
                                 dialogbox.dismiss();
                                 Toast.makeText(OtpVerification.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                             @Override
                             public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                             {
                                 dialogbox.dismiss();
                                 resend_otp.setVisibility(View.GONE);
                                 otp_timer.setVisibility(View.VISIBLE);
                                 getotp=newotp;
                                 Toast.makeText(OtpVerification.this, "sent otp", Toast.LENGTH_SHORT).show();

                                 new CountDownTimer(30000, 1) {
                                     @SuppressLint("SetTextI18n")
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
    }
}