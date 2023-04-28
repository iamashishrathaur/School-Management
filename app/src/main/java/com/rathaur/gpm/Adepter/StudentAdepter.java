package com.rathaur.gpm.Adepter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.Student;
import com.rathaur.gpm.R;
import com.squareup.picasso.Picasso;

public class StudentAdepter extends FirebaseRecyclerAdapter<Student,StudentAdepter.ViewHolder> {
    public StudentAdepter(@NonNull FirebaseRecyclerOptions<Student> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull StudentAdepter.ViewHolder holder, int position, @NonNull Student model) {
        Picasso.get().load(model.getSimage()).fit().placeholder(R.drawable.chat_user).into(holder.imageView);
        holder.name.setText(model.getSname());
        holder.mobile.setText(model.getSmobile());
        String year=model.getSyear();
        if (year.equals("First Year"))
        {
            holder.stander.setText("I");
        }
        else if ( year.equals("Second Year")) {
            holder.stander.setText("II");
        }

        else {
            holder.stander.setText("III");
        }

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.recycler_anim));

    }

    @NonNull
    @Override
    public StudentAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.studentlist_recycle_view,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView name;
        final TextView mobile;
        final TextView stander;
        final CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recycle_student_image);
            name=itemView.findViewById(R.id.recycle_student_name);
            mobile=itemView.findViewById(R.id.recycle_student_mobile);
            stander=itemView.findViewById(R.id.recycle_student_class);
            cardView=itemView.findViewById(R.id.cardView_student_anim);
        }
    }
}
