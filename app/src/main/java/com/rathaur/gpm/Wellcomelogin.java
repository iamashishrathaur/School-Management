package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.rathaur.gpm.Adepter.ViewPagerAdepter;
import com.rathaur.gpm.DataBaseModal.SlideItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;

public class Wellcomelogin extends AppCompatActivity {
    ViewPagerAdepter adepter;
    TextView blink;
    ViewPager2 viewPager;
    Animation animBlink;
    CircleIndicator3 circleIndicator;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
         viewPager=findViewById(R.id.login_viewPager);
         blink=findViewById(R.id.login_text_blink);
        circleIndicator = findViewById(R.id.login_tab_layout);
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.text_blink);
        blink.startAnimation(animBlink);
        List<SlideItem> list=new ArrayList<>();
            list.add(new SlideItem(R.drawable.sc_1));
            list.add(new SlideItem(R.drawable.sc_2));
            list.add(new SlideItem(R.drawable.sc_3));
            list.add(new SlideItem(R.drawable.sc_4));
            list.add(new SlideItem(R.drawable.sc_5));
            list.add(new SlideItem(R.drawable.sc_6));
            list.add(new SlideItem(R.drawable.sc_7));
            list.add(new SlideItem(R.drawable.sc_8));

            adepter=new ViewPagerAdepter(list,viewPager);
            viewPager.setAdapter(adepter);
            circleIndicator.setViewPager(viewPager);
            adepter.registerAdapterDataObserver(circleIndicator.getAdapterDataObserver());

    }


    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LogInPage.class));
    }

    public void sign(View view) {
        startActivity(new Intent(getApplicationContext(),MobileVerification.class));
    }
}