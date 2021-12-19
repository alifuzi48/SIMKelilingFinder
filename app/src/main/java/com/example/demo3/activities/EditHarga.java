package com.example.demo3.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.demo3.R;
import com.example.demo3.model.ModelBiaya2;
import com.example.demo3.model.ModelJam1;
import com.example.demo3.model.ModelJam2;
import com.example.demo3.model.Modelhari;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditHarga extends AppCompatActivity {
    Toolbar tbEditharga;
    private EditText editsima;
    private EditText editsimc;
    private EditText editasuransi;
    private EditText editcek;
    private Button addharga;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef1 = db.document("harga/sima");
    private DocumentReference noteRef2 = db.document("harga/simc");
    private DocumentReference noteRef3 = db.document("harga/simac");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editharga);

        tbEditharga = findViewById(R.id.tbAddharga);
        tbEditharga.setTitle("Edit Harga SIM");
        setSupportActionBar(tbEditharga);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editsima = findViewById(R.id.etAddsima);
        editsimc = findViewById(R.id.etAddsimc);
        editasuransi = findViewById(R.id.etAddasuransi);
        editcek = findViewById(R.id.etAddcek);
        addharga = findViewById(R.id.btnAddharga);

        noteRef1.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelBiaya2 mb = documentSnapshot.toObject(ModelBiaya2.class);
                            int sima = mb.getBiayasim();
                            int asuransi = mb.getBiayaasuransi();
                            int cek = mb.getBiayacek();
                            editsima.setText(sima + "");
                            editasuransi.setText(asuransi + "");
                            editcek.setText(cek + "");
                        }
                    }
                });

        noteRef2.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ModelBiaya2 mb = documentSnapshot.toObject(ModelBiaya2.class);
                            int simc= mb.getBiayasim();
                            editsimc.setText(simc + "");
                        }
                    }
                });

        addharga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HasileditHarga();
            }
        });
    }

    private void HasileditHarga() {
        String hasilsima = editsima.getText().toString();
        String hasilsimc= editsimc.getText().toString();
        String hasilasuransi = editasuransi.getText().toString();
        String hasilcek = editcek.getText().toString();
        int hasilsima2 = ParseInteger(hasilsima);
        int hasilsimc2 = ParseInteger(hasilsimc);
        int hasilasuransi2 = ParseInteger(hasilasuransi);
        int hasilcek2 = ParseInteger(hasilcek);
        int hasilsimac = hasilsima2 + hasilsimc2;
        int hasilasuransiac = hasilasuransi2*2;
        int hasilcekac = hasilcek2;
        int seluruha = hasilsima2 + hasilasuransi2 + hasilcek2;
        int seluruhc = hasilsimc2 + hasilasuransi2 + hasilcek2;
        int seluruhaac = hasilsimac + hasilasuransiac + hasilcekac;
        if(hasilsima2 == 0 || hasilsimc2 == 0 || hasilasuransi2 == 0 || hasilcek2 == 0)
        {
            /* Display a message toast to user to enter the details */
            Toast.makeText(EditHarga.this, "Anda belum memasukkan dengan lengkap", Toast.LENGTH_LONG).show();
        }else {
            Map<String, Object> dataA = new HashMap<>();
            dataA.put("biayasim", hasilsima2);
            dataA.put("biayaasuransi", hasilasuransi2);
            dataA.put("biayacek", hasilcek2);
            dataA.put("total", seluruha);
            Map<String, Object> dataC = new HashMap<>();
            dataC.put("biayasim", hasilsimc2);
            dataC.put("biayaasuransi", hasilasuransi2);
            dataC.put("biayacek", hasilcek2);
            dataC.put("total", seluruhc);
            Map<String, Object> dataAC = new HashMap<>();
            dataAC.put("biayasim", hasilsimac);
            dataAC.put("biayaasuransi", hasilasuransiac);
            dataAC.put("biayacek", hasilcekac);
            dataAC.put("total", seluruhaac);
            noteRef1.update(dataA);
            noteRef2.update(dataC);
            noteRef3.update(dataAC);
            Toast.makeText(EditHarga.this, "Data berhasil dimasukkan", Toast.LENGTH_LONG).show();
        }
    }

    int ParseInteger(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MenuAdmin.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
