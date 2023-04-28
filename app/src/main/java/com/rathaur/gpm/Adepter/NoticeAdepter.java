package com.rathaur.gpm.Adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.Notice;
import com.rathaur.gpm.R;

public class NoticeAdepter extends FirebaseRecyclerAdapter<Notice,NoticeAdepter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NoticeAdepter(@NonNull FirebaseRecyclerOptions<Notice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoticeAdepter.ViewHolder holder, int position, @NonNull Notice model) {
       holder.notice.setText(model.getSubject());
       holder.topic.setText(model.getTopic());
       holder.date.setText(model.getDate());

    }

    @NonNull
    @Override
    public NoticeAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_sample,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView notice;
        final TextView date;
        final TextView topic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notice=itemView.findViewById(R.id.your_notice);
            date=itemView.findViewById(R.id.your_notice_date);
            topic=itemView.findViewById(R.id.your_notice_topic);
        }
    }
}
