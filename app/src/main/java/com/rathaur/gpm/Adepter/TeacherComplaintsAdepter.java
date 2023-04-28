package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.Teacher;
import com.rathaur.gpm.R;

public class TeacherComplaintsAdepter extends FirebaseRecyclerAdapter<Teacher,TeacherComplaintsAdepter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TeacherComplaintsAdepter(@NonNull FirebaseRecyclerOptions<Teacher> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TeacherComplaintsAdepter.ViewHolder holder, int position, @NonNull Teacher model) {
        holder.complaints.setText(model.getTcomplaints());
        holder.date.setText(model.getTdate());

    }

    @NonNull
    @Override
    public TeacherComplaintsAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complaints_recyclerview,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView complaints;
        final TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaints=itemView.findViewById(R.id.your_complaints);
            date=itemView.findViewById(R.id.your_complaints_date);
        }
    }
}
