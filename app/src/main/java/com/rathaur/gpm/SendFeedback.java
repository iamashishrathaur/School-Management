package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.DataBaseModal.FeedBack;

import java.util.Objects;
import java.util.UUID;

public class SendFeedback extends AppCompatActivity {
    ImageView love, sad, happy, sure;
    TextInputEditText feedback;
    String mood = "";
    String enrollment;
    String name;
    TextView submit;
    Dialog dialog;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        Objects.requireNonNull(getSupportActionBar()).hide();
        love = findViewById(R.id.love_click);
        sad = findViewById(R.id.sad_click);
        happy = findViewById(R.id.happy_click);
        sure = findViewById(R.id.not_sure_click);
        feedback = findViewById(R.id.feedback);
        submit = findViewById(R.id.send_feedback);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RelativeLayout back=findViewById(R.id.feedback_back_pressed);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.success_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        TextView text = dialog.findViewById(R.id.success_dialog_text);
        text.setText("Feedback successfully saved");
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        name = sharedPreferences.getString("name", "");
         enrollment = sharedPreferences.getString("enrollment", "");
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                love.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.love_));
                sad.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sad));
                happy.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.happy));
                sure.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sure));
                mood = "love";
            }
        });
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sad.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sad_));
                love.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.love));
                happy.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.happy));
                sure.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sure));
                mood = "sad";
            }
        });
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happy.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.happy_));
                sad.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sad));
                love.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.love));
                sure.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sure));
                mood = "happy";
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sure.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sure_));
                happy.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.happy));
                sad.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.sad));
                love.setBackground(ContextCompat.getDrawable(SendFeedback.this, R.drawable.love));
                mood = "not sure";
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feed = Objects.requireNonNull(feedback.getText()).toString();
                if (!feed.isEmpty()) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Feedback");
                    String uuid = UUID.randomUUID().toString();
                    FeedBack feedBack = new FeedBack(name, enrollment, feed, mood);
                    databaseReference.child(enrollment).child(uuid).setValue(feedBack).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.show();
                            TextView button = dialog.findViewById(R.id.success_dialog_button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onBackPressed();
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(SendFeedback.this, "fill feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}