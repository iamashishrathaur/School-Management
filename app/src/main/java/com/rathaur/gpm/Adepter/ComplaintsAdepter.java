package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBase.Student;
import com.rathaur.gpm.R;

public class ComplaintsAdepter extends FirebaseRecyclerAdapter<Student,ComplaintsAdepter.ViewHolder> {
    public ComplaintsAdepter(@NonNull FirebaseRecyclerOptions<Student> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ComplaintsAdepter.ViewHolder holder, int position, @NonNull Student model) {
     holder.complaints.setText(model.getScomplaints());
     holder.date.setText(model.getSdate());
    }
    @NonNull
    @Override
    public ComplaintsAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.complaints_recyclerview,parent,false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaints,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaints=itemView.findViewById(R.id.your_complaints);
            date=itemView.findViewById(R.id.your_complaints_date);
        }
    }
}
