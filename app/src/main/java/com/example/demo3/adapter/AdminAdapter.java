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
import com.example.demo3.model.ModelAdmin;
import com.example.demo3.model.ModelInfo;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{
    private List<ModelAdmin> items;
    private AdminAdapter.onSelectData onSelectData;

    public interface onSelectData {
        void onSelected(ModelAdmin mdladmin);
    }

    public AdminAdapter(List<ModelAdmin> items, AdminAdapter.onSelectData xSelectData) {
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ModelAdmin data = items.get(position);
        holder.imgMenuadmin.setImageResource(data.getImgSrc());
        holder.tvMenuadmin.setText(data.getTxtName());
        holder.cvMenuadmin.setOnClickListener(new View.OnClickListener() {
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
        public CardView cvMenuadmin;
        public TextView tvMenuadmin;
        public ImageView imgMenuadmin;

        public ViewHolder(View itemView) {
            super(itemView);
            cvMenuadmin = itemView.findViewById(R.id.cvMenuadmin);
            tvMenuadmin = itemView.findViewById(R.id.tvMenuadmin);
            imgMenuadmin = itemView.findViewById(R.id.imgMenuadmin);
        }
    }
}
