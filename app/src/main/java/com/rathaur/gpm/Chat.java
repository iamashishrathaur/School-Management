package com.rathaur.gpm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rathaur.gpm.Adepter.ChatAdepter;
import com.rathaur.gpm.DataBaseModal.ChatModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Chat extends AppCompatActivity {
   RecyclerView recyclerView;
   RoundedImageView imageView;
   TextView textView;
   ImageView send;
   RelativeLayout back;
    EditText chat;
    String enrollment;
    String receiverId;
    ChatAdepter chatAdepter;
    final List<ChatModal> chatList=new ArrayList<>();
   TextView status;
    String receiverName;
    LinearLayoutManager manager;
    ChatModal modelChat;
    String message;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView=findViewById(R.id.chat_show);
        textView=findViewById(R.id.chat_user_name_show);
        imageView=findViewById(R.id.chat_user_image_show);
        send=findViewById(R.id.chat_send_button);
        back=findViewById(R.id.chat_show_back_pressed);
        chat=findViewById(R.id.chat_edit_text);
        status=findViewById(R.id.chat_user_status_show);


        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        enrollment=sharedPreferences.getString("enrollment","");
        Intent intent=getIntent();
        receiverId=intent.getStringExtra("id");
        receiverName=intent.getStringExtra("name");
        recyclerView.setHasFixedSize(true);
        manager=new LinearLayoutManager(Chat.this);
        recyclerView.setLayoutManager(manager);

           DatabaseReference reference=FirebaseDatabase.getInstance().getReference("User");
           Query query=reference.orderByChild("enrollment").equalTo(receiverId);
           query.addValueEventListener(new ValueEventListener() {
               @SuppressLint("SetTextI18n")
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                      String name=""+ dataSnapshot.child("name").getValue();
                      String image=""+ dataSnapshot.child("image").getValue();
                      String onlinestatus = "" + dataSnapshot.child("onlineStatus").getValue();
                      String typingto = "" + dataSnapshot.child("typingTo").getValue();
                      //set data
                        textView.setText(name);
                        //typing
                      if (typingto.equals(enrollment)){
                          status.setText("typing...");
                      }
                      else {
                          // online
                          if (onlinestatus.equals("online")) {
                              status.setText(onlinestatus);
                          } else {
                              Calendar calendar = Calendar.getInstance();
                              calendar.setTimeInMillis(Long.parseLong(onlinestatus));
                              String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
                              status.setText("Last Seen: " + timedate);
                          }
                      }
                      Picasso.get().load(image).fit().placeholder(R.drawable.chat_user).into(imageView);
                  }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().trim().length()==0){
                checkTypingStatus("noOne");
            }
            else {
                checkTypingStatus(receiverId);
            }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chat.getText().toString().trim().isEmpty()){
                    message=chat.getText().toString();
                    chat.setText("");
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    modelChat=new ChatModal(message,receiverId,enrollment,"text",timestamp,false);
                    chatList.add(modelChat);
                    chatAdepter=new ChatAdepter(Chat.this,chatList);
                    recyclerView.setAdapter(chatAdepter);
                    recyclerView.scrollToPosition(chatList.size()-1);

                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Chats");
                            databaseReference.push().setValue(modelChat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("ChatList").child(receiverId).child(enrollment);
                                    ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (!snapshot.exists()) {
                                                ref1.child("id").setValue(enrollment);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("ChatList").child(enrollment).child(receiverId);
                                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (!snapshot.exists()) {
                                                ref2.child("id").setValue(receiverId);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            });
                        }
            }
        });
    }

    //online and typing
    @Override
    protected void onPause() {
        super.onPause();
        String timestamp = String.valueOf(System.currentTimeMillis());
        checkOnlineStatus(timestamp);
        checkTypingStatus("noOne");

    }

    //online and typing
    @Override
    protected void onResume() {
        checkOnlineStatus("online");
        super.onResume();
        checkSeenStatus();
    }

    private void checkSeenStatus() {
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Chats");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    modelChat = dataSnapshot1.getValue(ChatModal.class);
                    if (modelChat.getReceiver().equals(enrollment)
                            && modelChat.getSender().equals(receiverId)) {

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("dilihat", true);
                        dataSnapshot1.getRef().updateChildren(hashMap);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    // check online status
    private void checkOnlineStatus(String status) {
        // check online status
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User").child(enrollment);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("onlineStatus", status);
        dbref.updateChildren(hashMap);
    }


    // typing status here..
    private void checkTypingStatus(String typing) {

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("User").child(enrollment);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("typingTo", typing);
        dbref.updateChildren(hashMap);
    }

// read data here...
    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Chats");
        dbref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    modelChat = dataSnapshot1.getValue(ChatModal.class);
                    if (modelChat.getSender().equals(enrollment) &&
                            modelChat.getReceiver().equals(receiverId) ||
                            modelChat.getReceiver().equals(enrollment)
                                    && modelChat.getSender().equals(receiverId)) {
                        chatList.add(modelChat);
                    }
                    chatAdepter=new ChatAdepter(Chat.this,chatList);
                    recyclerView.setAdapter(chatAdepter);
                    recyclerView.scrollToPosition(chatList.size()-1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}