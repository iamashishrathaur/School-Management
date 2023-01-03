package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Wellcomelogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_login);
        getSupportActionBar().hide();

    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LogInPage.class));
    }

    public void sign(View view) {
        startActivity(new Intent(getApplicationContext(),MobileVerification.class));
    }
}