package com.rathaur.gpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.Adepter.RecentChatAdepter;
import com.rathaur.gpm.DataBaseModal.ChatModal;
import com.rathaur.gpm.DataBaseModal.ModalChatList;
import com.rathaur.gpm.DataBaseModal.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RecentChat extends AppCompatActivity {
 FloatingActionButton floatingActionButton;
 RecyclerView recyclerView;
 RecentChatAdepter adepter;
  String enrollment;
 DatabaseReference reference;
 List<ModalChatList> chatListList;
 List<Users> userList;

 RelativeLayout back;
 ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chat);
        Objects.requireNonNull(getSupportActionBar()).hide();
        floatingActionButton=findViewById(R.id.open_chat_list);
        recyclerView=findViewById(R.id.recent_chat_recycler);
        back=findViewById(R.id.recent_chat_back_pressed);
        progressBar=findViewById(R.id.recent_chat_progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        enrollment=sharedPreferences.getString("enrollment","");
        chatListList=new ArrayList<>();
        reference=  FirebaseDatabase.getInstance().getReference("ChatList").child(enrollment);
        reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               chatListList.clear();
               progressBar.setVisibility(View.GONE);
              for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                  ModalChatList chatList=dataSnapshot.getValue(ModalChatList.class);
                  chatListList.add(chatList);
              }
              loadChats();
              ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
              itemTouchHelper.attachToRecyclerView(recyclerView);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               progressBar.setVisibility(View.GONE);
           }
       });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChatUserList.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadChats() {

        userList=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               userList.clear();

               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   Users user=dataSnapshot.getValue(Users.class);
                   for (ModalChatList chatList:chatListList){
                       if (user.getEnrollment() !=null && user.getEnrollment().equals(chatList.getId())){
                           userList.add(user);
                           break;
                       }
                   }

                   //adapter
                   adepter=new RecentChatAdepter(RecentChat.this,userList);
                   recyclerView.setAdapter(adepter);
                   adepter.notifyDataSetChanged();
                   for (int i=0; i< userList.size(); i++){
                       lastMessage(userList.get(i).getEnrollment());
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void lastMessage(String userId) {
      DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Chats");
      reference1.addValueEventListener(new ValueEventListener() {
          @SuppressLint("NotifyDataSetChanged")
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              String theLastMessage="default";
              String theLastMessageTime=null;
              for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                  ChatModal chat=dataSnapshot.getValue(ChatModal.class);
                  if (chat==null){
                      continue;
                  }
                  String sender=chat.getSender();
                  String receiver=chat.getReceiver();
                  if (sender==null  || receiver==null){
                     continue;
                  }
                  if (chat.getReceiver().equals(enrollment) && chat.getSender().equals(userId) ||
                      chat.getReceiver().equals(userId) && chat.getSender().equals(enrollment)){
                      theLastMessage=chat.getMessage();
                      theLastMessageTime=chat.getTimestamp();
                  }
              }
              adepter.setLastMessageMap(userId,theLastMessage);
              adepter.setLastMessageTimeMap(userId,theLastMessageTime);
              adepter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
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
    final ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int position=viewHolder.getLayoutPosition();
            ModalChatList deleteUser=chatListList.get(position);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}