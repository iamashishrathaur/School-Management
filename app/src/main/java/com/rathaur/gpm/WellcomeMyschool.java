package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class WellcomeMyschool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_myschool);
        Objects.requireNonNull(getSupportActionBar()).hide();

    }

    public void Next(View view) {
       startActivity(new Intent(WellcomeMyschool.this,UserRagistation.class));
    }
}