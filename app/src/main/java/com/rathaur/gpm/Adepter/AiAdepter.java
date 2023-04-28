package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rathaur.gpm.DataBaseModal.AiMessageModal;
import com.rathaur.gpm.R;

import java.util.List;

public class AiAdepter extends RecyclerView.Adapter<AiAdepter.MyViewHolder> {

    final List<AiMessageModal> messageModalList;

    public AiAdepter(List<AiMessageModal> messageModalList) {
        this.messageModalList = messageModalList;
    }

    @NonNull
    @Override
    public AiAdepter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_bot_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AiAdepter.MyViewHolder holder, int position) {
         AiMessageModal messageModal=messageModalList.get(position);
         if (messageModal.getSentBy().equals(AiMessageModal.SENT_BY_ME)){
            holder.leftText.setVisibility(View.GONE);
            holder.rightText.setVisibility(View.VISIBLE);
            holder.rightText.setText(messageModal.getMessage());
         }else {
             holder.leftText.setVisibility(View.VISIBLE);
             holder.rightText.setVisibility(View.GONE);
             holder.leftText.setText(messageModal.getMessage());
         }
    }

    @Override
    public int getItemCount() {
        return messageModalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView leftText;
        final TextView rightText;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leftText=itemView.findViewById(R.id.left_chat_bot_text);
            rightText=itemView.findViewById(R.id.right_chat_bot_text);
        }
    }
}
