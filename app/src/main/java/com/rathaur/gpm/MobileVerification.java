package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileVerification extends AppCompatActivity {
  EditText user_mobile;
  TextView user_otp;
  ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        getSupportActionBar().hide();
        user_mobile=findViewById(R.id.user_mobile_number);
        user_otp=findViewById(R.id.user_get_otp);
        progressBar=findViewById(R.id.otp_progressbar);
        progressBar.setVisibility(View.GONE);
        user_otp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              if (!user_mobile.getText().toString().isEmpty()){
                  if (user_mobile.getText().toString().trim().length()==10){
                      progressBar.setVisibility(View.VISIBLE);
                      user_otp.setVisibility(View.GONE);
                      PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + user_mobile.getText().toString(), 30, TimeUnit.SECONDS, MobileVerification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                          @Override
                          public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                              progressBar.setVisibility(View.GONE);
                              user_otp.setVisibility(View.VISIBLE);
                          }
                          @Override
                          public void onVerificationFailed(@NonNull FirebaseException e) {
                              progressBar.setVisibility(View.GONE);
                              user_otp.setVisibility(View.VISIBLE);
                              Toast.makeText(MobileVerification.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                          }
                          @Override
                          public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                              super.onCodeSent(s, forceResendingToken);
                              progressBar.setVisibility(View.GONE);
                              user_otp.setVisibility(View.VISIBLE);
                              SharedPreferences number= getSharedPreferences("user",MODE_PRIVATE);
                              SharedPreferences.Editor editor=number.edit();
                              editor.putString("mobile",user_mobile.getText().toString());
                              editor.apply();
                              Intent intent=new Intent(getApplicationContext(),OtpVerification.class);
                              intent.putExtra("backendotp",s);
                              intent.putExtra("umobile", user_mobile.getText().toString());

                             startActivity(intent);
                          }
                      });
                  }
                  else {
                      Toast.makeText(MobileVerification.this, "Please enter Correct mobile number", Toast.LENGTH_SHORT).show();
                  }
              }
              else {
                  Toast.makeText(MobileVerification.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
              }
           }
       });

    }
}