package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.ChatListAdepter;
import com.rathaur.gpm.DataBaseModal.Users;

import java.util.HashMap;
import java.util.Objects;

public class ChatUserList extends AppCompatActivity {
    RecyclerView recyclerView;
    String enrollment;
    ChatListAdepter adepter;
    TextView count;
    DatabaseReference databaseReference;
    RelativeLayout back;
//            searchBack;
    ImageView search;
    AppBarLayout barLayout;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user_list);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView = findViewById(R.id.user_chat_list);
        count = findViewById(R.id.count_of_chat_user);
        back = findViewById(R.id.chat_user_back_pressed);
        search = findViewById(R.id.chat_user_search);
        // searchBack=findViewById(R.id.search_layout);
        barLayout = findViewById(R.id.appbar_chat_user);
        progressBar = findViewById(R.id.chat_user_list_progress_bar);
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        enrollment = sharedPreferences.getString("enrollment", "");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adepter = new ChatListAdepter(this);
        recyclerView.setAdapter(adepter);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adepter.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String uid = dataSnapshot.getKey();
                    if (!uid.equals(enrollment)) {
                        Users users = dataSnapshot.getValue(Users.class);
                        adepter.add(users);
                        String size = String.valueOf(adepter.getItemCount());
                        count.setText(size + " " + "users");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
@Override
protected void onPause() {
    super.onPause();
    String timestamp = String.valueOf(System.currentTimeMillis());
    checkOnlineStatus(timestamp);
    checkTypingStatus("noOne");
}
    @Override
    protected void onResume() {
        checkOnlineStatus("online");
        super.onResume();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
    private void checkOnlineStatus(String status) {
        // check online status
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User").child(enrollment);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("onlineStatus", status);
        dbref.updateChildren(hashMap);
    }

    private void checkTypingStatus(String typing) {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User").child(enrollment);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("typingTo", typing);
        dbref.updateChildren(hashMap);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}