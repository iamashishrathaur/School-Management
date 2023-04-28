package com.rathaur.gpm.Adepter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rathaur.gpm.Chat;
import com.rathaur.gpm.DataBaseModal.Users;
import com.rathaur.gpm.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RecentChatAdepter extends RecyclerView.Adapter<RecentChatAdepter.ViewHolder>{
    final Context context;
    final List<Users> userList;
    private final HashMap<String, String> lastMessageMap;
    private final HashMap<String, String> lastMessageTimeMap;

    public RecentChatAdepter(Context context, List<Users> userList) {
        this.context = context;
        this.userList = userList;
        lastMessageMap=new HashMap<>();
        lastMessageTimeMap=new HashMap<>();
    }

    @NonNull
    @Override
    public RecentChatAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_chat_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatAdepter.ViewHolder holder, int position) {
//        SharedPreferences sharedPreferences=context.getSharedPreferences("student",MODE_PRIVATE);
//        String enrollment=sharedPreferences.getString("senrollment","");
        Users user=userList.get(position);
        String hisUid=user.getEnrollment();
        String userImage=user.getImage();
        String lastMessage=lastMessageMap.get(hisUid);
        String lastMessageTime=lastMessageTimeMap.get(hisUid);
        String userName=user.getName();
        String onlinestatus=user.getOnlineStatus();
        Calendar calendar = Calendar.getInstance();

        if (lastMessageTime != null) {
            calendar.setTimeInMillis(Long.parseLong(lastMessageTime));
        }
        String lastTime = DateFormat.format("hh:mm aa", calendar).toString();
        holder.name.setText(userName);

             // online status

           if (onlinestatus.equals("online")) {
                holder.status.setText(onlinestatus);
          }


        // last msg
        if (lastMessage ==null || lastMessage.equals("default")){
            holder.lmessage.setVisibility(View.GONE);
        }
        else {
            holder.lmessage.setVisibility(View.VISIBLE);
            holder.lmessage.setText(lastMessage);
        }
        // last msg time
        if (lastMessageTime==null || lastMessageTime.equals("default")){
            holder.ltime.setVisibility(View.GONE);
        }
        else{
            holder.ltime.setVisibility(View.VISIBLE);
            holder.ltime.setText(lastTime);
        }
        // load images
        try {
            Picasso.get().load(userImage).fit().placeholder(R.drawable.chat_user).into(holder.imageView);

        }catch (Exception e){
            Picasso.get().load(R.drawable.chat_user).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Chat.class);
                intent.putExtra("id",hisUid);
                intent.putExtra("name",userName);
                context.startActivity(intent);
            }
        });
    }
    public void setLastMessageMap(String userId,String lastMessage){
        lastMessageMap.put(userId,lastMessage);
    }
    public void setLastMessageTimeMap(String userId,String lastMessageTime){
        lastMessageTimeMap.put(userId,lastMessageTime);
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final RoundedImageView imageView;
        final TextView name;
        final TextView lmessage;
        final TextView ltime;
        final TextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.recent_chat_user_name);
            imageView=itemView.findViewById(R.id.recent_chat_user_image);
            lmessage=itemView.findViewById(R.id.recent_last_chat);
            ltime=itemView.findViewById(R.id.recent_chat_last_time);
            status=itemView.findViewById(R.id.recent_chat_status);
        }
    }
}
