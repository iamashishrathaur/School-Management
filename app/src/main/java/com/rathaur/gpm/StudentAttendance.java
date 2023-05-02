package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.Attendance;

import java.util.ArrayList;
import java.util.Objects;

public class StudentAttendance extends AppCompatActivity {
    final ArrayList<Attendance> attendances = new ArrayList<>();
    PieChart pieChart;
    Dialog dialog;
    String enroll;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FloatingActionButton floatingActionButton;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.progress_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        floatingActionButton = findViewById(R.id.student_attendance_floating_action_button);
        pieChart = findViewById(R.id.pie_chart);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back = findViewById(R.id.student_attendance_back_pressed);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        enroll = sharedPreferences.getString("enrollment", "");
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), QrCodeScanner.class));
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setChart();

    }

    private void setChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1500, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialog.show();
        pieChart.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AttendanceInfo");
                databaseReference.child(enroll);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            pieChart.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            attendances.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Attendance attendance = dataSnapshot.getValue(Attendance.class);
                                attendances.add(attendance);
                            }
                            float a = attendances.size();
                            float b = 200 - a;
                            setData(b, a);
                        }
                        else {
                            dialog.dismiss();
                            pieChart.setVisibility(View.VISIBLE);
                            setData(1,0);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                        Toast.makeText(StudentAttendance.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 500);
//        setData(128, 12);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }


    private void setData(float absent, float present) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(present, "Present"));
        entries.add(new PieEntry(absent, "Absent"));

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.parseColor("#F2472C"));
        colors.add(Color.parseColor("#A6A6A6"));

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setSliceSpace(3f);
        dataSet.setColors(colors);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(12f);
        PieData data = new PieData(dataSet);

        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.highlightValues(null);
    }

}