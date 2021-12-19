package com.example.demo3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo3.R;
import com.example.demo3.model.ModelInfo;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder>{
    private List<ModelInfo> items;
    private DetailAdapter.onSelectData onSelectData;

    public interface onSelectData {
        void onSelected(ModelInfo mdlinfo);
    }

    public DetailAdapter(List<ModelInfo> items, DetailAdapter.onSelectData xSelectData) {
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelInfo data = items.get(position);
        holder.imgDetailinfo.setImageResource(data.getImgSrc());
        holder.tvDetailinfo.setText(data.getTxtName());
        holder.cvDetailinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvDetailinfo;
        public TextView tvDetailinfo;
        public ImageView imgDetailinfo;

        public ViewHolder(View itemView) {
            super(itemView);
            cvDetailinfo = itemView.findViewById(R.id.cvDetailinfo);
            tvDetailinfo = itemView.findViewById(R.id.tvDetailinfo);
            imgDetailinfo = itemView.findViewById(R.id.imgDetailinfo);
        }
    }
}