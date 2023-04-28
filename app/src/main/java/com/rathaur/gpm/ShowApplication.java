package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.rathaur.gpm.Adepter.ApplicationViewPagerAdepter;

public class ShowApplication extends AppCompatActivity {
  ApplicationViewPagerAdepter adepter;
  ViewPager viewPager;
  TabLayout layout;
  RelativeLayout back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_application);
        getSupportActionBar().hide();
        viewPager=findViewById(R.id.application_pager);
        layout=findViewById(R.id.application_tab);
        back=findViewById(R.id.show_application_back_press);
        adepter=new ApplicationViewPagerAdepter(getSupportFragmentManager());
        viewPager.setAdapter(adepter);
        layout.setupWithViewPager(viewPager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}