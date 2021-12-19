package com.example.demo3.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo3.R;
import com.example.demo3.adapter.LokasiAdapter;
import com.example.demo3.model.ModelLokasi;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Lokasi extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, LocationListener {

    private static final String KEY_TITLE = "latPeng";
    private static final String KEY_DESCRIPTION = "longPeng";
    private static final String KEY_JARAK = "jarak";
    private static int randomdah = 0;
    private String namaini;
    private double latini;
    private double longini;
    private static String simkel = "simkel";
    private static final String namaup ="nama";
    LocationManager locationManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView mFirestoreList;
    private LokasiAdapter adapter;
    Toolbar tbSimkel;
    private double latpeng, longpeng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);

        tbSimkel = findViewById(R.id.toolbar_Simkel);
        tbSimkel.setTitle("Daftar Lokasi SIM Keliling");
        setSupportActionBar(tbSimkel);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        openLocation();
        takeLocation();
        randomaje();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        //Query
        Query query = db.collection(simkel + randomdah).orderBy("jarak", Query.Direction.ASCENDING).whereLessThan("jarak", 30);;
        //RecycleviewOptions
        FirestoreRecyclerOptions<ModelLokasi> options = new FirestoreRecyclerOptions.Builder<ModelLokasi>()
                .setQuery(query, ModelLokasi.class)
                .build();
        //adapter
        adapter = new LokasiAdapter(options);
        //recycleview set
        mFirestoreList = findViewById(R.id.rvSimkel);
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

        adapter.setOnItemClickListener(new LokasiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
            }
        });
    }

    private void randomaje() {
        Random rand = new Random();
        int x = rand.nextInt(6);
        randomdah = x;
    }

    private double distance(double latitude1, double longitude1, double latitude2, double longitude2){

        double radius = 6371; // Earth's radius in kilometers
        double latDelta = degreesToRadians(latitude2 - latitude1);
        double longDelta = degreesToRadians(longitude2 - longitude1);

        double a = (Math.sin(latDelta / 2) * Math.sin(latDelta / 2)) +
                (Math.cos(degreesToRadians(latitude1)) * Math.cos(degreesToRadians(latitude2)) *
                        Math.sin(longDelta / 2) * Math.sin(longDelta / 2));

        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double c = radius * b;
        double roundOff = Math.round(c * 100.0) / 100.0;
        return roundOff;
    }

    private double degreesToRadians(double degrees) {
        return (degrees * Math.PI)/180;
    }

    @AfterPermissionGranted(123)
    private void openLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Lokasi Terakses", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "We need permissions because this and that",
                    123, perms);
        }
    }

    @SuppressLint("MissingPermission")
    private void takeLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,Lokasi.this);

        }catch (Exception e){
            e.printStackTrace();
        }

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
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latcakung = -6.18674, longcakung = 106.95864, latciledug = -6.23618, longciledug = 106.75727,
                latkalibata = -6.25400, longkalibata = 106.84849, latglodok = -6.14623, longglodok = 106.81667,
                latlpbanteng = -6.16840, longlpbanteng = 106.8347;

        latpeng = location.getLatitude();
        longpeng = location.getLongitude();

        double jarakcakung = distance(latpeng,longpeng,latcakung,longcakung);
        double jarakciledug = distance(latpeng,longpeng,latciledug,longciledug);
        double jarakkalibata = distance(latpeng,longpeng,latkalibata,longkalibata);
        double jarakglodok = distance(latpeng,longpeng,latglodok,longglodok);
        double jaraklpbanteng = distance(latpeng,longpeng,latlpbanteng,longlpbanteng);

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, latpeng);
        note.put(KEY_DESCRIPTION, longpeng);
        Map<String, Object> jcakung = new HashMap<>();
        jcakung.put(KEY_JARAK, jarakcakung);
        Map<String, Object> jciledug = new HashMap<>();
        jciledug.put(KEY_JARAK, jarakciledug);
        Map<String, Object> jkalibata = new HashMap<>();
        jkalibata.put(KEY_JARAK, jarakkalibata);
        Map<String, Object> jglodok = new HashMap<>();
        jglodok.put(KEY_JARAK, jarakglodok);
        Map<String, Object> jlpbanteng = new HashMap<>();
        jlpbanteng.put(KEY_JARAK, jaraklpbanteng);

        db.collection(simkel + randomdah).document("grandcakung" + randomdah).update(note);
        db.collection(simkel + randomdah).document("petukanganciledug" + randomdah).update(note);
        db.collection(simkel + randomdah).document("kampustrilogikalibata" + randomdah).update(note);
        db.collection(simkel + randomdah).document("lctglodok" + randomdah).update(note);
        db.collection(simkel + randomdah).document("kantorposlapanganbanteng" + randomdah).update(note);
        db.collection(simkel + randomdah).document("grandcakung" + randomdah).update(jcakung);
        db.collection(simkel + randomdah).document("petukanganciledug" + randomdah).update(jciledug);
        db.collection(simkel + randomdah).document("kampustrilogikalibata" + randomdah).update(jkalibata);
        db.collection(simkel + randomdah).document("lctglodok" + randomdah).update(jglodok);
        db.collection(simkel + randomdah).document("kantorposlapanganbanteng" + randomdah).update(jlpbanteng);

        for (int x=1; x<=10; x++) {
            FirebaseFirestore.getInstance().collection("update").document("dokumen"+x).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                namaini = documentSnapshot.getString(namaup);
                                latini = documentSnapshot.getDouble("latitude");
                                longini = documentSnapshot.getDouble("longitude");
                                double jarakbaru = distance(latpeng, longpeng, latini, longini);
                                Map<String, Object> jbaru = new HashMap<>();
                                jbaru.put(KEY_JARAK, jarakbaru);
                                db.collection(simkel + randomdah).document(namaini + randomdah).update(note);
                                db.collection(simkel + randomdah).document(namaini + randomdah).update(jbaru);
                            }
                        }
                    });
        }

        for (int y=1; y<=10; y++) {
            FirebaseFirestore.getInstance().collection("update").document("editan"+y).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                namaini = documentSnapshot.getString(namaup);
                                latini = documentSnapshot.getDouble("latitude");
                                longini = documentSnapshot.getDouble("longitude");
                                double jarakbaru = distance(latpeng,longpeng,latini,longini);
                                Map<String, Object> jbaru = new HashMap<>();
                                jbaru.put(KEY_JARAK, jarakbaru);
                                db.collection(simkel + randomdah).document(namaini + randomdah).update(note);
                                db.collection(simkel + randomdah).document(namaini + randomdah).update(jbaru);
                            }
                        }
                    });
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
