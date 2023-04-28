package com.rathaur.gpm.Adepter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rathaur.gpm.DataBaseModal.PDF;
import com.rathaur.gpm.FullShowStudyMaterials;
import com.rathaur.gpm.R;

public class PdfAdepter extends FirebaseRecyclerAdapter<PDF,PdfAdepter.ViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PdfAdepter(@NonNull FirebaseRecyclerOptions<PDF> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull PdfAdepter.ViewHolder holder, int position, @NonNull PDF model) {
          holder.name.setText(model.getPdfName()+".pdf");
          holder.time.setText(model.getTime());
          holder.date.setText(model.getDate());
          Context context=holder.itemView.getContext();
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(context, FullShowStudyMaterials.class);
                  intent.putExtra("pdfUri", model.getUri());
                  intent.putExtra("pdfName", model.getPdfName());
                  context.startActivity(intent);
              }
          });
    }

    @NonNull
    @Override
    public PdfAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_semple,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView date;
        final TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.recycle_pdf_name);
            date=itemView.findViewById(R.id.recycle_pdf_date);
            time=itemView.findViewById(R.id.recycle_pdf_time);
        }
    }
}
