package com.rathaur.gpm.Adepter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBase.Applications;
import com.rathaur.gpm.R;

public class ApplicationAdepter extends FirebaseRecyclerAdapter<Applications,ApplicationAdepter.ViewHolder> {
    public ApplicationAdepter(@NonNull FirebaseRecyclerOptions<Applications> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ApplicationAdepter.ViewHolder holder, int position, @NonNull Applications model) {
        holder.subject.setText(model.getSubject());
        holder.reason.setText(model.getReason()+"...");
        holder.time.setText(model.getTime());
    }

    @NonNull
    @Override
    public ApplicationAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.application_sample,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject,reason,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subject=itemView.findViewById(R.id.recycle_student_application_subject);
            reason=itemView.findViewById(R.id.recycle_student_application_reason);
            time=itemView.findViewById(R.id.recycle_student_application_time);
        }
    }
}
