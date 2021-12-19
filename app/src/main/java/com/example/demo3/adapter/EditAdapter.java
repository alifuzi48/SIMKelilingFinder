package com.example.demo3.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo3.R;
import com.example.demo3.activities.DetailEdit;
import com.example.demo3.model.ModelEdit;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class EditAdapter extends FirestoreRecyclerAdapter<ModelEdit, EditAdapter.EditHolder> {
    private LokasiAdapter.OnItemClickListener listener;

    public EditAdapter(@NonNull FirestoreRecyclerOptions<ModelEdit> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EditHolder holder, int position, @NonNull ModelEdit model) {
        holder.listnama.setText(model.getNama());
        holder.listimg.setText(model.getImgurl());
        holder.listjambuka.setText(model.getJambuka());
        holder.listjamtutup.setText(model.getJamtutup());
        holder.listharibuka.setText(model.getHaribuka());
        holder.listharitutup.setText(model.getHaritutup());
        holder.listlat.setText(model.getLatitude() + "");
        holder.listlong.setText(model.getLongitude() + "");

    }

    @Override
    public EditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_edit, parent, false);
        return new EditHolder(v);
    }

    class EditHolder extends RecyclerView.ViewHolder{
        private TextView listnama;
        private TextView listimg;
        private TextView listjambuka;
        private TextView listjamtutup;
        private TextView listharibuka;
        private TextView listharitutup;
        private TextView listlat;
        private TextView listlong;


        public EditHolder(@NonNull View itemView) {
            super(itemView);
            listnama = itemView.findViewById(R.id.tvEditnama);
            listimg = itemView.findViewById(R.id.tvEditImgurl);
            listjambuka = itemView.findViewById(R.id.tvJambuka);
            listjamtutup = itemView.findViewById(R.id.tvJamtutup);
            listharibuka = itemView.findViewById(R.id.tvHaribuka);
            listharitutup = itemView.findViewById(R.id.tvHaritutup);
            listlat = itemView.findViewById(R.id.tvEditlatitude);
            listlong = itemView.findViewById(R.id.tvEditlongitude);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        Intent i = new Intent (itemView.getContext(), DetailEdit.class);
                        i.putExtra("nama", listnama.getText().toString());
                        i.putExtra("imgurl", listimg.getText().toString());
                        i.putExtra("jambuka", listjambuka.getText().toString());
                        i.putExtra("jamtutup", listjamtutup.getText().toString());
                        i.putExtra("haribuka", listharibuka.getText().toString());
                        i.putExtra("haritutup", listharitutup.getText().toString());
                        i.putExtra("latitude", listlat.getText().toString());
                        i.putExtra("longitude", listlong.getText().toString());
                        itemView.getContext().startActivity(i);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(LokasiAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
