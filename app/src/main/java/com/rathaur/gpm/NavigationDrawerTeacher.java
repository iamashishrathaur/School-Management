package com.rathaur.gpm;

import static com.google.android.gms.common.util.ClientLibraryUtils.getPackageInfo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.BuildCompat;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import io.grpc.android.BuildConfig;
import kotlin.Suppress;

public class NavigationDrawerTeacher extends AppCompatActivity {

    ActivityResultLauncher<String> mTakeImage;
    int versioncode;
    FirebaseRemoteConfig remoteConfig;
    PackageInfo pInfo;
    TextView year;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseStorage storage;
    StorageReference reference;
    Uri imageUri;
    View headerView;
    RoundedImageView teacher_image;
    TextView email, name, profession;
    ImageSlider imageSlider;
    NavigationView navigationView;
    String tenrollment;
    DatabaseReference dpReference;
    StorageReference storageReference;
    RelativeLayout  teacher_card_attendance, teacher_card_chat, student_list, teacher_syllabus, teacher_complaints, teacher_gallery,
            teacher_application, teacher_list, teacher_assignment, teacher_event, teacher_notice,std_materials,attendance_teacher,chat_bot;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_teacher);
        year = findViewById(R.id.teacher_year);
        toolbar = findViewById(R.id.teacher_toolbar);
        drawerLayout = findViewById(R.id.teacher_navigation_drawer);
        Objects.requireNonNull(getSupportActionBar()).hide();
        teacher_card_attendance = findViewById(R.id.teacher_card_attendance);
        attendance_teacher=findViewById(R.id.teacher_card_teacher_attendance);
        teacher_card_chat = findViewById(R.id.teacher_card_chat);
        chat_bot=findViewById(R.id.teacher_card_chat_solution);
        teacher_syllabus = findViewById(R.id.teacher_syllabus);
        student_list = findViewById(R.id.teacher_student_list);
        teacher_complaints = findViewById(R.id.teacher_complaints);
        teacher_gallery = findViewById(R.id.teacher_gallery);
        teacher_application = findViewById(R.id.teacher_application);
        teacher_list = findViewById(R.id.card_teacher_list);
        teacher_assignment = findViewById(R.id.teacher_assignment);
        teacher_notice = findViewById(R.id.teacher_notice);
        teacher_event = findViewById(R.id.teacher_events);
        navigationView = findViewById(R.id.navigation_teacher);
        imageSlider = findViewById(R.id.teacher_image_slider);
        std_materials=findViewById(R.id.teacher_std_materials);
        headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id._teacher_name);
        email = headerView.findViewById(R.id._teacher_email);
        profession = headerView.findViewById(R.id._teacher_profession);
        teacher_image = headerView.findViewById(R.id.teacher_image_click);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String tname = sharedPreferences.getString("name", "");
        String temail = sharedPreferences.getString("email", "");
        tenrollment = sharedPreferences.getString("enrollment", "");
        String sprofession = sharedPreferences.getString("profession", "");
        String timage = sharedPreferences.getString("image", null);
        dpReference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        profession.setText(sprofession);
        name.setText(tname);
        email.setText(temail);
        Picasso.get().load(timage).fit().into(teacher_image);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.id_card, "     Home", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book, "     Login", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book, "     Logout", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book, "     Main", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book, "     Logout", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.book, "     Main", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy", Locale.getDefault());
        String ldate= dateFormat.format(date);
        int idate= Integer.parseInt(ldate);
        String adate= String.valueOf(idate-1);
        year.setText(adate+"-"+ldate);

        Dialog dialog = new Dialog(this);
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
                        }catch (Exception e){
                            Toast.makeText(NavigationDrawerTeacher.this, "try again", Toast.LENGTH_SHORT).show();
                            Log.d("version",e.toString());
                        }
                    });
                    cancel.setOnClickListener(view -> dialog.dismiss());
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home_teacher) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            if (id == R.id.about_teacher) {
                startActivity(new Intent(NavigationDrawerTeacher.this,AboutUs.class));
            }
            if (id == R.id.About_us_teacher) {
                startActivity(new Intent(NavigationDrawerTeacher.this, TeacherAboutSchool.class));
            }
            if (id == R.id.Contact_teacher) {
                startActivity(new Intent(NavigationDrawerTeacher.this, ContactUs.class));
            }
            if (id == R.id.Feedback_teacher) {
                startActivity(new Intent(NavigationDrawerTeacher.this, SendFeedback.class));
            }
            if (id == R.id.Help_teacher) {
                startActivity(new Intent(NavigationDrawerTeacher.this, HelpAndSupport.class));
            }
            if (id == R.id.privacy_teacher) {
                startActivity(new Intent(NavigationDrawerTeacher.this, PrivacyPolicy.class));
            }
            if (id == R.id.change_password_teacher) {
                startActivity(new Intent(NavigationDrawerTeacher.this, TeacherChangePassword.class));
            }
            if (id == R.id.logout_teacher) {
                AlertDialog.Builder alert = new AlertDialog.Builder(NavigationDrawerTeacher.this);
                alert.setMessage("Are you sure ?").setPositiveButton("Logout", (dialogInterface, i) -> {
                    SharedPreferences sharedPreferences1=getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), Wellcomelogin.class));
                    finish();
                }).setNegativeButton("Cancel", (dialogInterface, i) -> {

                });
                alert.show();

            }
            return true;
        });
        teacher_notice.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, TeacherNotice.class)));
        teacher_event.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, TeacherEvents.class)));
        teacher_assignment.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, TeacherAddAssignment.class)));
        teacher_list.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, TeacherList.class)));
        teacher_application.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, TeacherApllication.class)));
        teacher_gallery.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, StudentGelary.class)));
        teacher_complaints.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, TeacherComplaints.class)));
        teacher_syllabus.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, StudentSyllabus.class)));
        student_list.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, StudentList.class)));
        teacher_card_chat.setOnClickListener(view -> {
            startActivity(new Intent(NavigationDrawerTeacher.this, RecentChat.class));
        });
        chat_bot.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, AiChatsTools.class)));
        std_materials.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this, StudentStudyMaterials.class)));
        teacher_card_attendance.setOnClickListener(view -> {
            startActivity(new Intent(NavigationDrawerTeacher.this,AttendanceMainActivity.class));
        });
        attendance_teacher.setOnClickListener(view -> startActivity(new Intent(NavigationDrawerTeacher.this,TeacherAttendanceQrcode.class)));
        toolbar.setOnClickListener(view -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        mTakeImage=registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result!=null){
                imageUri=result;
                uplodeImage();
            }

        });
        teacher_image.setOnClickListener(view -> mTakeImage.launch("image/*"));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void uplodeImage() {
        DatabaseReference dr1=FirebaseDatabase.getInstance().getReference("User");
        StorageReference storageReference = reference.child("TeacherImage/" + tenrollment);
        storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            final Map<String, Object> map = new HashMap<>();
            map.put("timage", uri.toString());
            final Map<String,Object> uMap=new HashMap<>();
            uMap.put("image",uri.toString());
            dpReference.child(tenrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        dpReference.child(tenrollment).updateChildren(map);
                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("image", uri.toString());
                        editor.apply();
                    } else {
                        dpReference.child(tenrollment).setValue(map);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            dr1.child(tenrollment).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dr1.child(tenrollment).updateChildren(uMap);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    dr1.child(tenrollment).setValue(uMap);
                }
            });

        })).addOnFailureListener(e -> Toast.makeText(NavigationDrawerTeacher.this, "some time try again ", Toast.LENGTH_SHORT).show()).addOnProgressListener(snapshot -> {
        });
    }
}