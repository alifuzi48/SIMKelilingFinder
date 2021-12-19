package com.example.demo3.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.demo3.R;
import com.example.demo3.model.ModelBiaya;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HitungBiaya extends AppCompatActivity {
    private static final String TAG = "HitungBiaya";
    private Spinner spinner;
    private Button pilih;
    private TextView biayaSIM;
    private TextView biayaAsuransi;
    private TextView biayaTes;
    private TextView total;
    private CardView hasilPilih;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef1 = db.document("harga/sima");
    private DocumentReference noteRef2 = db.document("harga/simc");
    private DocumentReference noteRef3 = db.document("harga/simac");
    String[] mOptions = {"SIM A", "SIM C", "SIM A & C"};
    Toolbar tbHitungbiaya;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitungbiaya);
        tbHitungbiaya = findViewById(R.id.tbHitungbiaya);
        tbHitungbiaya.setTitle("Hitung Biaya SIM");
        setSupportActionBar(tbHitungbiaya);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.spinnerBiaya);
        pilih = findViewById(R.id.btnHitungbiaya);
        biayaSIM = findViewById(R.id.tvHasiSIM2);
        biayaAsuransi = findViewById(R.id.tvHasiAsuransi2);
        biayaTes = findViewById(R.id.tvHasiTes2);
        hasilPilih = findViewById(R.id.cvHasilpilih);
        total = findViewById(R.id.tvTotal2);

        //Creating the ArrayAdapter instance having the list of options
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mOptions);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    hasilPilih.setVisibility(View.INVISIBLE);
                    noteRef1.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        ModelBiaya mb = documentSnapshot.toObject(ModelBiaya.class);
                                        String nama = mb.getName();
                                        int sim = mb.getBiayasim();
                                        int asuransi = mb.getBiayaasuransi();
                                        int cek = mb.getBiayacek();
                                        int Total = mb.getTotal();
                                        biayaSIM.setText("Rp. " + sim);
                                        biayaAsuransi.setText("Rp. " + asuransi);
                                        biayaTes.setText("Rp. " + cek);
                                        total.setText("Rp. " + Total);
                                    }
                                }
                            });
                }
                if (position==1){
                    hasilPilih.setVisibility(View.INVISIBLE);
                    noteRef2.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        ModelBiaya mb = documentSnapshot.toObject(ModelBiaya.class);
                                        String nama = mb.getName();
                                        int sim = mb.getBiayasim();
                                        int asuransi = mb.getBiayaasuransi();
                                        int cek = mb.getBiayacek();
                                        int Total = mb.getTotal();
                                        biayaSIM.setText("Rp. " + sim);
                                        biayaAsuransi.setText("Rp. " + asuransi);
                                        biayaTes.setText("Rp. " + cek);
                                        total.setText("Rp. " + Total);
                                    }
                                }
                            });
                }
                if (position==2){
                    hasilPilih.setVisibility(View.INVISIBLE);
                    noteRef3.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        ModelBiaya mb = documentSnapshot.toObject(ModelBiaya.class);
                                        String nama = mb.getName();
                                        int sim = mb.getBiayasim();
                                        int asuransi = mb.getBiayaasuransi();
                                        int cek = mb.getBiayacek();
                                        int Total = mb.getTotal();
                                        biayaSIM.setText("Rp. " + sim);
                                        biayaAsuransi.setText("Rp. " + asuransi);
                                        biayaTes.setText("Rp. " + cek);
                                        total.setText("Rp. " + Total);
                                    }
                                }
                            });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), DetailInfo.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void getSelected(View v) {
        hasilPilih.setVisibility(View.VISIBLE);
    }
}
