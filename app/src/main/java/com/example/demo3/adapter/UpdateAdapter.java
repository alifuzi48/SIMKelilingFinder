package com.example.demo3.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo3.R;
import com.example.demo3.activities.DetailLokasi;
import com.example.demo3.activities.Dialoghapus;
import com.example.demo3.activities.UpdateData;
import com.example.demo3.model.ModelUpdate;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class UpdateAdapter extends FirestoreRecyclerAdapter<ModelUpdate, UpdateAdapter.UpdateHolder> {
    private UpdateAdapter.OnItemClickListener listener1;
    private UpdateAdapter.OnItemLongClickListener listener2;

    public UpdateAdapter(@NonNull FirestoreRecyclerOptions<ModelUpdate> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UpdateHolder holder, int position, @NonNull ModelUpdate model) {
        holder.listnama.setText(model.getNama());
    }

    @Override
    public UpdateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_update, parent, false);
        return new UpdateHolder(v);
    }

    class UpdateHolder extends RecyclerView.ViewHolder {
        private TextView listnama;

        public UpdateHolder(@NonNull View itemView) {
            super(itemView);
            listnama = itemView.findViewById(R.id.tvUpdatedata);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener1 != null) {
                        listener1.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener2 != null) {
                        listener2.onItemLongClick(getSnapshots().getSnapshot(position), position);
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(UpdateAdapter.OnItemClickListener listener) {
        this.listener1 = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemLongClickListener(UpdateAdapter.OnItemLongClickListener listener) {
        this.listener2 = listener;
    }
}
