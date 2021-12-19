package com.example.demo3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo3.R;
import com.example.demo3.model.ModelJam1;
import com.example.demo3.model.ModelJam2;
import com.example.demo3.model.Modelhari;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditJam extends AppCompatActivity {
    Toolbar tbEditjam;
    private Button addjam;
    private Spinner spHari;
    private Spinner spJam1;
    private Spinner spJam2;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("jam/operasional");
    String name, hari1, hari2, inijam1, inijam2;
    int spinnerPosition1 = 0;
    int spinnerPosition2 = 0;
    int spinnerPosition3 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editjam);

        AndroidThreeTen.init(this);
        tbEditjam = findViewById(R.id.tbEditjam);
        tbEditjam.setTitle("Edit seluruh jam");
        setSupportActionBar(tbEditjam);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spHari = findViewById(R.id.spinnerHari);
        spJam1 = findViewById(R.id.spinnerJam1);
        spJam2 = findViewById(R.id.spinnerJam2);
        addjam = findViewById(R.id.btnAddjam);

        List<Modelhari> hariList = new ArrayList<>();
        Modelhari hari = new Modelhari("Senin-Minggu", "Senin", "Minggu");
        hariList.add(hari);
        Modelhari hari0 = new Modelhari("Senin-Sabtu", "Senin", "Sabtu");
        hariList.add(hari0);
        Modelhari hari1 = new Modelhari("Senin-Jumat", "Senin", "Jumat");
        hariList.add(hari1);
        Modelhari hari2 = new Modelhari("Sabtu-Minggu", "Sabtu", "Minggu");
        hariList.add(hari2);
        Modelhari hari3 = new Modelhari("Senin", "Senin", "nihil");
        hariList.add(hari3);
        Modelhari hari4 = new Modelhari("Selasa", "Selasa", "nihil");
        hariList.add(hari4);
        Modelhari hari5 = new Modelhari("Rabu", "Rabu", "nihil");
        hariList.add(hari5);
        Modelhari hari6 = new Modelhari("Kamis", "Kamis", "nihil");
        hariList.add(hari6);
        Modelhari hari7 = new Modelhari("Jumat", "Jumat", "nihil");
        hariList.add(hari7);
        Modelhari hari8 = new Modelhari("Sabtu", "Sabtu", "nihil");
        hariList.add(hari8);
        Modelhari hari9 = new Modelhari("Minggu", "Minggu", "nihil");
        hariList.add(hari9);

        List<ModelJam1> jamList1 = new ArrayList<>();
        ModelJam1 jam1 = new ModelJam1("07.00", "07.00");
        jamList1.add(jam1);
        ModelJam1 jam2 = new ModelJam1("08.00", "08.00");
        jamList1.add(jam2);
        ModelJam1 jam3 = new ModelJam1("09.00", "09.00");
        jamList1.add(jam3);
        ModelJam1 jam4 = new ModelJam1("10.00", "10.00");
        jamList1.add(jam4);
        ModelJam1 jam5 = new ModelJam1("11.00", "11.00");
        jamList1.add(jam5);
        ModelJam1 jam6 = new ModelJam1("12.00", "12.00");
        jamList1.add(jam6);
        ModelJam1 jam7 = new ModelJam1("13.00", "13.00");
        jamList1.add(jam7);
        ModelJam1 jam8 = new ModelJam1("14.00", "14.00");
        jamList1.add(jam8);
        ModelJam1 jam9 = new ModelJam1("15.00", "15.00");
        jamList1.add(jam9);
        ModelJam1 jam10 = new ModelJam1("16.00", "16.00");
        jamList1.add(jam10);
        ModelJam1 jam11 = new ModelJam1("17.00", "17.00");
        jamList1.add(jam11);

        List<ModelJam2> jamList2 = new ArrayList<>();
        ModelJam2 jjam1 = new ModelJam2("07.00", "07.00");
        jamList2.add(jjam1);
        ModelJam2 jjam2 = new ModelJam2("08.00", "08.00");
        jamList2.add(jjam2);
        ModelJam2 jjam3 = new ModelJam2("09.00", "09.00");
        jamList2.add(jjam3);
        ModelJam2 jjam4 = new ModelJam2("10.00", "10.00");
        jamList2.add(jjam4);
        ModelJam2 jjam5 = new ModelJam2("11.00", "11.00");
        jamList2.add(jjam5);
        ModelJam2 jjam6 = new ModelJam2("12.00", "12.00");
        jamList2.add(jjam6);
        ModelJam2 jjam7 = new ModelJam2("13.00", "13.00");
        jamList2.add(jjam7);
        ModelJam2 jjam8 = new ModelJam2("14.00", "14.00");
        jamList2.add(jjam8);
        ModelJam2 jjam9 = new ModelJam2("15.00", "15.00");
        jamList2.add(jjam9);
        ModelJam2 jjam10 = new ModelJam2("16.00", "16.00");
        jamList2.add(jjam10);
        ModelJam2 jjam11 = new ModelJam2("17.00", "17.00");
        jamList2.add(jjam11);

        ArrayAdapter<Modelhari> adapter = new ArrayAdapter<Modelhari>(this,
                android.R.layout.simple_spinner_item, hariList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<ModelJam1> adapter1 = new ArrayAdapter<ModelJam1>(this,
                android.R.layout.simple_spinner_item, jamList1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<ModelJam2> adapter2 = new ArrayAdapter<ModelJam2>(this,
                android.R.layout.simple_spinner_item, jamList2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spHari.setAdapter(adapter);
        spJam1.setAdapter(adapter1);
        spJam2.setAdapter(adapter2);
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String haribuka = documentSnapshot.getString("haribuka");
                            String haritutup = documentSnapshot.getString("haritutup");

                            if (haribuka.equals("Senin") && haritutup.equals("Minggu")){
                                spinnerPosition1 = 0;
                            } else if (haribuka.equals("Senin") && haritutup.equals("Sabtu")){
                                spinnerPosition1 = 1;
                            } else if (haribuka.equals("Senin") && haritutup.equals("Jumat")){
                                spinnerPosition1 = 2;
                            } else if (haribuka.equals("Sabtu") && haritutup.equals("Minggu")){
                                spinnerPosition1 = 3;
                            } else if (haribuka.equals("Senin") && haritutup.equals("nihil")){
                                spinnerPosition1 = 4;
                            } else if (haribuka.equals("Selasa") && haritutup.equals("nihil")){
                                spinnerPosition1 = 5;
                            } else if (haribuka.equals("Rabu") && haritutup.equals("nihil")){
                                spinnerPosition1 = 6;
                            } else if (haribuka.equals("Kamis") && haritutup.equals("nihil")){
                                spinnerPosition1 = 7;
                            } else if (haribuka.equals("Jumat") && haritutup.equals("nihil")){
                                spinnerPosition1 = 8;
                            } else if (haribuka.equals("Sabtu") && haritutup.equals("nihil")){
                                spinnerPosition1 = 9;
                            } else if (haribuka.equals("Minggu") && haritutup.equals("nihil")){
                                spinnerPosition1 = 10;
                            } else {}
                            spHari.setSelection(spinnerPosition1);
                        }
                    }
                });

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String jambuka = documentSnapshot.getString("jambuka");

                            if (jambuka.equals("07.00") ){
                                spinnerPosition2 = 0;
                            } else if (jambuka.equals("08.00") ){
                                spinnerPosition2 = 1;
                            } else if (jambuka.equals("09.00") ){
                                spinnerPosition2 = 2;
                            } else if (jambuka.equals("10.00")){
                                spinnerPosition2 = 3;
                            } else if (jambuka.equals("11.00")){
                                spinnerPosition2 = 4;
                            } else if (jambuka.equals("12.00") ){
                                spinnerPosition2 = 5;
                            } else if (jambuka.equals("13.00") ){
                                spinnerPosition2 = 6;
                            } else if (jambuka.equals("14.00") ){
                                spinnerPosition2 = 7;
                            } else if (jambuka.equals("15.00")){
                                spinnerPosition2 = 8;
                            } else if (jambuka.equals("16.00")){
                                spinnerPosition2 = 9;
                            }else if (jambuka.equals("17.00")){
                                spinnerPosition2 = 10;
                            } else {}
                            spJam1.setSelection(spinnerPosition2);
                        }
                    }
                });

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String jamtutup = documentSnapshot.getString("jamtutup");

                            if (jamtutup.equals("07.00") ){
                                spinnerPosition3 = 0;
                            } else if (jamtutup.equals("08.00") ){
                                spinnerPosition3 = 1;
                            } else if (jamtutup.equals("09.00") ){
                                spinnerPosition3 = 2;
                            } else if (jamtutup.equals("10.00")){
                                spinnerPosition3 = 3;
                            } else if (jamtutup.equals("11.00")){
                                spinnerPosition3 = 4;
                            } else if (jamtutup.equals("12.00") ){
                                spinnerPosition3 = 5;
                            } else if (jamtutup.equals("13.00") ){
                                spinnerPosition3 = 6;
                            } else if (jamtutup.equals("14.00") ){
                                spinnerPosition3 = 7;
                            } else if (jamtutup.equals("15.00")){
                                spinnerPosition3 = 8;
                            } else if (jamtutup.equals("16.00")){
                                spinnerPosition3 = 9;
                            }else if (jamtutup.equals("17.00")){
                                spinnerPosition3 = 10;
                            } else {}
                            spJam2.setSelection(spinnerPosition3);
                        }
                    }
                });

        spHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Modelhari hari = (Modelhari) parent.getSelectedItem();
                displayhari(hari);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spJam1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelJam1 jjam1 = (ModelJam1) parent.getSelectedItem();
                displayjam1(jjam1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spJam2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ModelJam2 jjam2 = (ModelJam2) parent.getSelectedItem();
                displayjam2(jjam2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HasileditJam();
            }
        });
    }

    private void HasileditJam() {
        if (spJam1.getSelectedItemPosition() >= spJam2.getSelectedItemPosition()){
            Toast.makeText(EditJam.this, "Maaf jam yang anda masukkan tidak benar", Toast.LENGTH_LONG).show();
        } else {
            Map<String, Object> datat = new HashMap<>();
            datat.put("haribuka", hari1);
            datat.put("haritutup", hari2);
            datat.put("jambuka", inijam1);
            datat.put("jamtutup", inijam2);
            noteRef.update(datat);
            Toast.makeText(this, "Data berhasil terupdate", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MenuAdmin.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    private void displayhari(Modelhari user) {
        hari1 = user.getHari1();
        hari2 = user.getHari2();
    }

    private void displayjam1(ModelJam1 user) {
        inijam1 = user.getJam1();
    }

    private void displayjam2(ModelJam2 user) {
        inijam2 = user.getJam2();
    }
}
