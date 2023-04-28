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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.Homework;
import com.rathaur.gpm.HomeWorkFull;
import com.rathaur.gpm.R;

public class HomeworkAdepter extends FirebaseRecyclerAdapter<Homework,HomeworkAdepter.ViewHolder> {

    public HomeworkAdepter(@NonNull FirebaseRecyclerOptions<Homework> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeworkAdepter.ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Homework model) {
        Context context=holder.itemView.getContext();
        holder.subject.setText(model.getSubject());
        holder.heading.setText(model.getHeading());
        // holder.content.setText(model.getContent());
        holder.time.setText(model.getTime());
        String refKey = getRef(position).getKey();
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_anim));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HomeWorkFull.class);
                    intent.putExtra("subject", model.getSubject());
                    intent.putExtra("heading", model.getHeading());
                    intent.putExtra("content", model.getContent());
                    intent.putExtra("date", model.getTime());
                    intent.putExtra("position", refKey);
                    context.startActivity(intent);
                }
            });
    }

    @NonNull
    @Override
    public HomeworkAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_recycler_view_sample,parent,false);
        return new ViewHolder(view);
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getRef().removeValue();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView subject;
        final TextView heading;
        TextView content;
        final TextView time;
        final CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.recycle_student_subject_name);
            heading=itemView.findViewById(R.id.recycle_student_subject_heading);
           // content=itemView.findViewById(R.id.recycle_student_subject_context);
            time=itemView.findViewById(R.id.recycle_student_subject_time);
            cardView=itemView.findViewById(R.id.home_work_cardView);
        }

    }
}