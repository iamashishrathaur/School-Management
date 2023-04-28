package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class PrivacyPolicy extends AppCompatActivity {
    LinearLayout privacy;
    ZoomControls controls;
    float x;
    float y;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        Objects.requireNonNull(getSupportActionBar()).hide();
        privacy = findViewById(R.id.privacy_policy);
        controls = findViewById(R.id.zoomControl);
        controls.hide();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back = findViewById(R.id.privacy_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        privacy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                controls.show();
                return false;

            }
        });
        controls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = privacy.getScaleX();
                y = privacy.getScaleY();
                privacy.setScaleX((float) (x + 1));
                privacy.setScaleY((float) (y + 1));
            }
        });
        controls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = privacy.getScaleX();
                y = privacy.getScaleY();
                if (y == 1 && x == 1) {
                    privacy.setScaleX(x);
                    privacy.setScaleY(y);
                } else {
                    privacy.setScaleX((float) (x - 1));
                    privacy.setScaleY((float) (y - 1));
                }
            }
        });


    }
}