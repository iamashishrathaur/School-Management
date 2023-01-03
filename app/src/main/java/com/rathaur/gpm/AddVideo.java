package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBase.Video;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVideo extends AppCompatActivity {
   TextInputEditText name,url;
   TextView submit;
   String senrollment;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        submit=findViewById(R.id.vedio_submit);
        name=findViewById(R.id.video_name);
        url=findViewById(R.id.video_url);
        getSupportActionBar().hide();
        SharedPreferences sharedPreferences=getSharedPreferences("student",MODE_PRIVATE);
        String sname=sharedPreferences.getString("sname","");
        String semail=sharedPreferences.getString("semail","");
        senrollment=sharedPreferences.getString("senrollment","");
        String sprofession=sharedPreferences.getString("sprofession","");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date= Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String sdate= dateFormat.format(date);
                String sname=name.getText().toString();
                String surl=url.getText().toString();
                String id=getYoutubeId(surl);
                String uid= UUID.randomUUID().toString();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("video");
                Video video=new Video(senrollment,sname,sdate,id);
                databaseReference.child(uid).setValue(video);
            }
        });
    }
    public static String getYoutubeId(String url) {
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
        Pattern compiledPattern = Pattern.compile(pattern,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }/*from w  w  w.  j a  va  2 s .c om*/
        return null;
    }
}