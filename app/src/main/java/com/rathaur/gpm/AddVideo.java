package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.DataBaseModal.Video;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVideo extends AppCompatActivity {
   TextInputEditText name,url;
   TextView submit;
   String senrollment;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        submit=findViewById(R.id.vedio_submit);
        name=findViewById(R.id.video_name);
        url=findViewById(R.id.video_url);
        Objects.requireNonNull(getSupportActionBar()).hide();
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        senrollment=sharedPreferences.getString("enrollment","");
        Dialog dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.success_dialog);
        Objects.requireNonNull(dialogbox.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
        dialogbox.setCanceledOnTouchOutside(false);
        TextView dialogtext = dialogbox.findViewById(R.id.success_dialog_text);
        dialogtext.setText("Video successfully saved");

        submit.setOnClickListener(view -> {
            Date date= Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String sdate= dateFormat.format(date);
            String sname1 = Objects.requireNonNull(name.getText()).toString();
            String surl= Objects.requireNonNull(url.getText()).toString();
            String id=getYoutubeId(surl);
            String uid= UUID.randomUUID().toString();
            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference databaseReference=firebaseDatabase.getReference().child("video");
            Video video=new Video(senrollment, sname1,sdate,id);
            databaseReference.child(uid).setValue(video);
            dialogbox.show();
            TextView dialogbutton = dialogbox.findViewById(R.id.success_dialog_button);
            dialogbutton.setOnClickListener(view1 -> onBackPressed());
        });
    }
    public static String getYoutubeId(String url) {
        String pattern = "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*";
        Pattern compiledPattern = Pattern.compile(pattern,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}