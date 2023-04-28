package com.rathaur.gpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.rathaur.gpm.Adepter.ComplaintsViewPagerAdepter;

public class ShowComplaints extends AppCompatActivity {
 TabLayout tabLayout;
 ViewPager viewPager;
    RelativeLayout back;
    ComplaintsViewPagerAdepter adepter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_complaints);
        getSupportActionBar().hide();
        tabLayout=findViewById(R.id.complaints_tab);
        viewPager=findViewById(R.id.complaints_pager);
        back=findViewById(R.id.show_complaints_back_press);
        adepter=new ComplaintsViewPagerAdepter(getSupportFragmentManager());
        viewPager.setAdapter(adepter);
        tabLayout.setupWithViewPager(viewPager);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}