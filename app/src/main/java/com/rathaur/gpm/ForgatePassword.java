package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgatePassword extends AppCompatActivity {
    String userOtp;
    String mobileOtp;
    TextView button,otpButton,verifyButton;
    TextInputEditText enrollment,Password,otp;
    private FirebaseAuth mAuth;
    TextInputLayout pLayout,oLayout;
    FirebaseDatabase  sfirebaseDatabase;
    String userEnrollment;
    FirebaseDatabase database;
    DatabaseReference reference;
    Dialog dialogbox;
    Dialog dialog;
    boolean profasion=false;
    DatabaseReference sdatabaseReference;
    TextView text,ok;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgate_password);
        button=findViewById(R.id.forgate_password_botton);
        enrollment=findViewById(R.id.forgate_enrollment);
        Password=findViewById(R.id.forgate_password_);
        pLayout=findViewById(R.id.forgate_password_vissival);
        oLayout=findViewById(R.id.forgate_otp_vissival);
        otpButton=findViewById(R.id.forgate_otp_botton);
        otp=findViewById(R.id.forgate_otp);
        verifyButton=findViewById(R.id.forgate_otp_verify_button);
        button.setVisibility(View.GONE);
        pLayout.setVisibility(View.GONE);
        otpButton.setVisibility(View.VISIBLE);
        oLayout.setVisibility(View.GONE);
        verifyButton.setVisibility(View.GONE);
        getSupportActionBar().hide();
        userOtp=otp.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        text=dialog.findViewById(R.id.success_dialog_text);
        text.setText("Password Successfully Update");
        ok=dialog.findViewById(R.id.success_dialog_button);
        dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.progress_dialog);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.forgote_password_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            userEnrollment = Objects.requireNonNull(enrollment.getText()).toString().trim();
                if (!userEnrollment.isEmpty()){
                    dialogbox.show();
                     sfirebaseDatabase = FirebaseDatabase.getInstance();
                     sdatabaseReference = sfirebaseDatabase.getReference("user");
                    Query checkEnrolment = sdatabaseReference.orderByChild("enrollment").equalTo(userEnrollment);
                    checkEnrolment.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                String mobile=snapshot.child(userEnrollment).child("smobile").getValue(String.class);
                                profasion=true;
                                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile, 30, TimeUnit.SECONDS, ForgatePassword.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                dialogbox.dismiss();
                                                oLayout.setVisibility(View.VISIBLE);
                                                verifyButton.setVisibility(View.VISIBLE);
                                                otpButton.setVisibility(View.GONE);
                                                final String code = phoneAuthCredential.getSmsCode();
                                                enrollment.setEnabled(false);
                                                profasion=true;
                                                if (code != null) {
                                                    otp.setText(code);
                                                }
                                            }
                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                dialogbox.dismiss();
                                                oLayout.setVisibility(View.GONE);
                                                verifyButton.setVisibility(View.GONE);
                                                otpButton.setVisibility(View.VISIBLE);
                                            }
                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(s, forceResendingToken);
                                        dialogbox.dismiss();
                                        oLayout.setVisibility(View.VISIBLE);
                                        verifyButton.setVisibility(View.VISIBLE);
                                        otpButton.setVisibility(View.GONE);
                                        mobileOtp=s;
                                        profasion=true;
                                        enrollment.setEnabled(false);
                                    }
                                });
                            }
                            else{
                                database=FirebaseDatabase.getInstance();
                                reference=database.getReference("teacher");
                                Query check_teacher_database =  reference.orderByChild("tenrollment").equalTo(userEnrollment);
                                check_teacher_database.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            Toast.makeText(ForgatePassword.this, "otp send", Toast.LENGTH_SHORT).show();
                                            String mobile=dataSnapshot.child(userEnrollment).child("tmobile").getValue(String.class);
                                            profasion=false;
                                            PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile, 30, TimeUnit.SECONDS, ForgatePassword.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    dialogbox.dismiss();
                                                    oLayout.setVisibility(View.VISIBLE);
                                                    verifyButton.setVisibility(View.VISIBLE);
                                                    otpButton.setVisibility(View.GONE);
                                                    final String code = phoneAuthCredential.getSmsCode();
                                                    enrollment.setEnabled(false);
                                                    profasion=false;
                                                    if (code != null) {
                                                        otp.setText(code);
                                                    }
                                                }
                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                    dialogbox.dismiss();
                                                    oLayout.setVisibility(View.GONE);
                                                    verifyButton.setVisibility(View.GONE);
                                                    otpButton.setVisibility(View.VISIBLE);
                                                }
                                                @Override
                                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    dialogbox.dismiss();
                                                    super.onCodeSent(s, forceResendingToken);
                                                    oLayout.setVisibility(View.VISIBLE);
                                                    verifyButton.setVisibility(View.VISIBLE);
                                                    otpButton.setVisibility(View.GONE);
                                                    mobileOtp=s;
                                                    enrollment.setEnabled(false);
                                                    profasion=false;
                                                }
                                            });
                                        }
                                        else {
                                            dialogbox.dismiss();
                                            Toast.makeText(getApplicationContext(), "user not exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        dialogbox.dismiss();
                                        Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            dialogbox.dismiss();
                            Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    dialogbox.dismiss();
                    Toast.makeText(getApplicationContext(), "please Enter Enrollment Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //----
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(otp.getText().toString());
                dialogbox.show();
            }
        });
    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mobileOtp, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            verifyButton.setVisibility(View.GONE);
                            pLayout.setVisibility(View.VISIBLE);
                            button.setVisibility(View.VISIBLE);
                            otp.setEnabled(false);
                            dialogbox.dismiss();
                        } else {
                            dialogbox.dismiss();
                            Toast.makeText(getApplicationContext(), "wrong otp", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialogbox.show();
                String pass=Password.getText().toString().trim();
                if (!pass.isEmpty()){
                    //Toast.makeText(ForgatePassword.this, "wright", Toast.LENGTH_SHORT).show();
                    if(profasion) {
                        final Map<String, Object> smap = new HashMap<>();
                        smap.put("spassword", pass);
                        sdatabaseReference.child("student").orderByChild(userEnrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                sdatabaseReference.child(userEnrollment).updateChildren(smap);
                               // Toast.makeText(ForgatePassword.this, "Successfully Update", Toast.LENGTH_SHORT).show();
                                dialogbox.dismiss();
                                dialog.show();
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialogbox.dismiss();
                                Toast.makeText(ForgatePassword.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {

                        final Map<String, Object> tmap = new HashMap<>();
                        tmap.put("tpassword", pass);
                        reference.child("teacher").orderByChild(userEnrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                reference.child(userEnrollment).updateChildren(tmap);
                                dialogbox.dismiss();
                                dialog.show();
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        onBackPressed();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                dialogbox.dismiss();
                                Toast.makeText(ForgatePassword.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                       // Toast.makeText(ForgatePassword.this, "teacher", Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(ForgatePassword.this, "fill password ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}