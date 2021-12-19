package com.example.demo3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo3.R;
import com.example.demo3.adapter.EditAdapter;
import com.example.demo3.adapter.LokasiAdapter;
import com.example.demo3.model.ModelEdit;
import com.example.demo3.model.ModelLokasi;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class Editdata extends AppCompatActivity {
    Toolbar tbEditdata;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView rvEditdata;
    private EditAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);

        tbEditdata = findViewById(R.id.toolbar_Editdata);
        tbEditdata.setTitle("Edit Data SIM Keliling");
        setSupportActionBar(tbEditdata);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        //Query
        Query query = db.collection("simkel0");
        //RecycleviewOptions
        FirestoreRecyclerOptions<ModelEdit> options = new FirestoreRecyclerOptions.Builder<ModelEdit>()
                .setQuery(query, ModelEdit.class)
                .build();
        //adapter
        adapter = new EditAdapter(options);
        //recycleview set
        rvEditdata = findViewById(R.id.rvEditdata);
        rvEditdata.setHasFixedSize(true);
        rvEditdata.setLayoutManager(new LinearLayoutManager(this));
        rvEditdata.setAdapter(adapter);

        adapter.setOnItemClickListener(new LokasiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ModelLokasi ml = documentSnapshot.toObject(ModelLokasi.class);
                String id = documentSnapshot.getId();
                Map<String, Object> idedit = new HashMap<>();
                idedit.put("nama", id);
                db.collection("update").document("buatedit").update(idedit);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MenuAdmin.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
