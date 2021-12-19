package com.example.demo3.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo3.R;
import com.example.demo3.activities.DetailLokasi;
import com.example.demo3.model.ModelLokasi;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class LokasiAdapter extends FirestoreRecyclerAdapter<ModelLokasi, LokasiAdapter.lokasiHolder>  {
    private OnItemClickListener listener;

    public LokasiAdapter(@NonNull FirestoreRecyclerOptions<ModelLokasi> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull lokasiHolder holder, int position, @NonNull ModelLokasi model) {
        Picasso.get().load(model.getImgurl()).into(holder.list_imgurl);
        holder.list_nama.setText(model.getNama());
        holder.listjambuka.setText(model.getJambuka());
        holder.listjamtutup.setText(model.getJamtutup());
        holder.listharibuka.setText(model.getHaribuka());
        holder.listharitutup.setText(model.getHaritutup());
        holder.list_longitude.setText(model.getLongitude() + "");
        holder.list_latitude.setText(model.getLatitude() + "");
        holder.list_latpeng.setText(model.getLatPeng() + "");
        holder.list_longpeng.setText(model.getLongPeng()  + "");
        holder.list_jarak.setText(model.getJarak() + "" + " KM");
    }

    @NonNull
    @Override
    public lokasiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lokasi_simkel, parent, false);
        return new lokasiHolder(view);
    }

    public class lokasiHolder extends RecyclerView.ViewHolder {
        private TextView list_nama;
        private TextView listjambuka;
        private TextView listjamtutup;
        private TextView listharibuka;
        private TextView listharitutup;
        private TextView list_latitude;
        private TextView list_longitude;
        private TextView list_jarak;
        private TextView list_latpeng;
        private TextView list_longpeng;
        private ImageView list_imgurl;

        public lokasiHolder(@NonNull View itemView) {
            super(itemView);

            list_nama = itemView.findViewById(R.id.tvNamaLokasi);
            list_imgurl = itemView.findViewById(R.id.imgLokasi);
            listjambuka = itemView.findViewById(R.id.tvJambukalok);
            listjamtutup = itemView.findViewById(R.id.tvJamtutuplok);
            listharibuka = itemView.findViewById(R.id.tvharibukalok);
            listharitutup = itemView.findViewById(R.id.tvharitutuplok);
            list_latitude = itemView.findViewById(R.id.tvlatitude);
            list_longitude = itemView.findViewById(R.id.tvlongitude);
            list_jarak = itemView.findViewById(R.id.tvjarakLokasi);
            list_latpeng = itemView.findViewById(R.id.tvlatpeng);
            list_longpeng = itemView.findViewById(R.id.tvlongpeng);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        Intent i = new Intent (itemView.getContext(), DetailLokasi.class);
                        i.putExtra("nama", list_nama.getText().toString());
                        i.putExtra("jambuka", listjambuka.getText().toString());
                        i.putExtra("jamtutup", listjamtutup.getText().toString());
                        i.putExtra("haribuka", listharibuka.getText().toString());
                        i.putExtra("haritutup", listharitutup.getText().toString());
                        i.putExtra("latitude", list_latitude.getText().toString());
                        i.putExtra("longitude", list_longitude.getText().toString());
                        i.putExtra("latPeng", list_latpeng.getText().toString());
                        i.putExtra("longPeng", list_longpeng.getText().toString());
                        itemView.getContext().startActivity(i);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
