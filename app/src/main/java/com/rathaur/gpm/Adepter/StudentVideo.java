package com.rathaur.gpm.Adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.Video;
import com.rathaur.gpm.R;
import com.rathaur.gpm.VideoPlayer;

public class StudentVideo extends FirebaseRecyclerAdapter<Video,StudentVideo.viewHolder> {

    public StudentVideo(@NonNull FirebaseRecyclerOptions<Video> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StudentVideo.viewHolder holder, int position, @NonNull Video model) {
        Context context=holder.itemView.getContext();
        holder.time.setText(model.getVtime());
        holder.title.setText(model.getVtitle());

        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_anim));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, VideoPlayer.class);
                intent.putExtra("videoUrl",model.getVurl());
                intent.putExtra("videoTitle",model.getVtitle());
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public StudentVideo.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_recycler_view,parent,false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        final TextView time;
        final TextView title;
        @SuppressLint("SetJavaScriptEnabled")
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.recycle_video_time);
            title=itemView.findViewById(R.id.recycle_video_name);


        }

    }
}
