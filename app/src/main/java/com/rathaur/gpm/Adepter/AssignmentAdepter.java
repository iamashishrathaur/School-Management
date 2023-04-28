package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.Assignment;
import com.rathaur.gpm.R;

public class AssignmentAdepter extends FirebaseRecyclerAdapter<Assignment,AssignmentAdepter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AssignmentAdepter(@NonNull FirebaseRecyclerOptions<Assignment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AssignmentAdepter.ViewHolder holder, int position, @NonNull Assignment model) {
    holder.date.setText(model.getLdate());
    holder.teacher.setText(model.getTname());
    holder.topic.setText(model.getTopic());
    holder.subject.setText(model.getSubject());

    }

    @NonNull
    @Override
    public AssignmentAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_semple,parent,false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView teacher;
        final TextView subject;
        final TextView topic;
        final TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher=itemView.findViewById(R.id.recycle_student_assignment_teacher);
            subject=itemView.findViewById(R.id.recycle_student_assignment_subject);
            topic=itemView.findViewById(R.id.recycle_student_assignment_topic);
            date=itemView.findViewById(R.id.recycle_student_assignment_lastTime);
        }
    }
}
