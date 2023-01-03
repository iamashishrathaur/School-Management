package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBase.Homework;
import com.rathaur.gpm.R;

public class HomeworkAdepter extends FirebaseRecyclerAdapter<Homework,HomeworkAdepter.ViewHolder> {

    public HomeworkAdepter(@NonNull FirebaseRecyclerOptions<Homework> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeworkAdepter.ViewHolder holder, int position, @NonNull Homework model) {
        holder.subject.setText(model.getSubject());
        holder.heading.setText(model.getHeading());
        holder.content.setText(model.getContent());
        holder.time.setText(model.getTime());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_anim));
    }

    @NonNull
    @Override
    public HomeworkAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_recycler_view_sample,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject,heading,content,time;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.recycle_student_subject_name);
            heading=itemView.findViewById(R.id.recycle_student_subject_heading);
            content=itemView.findViewById(R.id.recycle_student_subject_context);
            time=itemView.findViewById(R.id.recycle_student_subject_time);
            cardView=itemView.findViewById(R.id.home_work_cardView);
        }
    }

}