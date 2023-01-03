package com.rathaur.gpm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Navigation_drawer extends AppCompatActivity {
   DrawerLayout drawerLayout;
   ImageView student_image;
   ImageSlider imageSlider;
   NavigationView navigationView;
   RelativeLayout card_subject,card_syllabus,card_timetable,card_s_list,card_attendance,card_homework,card_icard,card_t_list,card_add_student,card_gallery,card_complaints,card_video,card_events,card_application;
   Toolbar toolbar;
   TextView email,name,profession;
   View headerView;
   public Uri imageUri;
   FirebaseStorage storage;
   StorageReference reference;
    String senrollment;
    ProgressBar image_progressBar;
    DatabaseReference dpReference;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer);
        toolbar=findViewById(R.id.toolbar);
        card_subject=findViewById(R.id.card_subject);
        card_syllabus=findViewById(R.id.card_syllabus);
        card_timetable=findViewById(R.id.card_timetable);
        card_s_list=findViewById(R.id.card_list);
        card_attendance=findViewById(R.id.card_attendance);
        card_homework=findViewById(R.id.card_homework);
        card_icard=findViewById(R.id.card_ICard);
        card_t_list=findViewById(R.id.card_t_list);
        card_add_student=findViewById(R.id.card_add_student);
        card_gallery=findViewById(R.id.card_gallery);
        card_video=findViewById(R.id.card_video);
        card_events=findViewById(R.id.card_events);
        card_application=findViewById(R.id.card_application);
        email=findViewById(R.id._student_email);
        headerView=navigationView.getHeaderView(0);
        name=headerView.findViewById(R.id._student_name);
        email=headerView.findViewById(R.id._student_email);
        profession=headerView.findViewById(R.id._student_profession);
        student_image=headerView.findViewById(R.id.student_image_click);
        image_progressBar=headerView.findViewById(R.id.student_image_progress);
        card_complaints=findViewById(R.id.card_complaint);
        image_progressBar.setVisibility(View.GONE);
        storage=FirebaseStorage.getInstance();
        reference=storage.getReference();

        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        String sname=sharedPreferences.getString("sname","");
        String semail=sharedPreferences.getString("semail","");
        senrollment=sharedPreferences.getString("senrollment","");
        String sprofession=sharedPreferences.getString("sprofession","");
        profession.setText(sprofession);
        name.setText(sname);
        email.setText(semail);

        dpReference= FirebaseDatabase.getInstance().getReference().child("student");
        storageReference=FirebaseStorage.getInstance().getReference();

         student_image.setOnClickListener(v -> selectImage());

        card_gallery.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentGelary.class)));
        card_add_student.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentAddNewStudent.class)));
        card_s_list.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),StudentList.class)));
        card_attendance.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentAttendance.class)));
        card_homework.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this, ShowStudentHomework.class)));
        card_icard.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentIdcard.class)));
        card_t_list.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TeacherList.class)));
        card_complaints.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),StudentComplents.class)));
        card_syllabus.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentSyllabus.class)));
        card_timetable.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentTimetable.class)));
        card_subject.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentSubject.class)));
        card_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentEvents.class));
            }
        });
        card_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),VideoMaterials.class));
            }
        });
        card_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StudentApplicationShow.class));
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.closeDrawer(GravityCompat.START);
        navigationView.setNavigationItemSelectedListener(item -> {
           int id=item.getItemId();
           if (id==R.id.s_home){
               Toast.makeText(Navigation_drawer.this, "Home", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_profile){
               Toast.makeText(Navigation_drawer.this, "profile", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_About_school){
               Toast.makeText(Navigation_drawer.this, "About school", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_about_us){
               Toast.makeText(Navigation_drawer.this, "about", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_Contact){
               Toast.makeText(Navigation_drawer.this, "Contact", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_Feedback){
               Toast.makeText(Navigation_drawer.this, "Feedback", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_Help){
               Toast.makeText(Navigation_drawer.this, "Help", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_privacy){
               Toast.makeText(Navigation_drawer.this, "Privacy", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_change_password){
               Toast.makeText(Navigation_drawer.this, "password", Toast.LENGTH_SHORT).show();
           }
           if (id==R.id.s_logout){
               Toast.makeText(Navigation_drawer.this, "Logout", Toast.LENGTH_SHORT).show();
           }
           return true;
       });
        Objects.requireNonNull(getSupportActionBar()).hide();

        ////////////////////////

        imageSlider=findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.id_card,"     Home", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book,"     Login", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book,"     Logout", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book,"     Main", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book,"     Logout", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book,"     Main", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);
    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
           imageUri=data.getData();
          // student_image.setImageURI(imageUri);
           uplodeImage();
        }
    }
    private void uplodeImage() {
        StorageReference storageReference=reference.child("StudentImage/"+senrollment);
        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
         final Map<String,Object> map=new HashMap<>();
         map.put("simage",uri.toString());
         dpReference.child(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()) {
                     dpReference.child(senrollment).updateChildren(map);
                 } else {
                     dpReference.child(senrollment).setValue(map);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        })).addOnFailureListener(e -> Toast.makeText(Navigation_drawer.this, "some time try again ", Toast.LENGTH_SHORT).show()).addOnProgressListener(snapshot -> {
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        dpReference.child(senrollment);
        dpReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String image=snapshot.child(senrollment).child("simage").getValue(String.class);
                    Picasso.get().load(image).fit().into(student_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}