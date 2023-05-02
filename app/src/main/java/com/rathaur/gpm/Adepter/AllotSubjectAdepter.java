package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.rathaur.gpm.DataBaseModal.AllotSubjectModal;
import com.rathaur.gpm.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AllotSubjectAdepter extends FirebaseRecyclerAdapter<AllotSubjectModal,AllotSubjectAdepter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AllotSubjectAdepter(@NonNull FirebaseRecyclerOptions<AllotSubjectModal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AllotSubjectAdepter.ViewHolder holder, int position, @NonNull AllotSubjectModal model) {
        //Picasso.get().load(model.getName()).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.subject.setText(model.getSubject());

        String ref=getRef(position).getKey();
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("allotsubject").child(Objects.requireNonNull(ref)).removeValue();
            }
        });
    }

    @NonNull
    @Override
    public AllotSubjectAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.allot_subject_semple,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout delete;
        TextView name,subject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.allot_subject_teacher_image);
            name=itemView.findViewById(R.id.allot_subject_teacher_name);
            subject=itemView.findViewById(R.id.allot_subject_teacher_subject);
            delete=itemView.findViewById(R.id.delete_allot_subject_teacher);
        }
    }
}
