package com.rathaur.gpm.Adepter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rathaur.gpm.DataBaseModal.ChatModal;
import com.rathaur.gpm.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ChatAdepter extends RecyclerView.Adapter<ChatAdepter.ViewHolder> {
    private static final int VIEW_TYPE_RECEIVE = 0;
    private static final int VIEW_TYPE_SENT = 1;

    final Context context;
    final List<ChatModal> chatModals;

    public ChatAdepter(Context context, List<ChatModal> chatModals) {
        this.context = context;
        this.chatModals = chatModals;
    }

    @NonNull
    @Override
    public ChatAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.sent_chat_layout, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receive_chat_layout, parent, false);
            return new ViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChatAdepter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ChatModal modal=chatModals.get(position);
        holder.textView.setText(modal.getMessage());
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(modal.getTimestamp()));
        String timedate = DateFormat.format("hh:mm", calendar).toString();
       // holder.time.setText(timedate);
        boolean isSeen=modal.isDilihat();
         if (VIEW_TYPE_SENT==getItemViewType(position)){
             if (isSeen){
                 holder.isSeen.setVisibility(View.VISIBLE);
                 holder.isUnseen.setVisibility(View.GONE);
             }
             else {
                 holder.isSeen.setVisibility(View.GONE);
                 holder.isUnseen.setVisibility(View.VISIBLE);
             }
         }

           holder.chatLayout.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View view) {
                   BottomSheetDialog dialog=new BottomSheetDialog(context);
                   dialog.setContentView(R.layout.unsend_bootom_dialog);
                   dialog.show();
                   TextView cancel=dialog.findViewById(R.id.chat_unsent_cancel);
                   TextView resend=dialog.findViewById(R.id.chat_unsent_confirm);
                   Objects.requireNonNull(cancel).setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           dialog.dismiss();
                       }
                   });
                   Objects.requireNonNull(resend).setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                          deleteMsg(position,holder.itemView);
                          dialog.dismiss();
                       }
                   });
                   return true;
               }
           });
    }

    @Override
    public int getItemCount() {
        return chatModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final RelativeLayout chatLayout;
        final TextView time;
        final ImageView isSeen;
        final ImageView isUnseen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.send_chat_text);
            chatLayout=itemView.findViewById(R.id.chat_text_layout);
            time=itemView.findViewById(R.id.send_chat_time);
            isSeen=itemView.findViewById(R.id.isSeen);
            isUnseen=itemView.findViewById(R.id.isUnseen);
        }
    }

   @Override
    public int getItemViewType(int position) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("user",MODE_PRIVATE);
        String enrollment=sharedPreferences.getString("enrollment","");
        if (chatModals.get(position).getSender().equals(enrollment)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVE;
        }
    }
    private void deleteMsg(int position, View view) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("user",MODE_PRIVATE);
        String myuid=sharedPreferences.getString("enrollment","");
        String msgtimestmp = chatModals.get(position).getTimestamp();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Chats");
        Query query = dbref.orderByChild("timestamp").equalTo(msgtimestmp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.child("sender").getValue().equals(myuid)) {
                        try {
                            chatModals.remove(position);
                            notifyDataSetChanged();
                            dataSnapshot1.getRef().removeValue();
                            Snackbar.make(view,"Unsend your message",Snackbar.LENGTH_LONG).show();

                        }
                        catch (IndexOutOfBoundsException e)
                        {
                            Log.d("chat",e.getMessage());
                            throw e;
                        }

                    } else {
                        Snackbar snackbar = Snackbar.make(view, "You Can Unsend Only Your msg....", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
