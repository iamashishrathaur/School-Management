package com.rathaur.gpm.Adepter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rathaur.gpm.DataBaseModal.Gallery;
import com.rathaur.gpm.FullImage;
import com.rathaur.gpm.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class RecycleAdepter extends FirebaseRecyclerAdapter<Gallery,RecycleAdepter.ViewHolder> {
    public RecycleAdepter(@NonNull FirebaseRecyclerOptions<Gallery> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Gallery model) {
        Context context=holder.itemView.getContext();
        Picasso.get().load(model.getImage()).fit().into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                //Toast.makeText(context, ""+getItemCount(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "try again", Toast.LENGTH_SHORT).show();
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FullImage.class);
                intent.putExtra("uri",model.getImage());
                context.startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_single_image,parent,false);
        return new ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        final RoundedImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.single_image);

        }
    }
}
