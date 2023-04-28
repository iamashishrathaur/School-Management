package com.rathaur.gpm.Adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.rathaur.gpm.Chat;
import com.rathaur.gpm.DataBaseModal.Users;
import com.rathaur.gpm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdepter extends RecyclerView.Adapter<ChatListAdepter.ViewHolder>  {
    private final Context context;
    public final List<Users> userList;

    public ChatListAdepter(Context context) {
        this.context = context;
        userList=new ArrayList<>();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void add(Users users){
        userList.add(users);
        notifyDataSetChanged();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void clear(){
      userList.clear();
      notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatListAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdepter.ViewHolder holder, int position) {
        Users users=userList.get(position);
        holder.name.setText(users.getName());
        holder.enroll.setText(users.getEnrollment());
        Picasso.get().load(users.getImage()).placeholder(R.drawable.chat_user).fit().into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(context,Chat.class);
              intent.putExtra("id",users.getEnrollment());
              context.startActivity(intent);
              ((AppCompatActivity)context).finish();
            }
        });
   }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView enroll;
        final ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.chat_user_name);
            enroll=itemView.findViewById(R.id.chat_user_enrollment);
            imageView=itemView.findViewById(R.id.chat_user_image);
        }
    }
}
