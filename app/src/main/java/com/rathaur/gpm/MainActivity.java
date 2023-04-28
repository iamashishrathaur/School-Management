package com.rathaur.gpm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String pro=sharedPreferences.getString("profession","");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                if (pro.equals("Student")){
                    startActivity(new Intent(getApplicationContext(), Navigation_drawer.class));
                    finish();
                }
                else if ( pro.equals("Teacher")) {
                    startActivity(new Intent(getApplicationContext(), NavigationDrawerTeacher.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(),Wellcomelogin.class));
                    finish();
                }

            }
        },300);
    }
}