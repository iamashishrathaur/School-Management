package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class FullImage extends AppCompatActivity {
    TouchImageView imageView;
    RelativeLayout back;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        Objects.requireNonNull(getSupportActionBar()).hide();
        imageView = findViewById(R.id.scroll_gallery_view);
        back=findViewById(R.id.full_gelary_back_pressed);
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        Intent intent = getIntent();
        String url = intent.getStringExtra("uri");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        Picasso.get().load(url).resize(width,imageView.getHeight()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
              dialog.dismiss();
            }

            @Override
            public void onError(Exception e) {
            dialog.dismiss();
                Toast.makeText(FullImage.this, "try again", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}