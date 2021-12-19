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

import com.example.demo3.adapter.UpdateAdapter;
import com.example.demo3.model.ModelUpdate;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class UpdateData extends AppCompatActivity implements Dialoghapus.ExampleDialogListener {
    Toolbar tbUpdatedata;
    private RecyclerView rvUpdatedata;
    private UpdateAdapter adapter;
    int a=0 , b=1, c=2, d=3, e=4, f=5;
    private long backPressedTime;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("update/buathapus");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedata);

        tbUpdatedata = findViewById(R.id.toolbar_Updatedata);
        tbUpdatedata.setTitle("Hapus Data SIM Keliling");
        setSupportActionBar(tbUpdatedata);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        //Query
        Query query = db.collection("simkel0");
        //RecycleviewOptions
        FirestoreRecyclerOptions<ModelUpdate> options = new FirestoreRecyclerOptions.Builder<ModelUpdate>()
                .setQuery(query, ModelUpdate.class)
                .build();
        //adapter
        adapter = new UpdateAdapter(options);
        //recycleview set
        rvUpdatedata = findViewById(R.id.rvUpdatedata);
        rvUpdatedata.setHasFixedSize(true);
        rvUpdatedata.setLayoutManager(new LinearLayoutManager(this));
        rvUpdatedata.setAdapter(adapter);

        /*adapter.setOnItemClickListener(new UpdateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                openDialog();
                ModelUpdate ml = documentSnapshot.toObject(ModelUpdate.class);
                String id = documentSnapshot.getId();
                String nama = documentSnapshot.getString("nama");
                Map<String, Object> idhapus = new HashMap<>();
                idhapus.put("id", id);
                idhapus.put("nama", id);
                db.collection("update").document("buathapus").update(idhapus);
            }
        });*/

        adapter.setOnItemLongClickListener(new UpdateAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(DocumentSnapshot documentSnapshot, int position) {
                openDialog();
                ModelUpdate ml = documentSnapshot.toObject(ModelUpdate.class);
                String id = documentSnapshot.getId();
                String nama = documentSnapshot.getString("nama");
                Map<String, Object> idhapus = new HashMap<>();
                idhapus.put("id", id);
                idhapus.put("nama", id);
                db.collection("update").document("buathapus").update(idhapus);
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

    static String toAlphaLowerCase(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public void openDialog() {
        Dialoghapus dialog = new Dialoghapus();
        dialog.show(getSupportFragmentManager(), "Dialog hapus");
    }

    @Override
    public void onYesClicked() {
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String namaini = documentSnapshot.getString("id");
                            String buatdb = toAlphaLowerCase(namaini);
                            db.collection("simkel0").document(buatdb + a).delete();
                            db.collection("simkel1").document(buatdb + b).delete();
                            db.collection("simkel2").document(buatdb + c).delete();
                            db.collection("simkel3").document(buatdb + d).delete();
                            db.collection("simkel4").document(buatdb + e).delete();
                            db.collection("simkel5").document(buatdb + f).delete();
                            db.collection("update").document("syaratnama").update("name", FieldValue.arrayRemove(buatdb));
                            Toast.makeText(UpdateData.this, "Data berhasil dihapus", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UpdateData.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
