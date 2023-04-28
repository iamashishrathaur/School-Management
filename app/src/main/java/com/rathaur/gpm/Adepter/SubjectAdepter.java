package com.rathaur.gpm.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rathaur.gpm.DataBaseModal.Subject;
import com.rathaur.gpm.R;

import java.util.List;

public class SubjectAdepter extends RecyclerView.Adapter<SubjectAdepter.ViewHolder> {
    final List<Subject> subjects;
    final Context context;

    public SubjectAdepter(List<Subject> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
    }

    @NonNull
    @Override
    public SubjectAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdepter.ViewHolder holder, int position) {
         Subject subject=subjects.get(position);
         holder.sem.setText(subject.getSem());
         holder.sub.setText(subject.getSubject());
         holder.type.setText(subject.getType());
         holder.teacher.setText(subject.getTeacher());
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView sub;
        final TextView type;
        final TextView teacher;
        final TextView sem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sem=itemView.findViewById(R.id.student_sem_number);
            sub=itemView.findViewById(R.id.student_subject_name);
            type=itemView.findViewById(R.id.student_subject_type);
            teacher=itemView.findViewById(R.id.student_subject_teacher);
        }
    }
}
