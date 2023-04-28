package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rathaur.gpm.DataBaseModal.SlideItem;
import com.rathaur.gpm.R;

import java.util.List;

public class ViewPagerAdepter extends RecyclerView.Adapter<ViewPagerAdepter.ViewHolder> {
    private final List<SlideItem> slideItems;
    private final ViewPager2 viewPager2;

    public ViewPagerAdepter(List<SlideItem> slideItems, ViewPager2 viewPager2) {
        this.slideItems = slideItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewPagerAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdepter.ViewHolder holder, int position) {
        holder.setImage(slideItems.get(position));

    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final RoundedImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.login_viewPager_image);

        }
        void setImage(SlideItem slideItem){
            imageView.setImageResource(slideItem.getImage());
        }
    }
}
