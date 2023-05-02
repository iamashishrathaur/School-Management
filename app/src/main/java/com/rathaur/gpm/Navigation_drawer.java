package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Navigation_drawer extends AppCompatActivity {
   ActivityResultLauncher<String> mTakeImage;
   int versioncode;
   FirebaseRemoteConfig remoteConfig;
   PackageInfo pInfo;
    Uri resultUri;
   DrawerLayout drawerLayout;
   RoundedImageView student_image;
   ImageSlider imageSlider;
   NavigationView navigationView;
   SharedPreferences preferences=null;
   SharedPreferences.Editor editor;
   String destUri;
   RelativeLayout card_subject,card_syllabus,card_timetable,card_s_list,card_attendance,
    card_homework,card_icard,card_t_list, card_chat_bot,card_gallery,card_complaints,
    card_video,card_events,card_application,card_study_material,
           card_chat,card_assignment,card_notice;
   Toolbar toolbar;
   TextView email,name,profession;
   View headerView;
   public Uri imageUri;
   FirebaseStorage storage;
   StorageReference reference;
    String senrollment;
    private Dialog dialog;
    DatabaseReference dpReference;
    StorageReference storageReference;
    ProgressBar progressBar;
    TextView year;
    String ldate;
    @SuppressLint("SetTextI18n")
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
        card_chat_bot =findViewById(R.id.card_chat_bot);
        card_gallery=findViewById(R.id.card_gallery);
        card_video=findViewById(R.id.card_video);
        card_events=findViewById(R.id.card_events);
        card_application=findViewById(R.id.card_application);
        card_study_material=findViewById(R.id.card_std_materials);
        card_notice=findViewById(R.id.card_notice);
        card_chat =findViewById(R.id.card_result);
        card_assignment=findViewById(R.id.card_assignment);
        email=findViewById(R.id._student_email);
        headerView=navigationView.getHeaderView(0);
        name=headerView.findViewById(R.id._student_name);
        email=headerView.findViewById(R.id._student_email);
        profession=headerView.findViewById(R.id._student_profession);
        student_image=headerView.findViewById(R.id.student_image_click);
        progressBar=headerView.findViewById(R.id.student_image_progress);
        card_complaints=findViewById(R.id.card_complaint);
        year=findViewById(R.id.sesion_year);



        storage=FirebaseStorage.getInstance();
        reference=storage.getReference();
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String sname=sharedPreferences.getString("name","");
        String semail=sharedPreferences.getString("email","");
        senrollment=sharedPreferences.getString("enrollment","");
        String sprofession=sharedPreferences.getString("profession","");
        String simage=sharedPreferences.getString("image",null);

        profession.setText(sprofession);
        name.setText(sname);
        email.setText(semail);
        if (simage !=null){
            Picasso.get().load(simage).fit().into(student_image);
        }

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.update_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        remoteConfig=FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings=new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5).build();
        remoteConfig.setConfigSettingsAsync(configSettings);
        remoteConfig.fetchAndActivate().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                final String new_version_code=remoteConfig.getString("version");
                if (Integer.parseInt(new_version_code) > versioncode){
                   dialog.show();
                   TextView update=dialog.findViewById(R.id.update);
                   TextView cancel=dialog.findViewById(R.id.update_cancel);
                   update.setOnClickListener(view -> {
                       try {
                           startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/games")));
                           dialog.dismiss();
                       }catch (Exception e){
                           Toast.makeText(Navigation_drawer.this, "try again", Toast.LENGTH_SHORT).show();
                           Log.d("version",e.toString());
                       }
                   });
                   cancel.setOnClickListener(view -> dialog.dismiss());
                }
            }
        });


        ///Date--------
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy", Locale.getDefault());
        ldate= dateFormat.format(date);
        int idate= Integer.parseInt(ldate);
        String adate= String.valueOf(idate-1);
        year.setText(adate+"-"+ldate);
        //
        dpReference= FirebaseDatabase.getInstance().getReference().child("student");
        storageReference=FirebaseStorage.getInstance().getReference();

        card_gallery.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentGelary.class)));
     //   card_add_student.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,AttendanceMainActivity.class)));
        card_s_list.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),StudentList.class)));
        card_attendance.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),StudentAttendance.class))
                //Toast.makeText(this, "Coming Soon..", Toast.LENGTH_SHORT).show()
        );
        card_chat_bot.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),AiChatsTools.class)));


        card_homework.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this, ShowStudentHomework.class)));
        card_icard.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentIdcard.class)));
        card_t_list.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TeacherList.class)));
        card_complaints.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),StudentComplents.class)));
        card_syllabus.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentSyllabus.class)));
        card_timetable.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentTimetable.class)));
        card_subject.setOnClickListener(v -> startActivity(new Intent(Navigation_drawer.this,StudentSubject.class
        )));
        card_chat.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),RecentChat.class)));
        card_assignment.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),StudentAssignment.class)));
        card_notice.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), StudentNotice.class)));
        card_study_material.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), StudentStudyMaterials.class));
            //Toast.makeText(Navigation_drawer.this, "Coming Soon..", Toast.LENGTH_SHORT).show();
        });
        card_events.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), StudentEvents.class)));
        card_video.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),VideoMaterials.class)));
        card_application.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), StudentApplicationShow.class)));
        toolbar.setOnClickListener(view -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.openDrawer(GravityCompat.START);
            }
            else {
              drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
           int id=item.getItemId();
           if (id==R.id.s_home){
               drawerLayout.closeDrawer(GravityCompat.START);
           }
            if (id==R.id.s_About_school){
               //Toast.makeText(Navigation_drawer.this, "About school", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), AboutSchool.class));
           }
           if (id==R.id.s_about_us){
               //Toast.makeText(Navigation_drawer.this, "about", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), AboutUs.class));
           }
           if (id==R.id.s_Contact){
              // Toast.makeText(Navigation_drawer.this, "Contact", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), ContactUs.class));
           }
           if (id==R.id.s_Feedback){
               //Toast.makeText(Navigation_drawer.this, "Feedback", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), SendFeedback.class));
           }
           if (id==R.id.s_Help){
              // Toast.makeText(Navigation_drawer.this, "Help", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), HelpAndSupport.class));
           }
           if (id==R.id.s_privacy){
              // Toast.makeText(Navigation_drawer.this, "Priva", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), PrivacyPolicy.class));
           }
           if (id==R.id.s_change_password){
               //Toast.makeText(Navigation_drawer.this, "password", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(getApplicationContext(), ChangePassword.class));
           }
           if (id==R.id.s_logout){
               AlertDialog.Builder alert = new AlertDialog.Builder(this);

               alert.setMessage("Are you sure ?").setPositiveButton("Logout", (dialog, which) -> {
                   SharedPreferences sharedPreferences1= getSharedPreferences("user",MODE_PRIVATE);
                   SharedPreferences.Editor editor=sharedPreferences1.edit();
                   editor.clear();
                   editor.apply();

                   Toast.makeText(Navigation_drawer.this, "Sussecfull", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(getApplicationContext(), Wellcomelogin.class));
                   finish();

               }).setNegativeButton("Cancel", null);
               AlertDialog alert1 = alert.create();
               alert1.show();
           }

           return true;
       });

        Objects.requireNonNull(getSupportActionBar()).hide();

        imageSlider=findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel("https://www.electrochem.org/wp-content/uploads/2021/06/students-diverse-iStock-1202957911-400x400-1.jpg","", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.poornima.org/img/slider/new/sitebanner.jpg","", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://donboscobangalore.education/img/sections/slider/placements1.jpg", "",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://www.myamcat.com/blog/wp-content/uploads/2017/09/interviewmockai.png","",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://img.collegedekhocdn.com/media/img/news/Institutes_with_100_Placements_1.png","", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://img.collegedekhocdn.com/media/img/news/Institutes_with_100_Placements_1.png","", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://img.collegedekhocdn.com/media/img/news/Institutes_with_100_Placements_1.png", "",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://img.collegedekhocdn.com/media/img/news/Institutes_with_100_Placements_1.png","", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://img.collegedekhocdn.com/media/img/news/Institutes_with_100_Placements_1.png","", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);



        mTakeImage=registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result!=null){
                destUri= UUID.randomUUID().toString() + ".jpg";
                UCrop.Options option=new UCrop.Options();
                imageUri=result;
                UCrop.of(imageUri,Uri.fromFile(new File(getCacheDir(),destUri))).withOptions(option)
                        .withAspectRatio(3,4)
                        .withMaxResultSize(200,500)
                        .start(Navigation_drawer.this);

            }
        });
        student_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTakeImage.launch("image/*");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            resultUri = UCrop.getOutput(data);
            uplodeImage();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(1000);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private void uplodeImage() {
        StorageReference storageReference=reference.child("StudentImage/"+senrollment);
        storageReference.putFile(resultUri).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
         final Map<String,Object> map=new HashMap<>();
         map.put("simage",uri.toString());
         dpReference.child(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()) {
                     dpReference.child(senrollment).updateChildren(map).addOnCompleteListener(task -> {
                         progressBar.setProgress(0);
                         progressBar.setVisibility(View.GONE);
                         student_image.setImageURI(resultUri);
                         SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                         SharedPreferences.Editor editor=sharedPreferences.edit();
                         editor.putString("image",uri.toString());
                         editor.apply();
                     });
                 } else {
                     dpReference.child(senrollment).setValue(map).addOnCompleteListener(task -> {
                         progressBar.setProgress(0);
                         progressBar.setVisibility(View.GONE);
                         student_image.setImageURI(imageUri);
                         SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                         SharedPreferences.Editor editor=sharedPreferences.edit();
                         editor.putString("image",uri.toString());
                         editor.apply();
                     });
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 progressBar.setProgress(0);
                 progressBar.setVisibility(View.GONE);
             }
         });
         // add chat user images
            DatabaseReference dr1=FirebaseDatabase.getInstance().getReference().child("User");
            final Map<String,Object> Umap=new HashMap<>();
            Umap.put("image",uri.toString());
            dr1.child(senrollment).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()){
                     dr1.child(senrollment).updateChildren(Umap);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
             }
         });

        })).addOnFailureListener(e -> {
            progressBar.setProgress(0);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Navigation_drawer.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
            preferences=getSharedPreferences("user",MODE_PRIVATE);
            editor=preferences.edit();
            if (isItFirstTime()) {

                MediaPlayer player=MediaPlayer.create(this,R.raw.welcome_music);
                player.setAuxEffectSendLevel(9);
                player.start();
            }

    }
    public boolean isItFirstTime() {
        if (preferences.getBoolean("firstTime", true)) {
            editor.putBoolean("firstTime", false);
            editor.commit();
            editor.apply();
            return true;
        } else {
            return false;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

}