package com.rathaur.gpm;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileVerification extends AppCompatActivity {
  EditText user_mobile;
  TextView user_otp;
    private FirebaseAuth mAuth;
    private String verificationId;
    private String code;
    Dialog dialogbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        getSupportActionBar().hide();
        user_mobile=findViewById(R.id.user_mobile_number);
        user_otp=findViewById(R.id.user_get_otp);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        mAuth = FirebaseAuth.getInstance();
        user_otp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              if (!user_mobile.getText().toString().isEmpty()){
                  if (user_mobile.getText().toString().trim().length()==10){
                     dialogbox.show();
                      sendVerificationCode(user_mobile.getText().toString().trim());
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

    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+mobile)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            SharedPreferences number= getSharedPreferences("user",MODE_PRIVATE);
                              SharedPreferences.Editor editor=number.edit();
                              editor.putString("mobile",user_mobile.getText().toString());
                              editor.apply();
                              Intent intent=new Intent(getApplicationContext(),OtpVerification.class);
                              intent.putExtra("backendotp",s);
                              intent.putExtra("umobile", user_mobile.getText().toString());
                              startActivity(intent);
            dialogbox.dismiss();
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            dialogbox.dismiss();
            code = phoneAuthCredential.getSmsCode();
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            dialogbox.dismiss();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

}