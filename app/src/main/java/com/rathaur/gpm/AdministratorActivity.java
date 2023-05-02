package com.rathaur.gpm;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.rathaur.gpm.databinding.ActivityAdministratorBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AdministratorActivity extends AppCompatActivity {
    String classSelection;
    ActivityAdministratorBinding binding;
    final String [] classItem={"First Year", "Second Year","Third Year"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdministratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

       ArrayList<SlideModel> slideModels=new ArrayList<>();
       slideModels.add(new SlideModel(R.drawable.id_card,"", ScaleTypes.FIT));
       slideModels.add(new SlideModel(R.drawable.id_card,"", ScaleTypes.FIT));
       slideModels.add(new SlideModel(R.drawable.id_card,"", ScaleTypes.FIT));
       slideModels.add(new SlideModel(R.drawable.id_card,"", ScaleTypes.FIT));
       binding.adminImageSlider.setImageList(slideModels);
        binding.adminBackPressed.setOnClickListener(view -> onBackPressed());

        binding.cardAdministorNotice.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AdminAddNoticeStudent.class)));
        binding.cardAdmistorComplaints.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AdminShowComplaintsStudent.class)));
        binding.cardAdmistorTimetable.setOnClickListener(view -> showDialog());
        binding.cardAdmistorApplication.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AdminShowApplicationStudent.class)));
        binding.cardAdmistorAddStudent.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),AddNewStudent.class)));

        binding.cardAdministorTeacherNotice.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AdminAddNoticeTeacher.class)));
        binding.cardAdmistorTeacherComplaints.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),AdminShowComplaintsTeacher.class)));
        binding.cardAdmistorTeacherAttendance.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),AdminTeacherAttendanceQrCode.class)));
        binding.cardAdmistorTeacherApplication.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),AdminShowApplicationTeacher.class)));
        binding.cardAdmistorAddTeacher.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),AddNewTeacher.class)));
        binding.cardAdmistorAllotSubjectTeacher.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),TeacherAllotSubjectShowing.class)));
    }
    public void showDialog () {
        classSelection = classItem[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the class");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(classItem, -1, (dialogInterface, i) -> classSelection = classItem[i]);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            Intent intent = new Intent(getApplicationContext(), UpdateTimeTable.class);
            intent.putExtra("timetable", classSelection);
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}