package com.rathaur.gpm.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.Teacher;
import com.rathaur.gpm.R;
import com.squareup.picasso.Picasso;

public class TeacherAdepter extends FirebaseRecyclerAdapter<Teacher,TeacherAdepter.ViewHolder> {
    public TeacherAdepter(@NonNull FirebaseRecyclerOptions<Teacher> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TeacherAdepter.ViewHolder holder, int position, @NonNull Teacher model) {
        Context context=holder.itemView.getContext();
        Picasso.get().load(model.getTimage()).fit().placeholder(R.drawable.chat_user).into(holder.imageView);
        holder.name.setText(model.getTname());
        holder.mobile.setText(model.getTmobile());
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.recycler_anim));

    }

    @NonNull
    @Override
    public TeacherAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.teacherlist_recycle_view,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView name;
        final TextView mobile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.recycle_teacher_name);
            mobile=itemView.findViewById(R.id.recycle_teacher_mobile);
            imageView=itemView.findViewById(R.id.recycle_teacher_image);
        }
    }
}
