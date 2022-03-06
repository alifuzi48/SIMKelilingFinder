package com.example.demo3.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.demo3.R;
import com.example.demo3.directionhelpers.FetchURL;
import com.example.demo3.directionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.Calendar;

public class DetailLokasi extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    Toolbar tbDetaillokasi;
    GoogleMap googleMaps;
    TextView namadetail, jamOpdetail, hariOpdetail, statusdetail;
    ImageView direct;
    String intent_nama, intent_jambuka, intent_jamtutup, intent_haribuka, intent_haritutup, intent_longitude,
            intent_latitude, intent_latpeng, intent_longpeng, namapeng = "Lokasi anda", jamop, hariop;
    double latitude, longitude, latpeng, longpeng;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lokasi);

        AndroidThreeTen.init(this);
        tbDetaillokasi = findViewById(R.id.tbDetaillokasi);
        tbDetaillokasi.setTitle("Detail Lokasi");
        setSupportActionBar(tbDetaillokasi);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //show maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        intent_nama = intent.getStringExtra("nama");
        intent_jambuka = intent.getStringExtra("jambuka");
        intent_jamtutup = intent.getStringExtra("jamtutup");
        intent_haribuka = intent.getStringExtra("haribuka");
        intent_haritutup = intent.getStringExtra("haritutup");
        intent_longitude = intent.getStringExtra("longitude");
        intent_latitude = intent.getStringExtra("latitude");
        intent_latpeng = intent.getStringExtra("latPeng");
        intent_longpeng = intent.getStringExtra("longPeng");

        namadetail = findViewById(R.id.tvDetailnamalokasi);
        jamOpdetail = findViewById(R.id.tvDetailjamOp);
        hariOpdetail = findViewById(R.id.tvDetailHariOp);
        statusdetail = findViewById(R.id.tvDetailstatus);
        direct = findViewById(R.id.directMaps);

        // zone time
        ZoneId zone = ZoneId.of("UTC+7");

        // date today
        LocalDate today = LocalDate.now(zone);
        Calendar cal = Calendar.getInstance();
        int doWeek = cal.get(Calendar.DAY_OF_WEEK);

        String[] parts2 = intent_jambuka.split("[.]");
        String part3 = parts2[0];
        String part4 = parts2[1];
        String[] parts3 = intent_jamtutup.split("[.]");
        String part5 = parts3[0];
        String part6 = parts3[1];

        int a = Integer.parseInt(part3);
        int b = Integer.parseInt(part4);
        int c = Integer.parseInt(part5);
        int d = Integer.parseInt(part6);

        // times
        LocalTime jambk = LocalTime.of(a, b); // or LocalTime.parse("08.00") jam mulai
        LocalTime jamttp = LocalTime.of(c, d); // or LocalTime.parse("14.00") jam tutup

        //waktu sekarang
        ZonedDateTime simstart = today.atTime(jambk).atZone(zone);
        ZonedDateTime simend = today.atTime(jamttp).atZone(zone);

        //waktu sekarang
        ZonedDateTime zdt = ZonedDateTime.now(zone);

        //check if it's open
        if (intent_haribuka.equals("Senin") && intent_haritutup.equals("Minggu")) {
            if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            } else {
                statusdetail.setText("Open");
                statusdetail.setTextColor(getResources().getColor(R.color.hijau));
            }
        } else if (intent_haribuka.equals("Senin") && intent_haritutup.equals("Sabtu")){
            if( doWeek >= Calendar.MONDAY && doWeek <= Calendar.SATURDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Senin") && intent_haritutup.equals("Jumat")){
            if( doWeek >= Calendar.MONDAY && doWeek <= Calendar.FRIDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Sabtu") && intent_haritutup.equals("Minggu")){
            if( doWeek == Calendar.SUNDAY || doWeek == Calendar.SATURDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Senin") && intent_haritutup.equals("nihil")){
            if( doWeek == Calendar.MONDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Selasa") && intent_haritutup.equals("nihil")){
            if( doWeek == Calendar.TUESDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Rabu") && intent_haritutup.equals("nihil")){
            if( doWeek == Calendar.WEDNESDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Kamis") && intent_haritutup.equals("nihil")){
            if( doWeek == Calendar.THURSDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Jumat") && intent_haritutup.equals("nihil")){
            if( doWeek == Calendar.FRIDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Sabtu") && intent_haritutup.equals("nihil")){
            if( doWeek == Calendar.SATURDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else if (intent_haribuka.equals("Minggu") && intent_haritutup.equals("nihil")){
            if( doWeek == Calendar.SUNDAY ) {
                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                    statusdetail.setText("Close");
                    statusdetail.setTextColor(getResources().getColor(R.color.merah));
                } else {
                    statusdetail.setText("Open");
                    statusdetail.setTextColor(getResources().getColor(R.color.hijau));
                }
            } else {
                statusdetail.setText("Close");
                statusdetail.setTextColor(getResources().getColor(R.color.merah));
            }
        } else {
            statusdetail.setText("Eror");
        }

        if (intent_haritutup.equals("nihil")){
            jamop = intent_jambuka + " - " + intent_jamtutup;
            hariop = intent_haribuka;
        } else {
            jamop = intent_jambuka + " - " + intent_jamtutup;
            hariop = intent_haribuka + " - " + intent_haritutup;
        }

        namadetail.setText(intent_nama);
        jamOpdetail.setText(jamop);
        hariOpdetail.setText(hariop);

        direct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+intent_latitude+","+intent_longitude);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), Lokasi.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        latitude = Double.parseDouble(intent_latitude);
        longitude = Double.parseDouble(intent_longitude);
        latpeng = Double.parseDouble(intent_latpeng);
        longpeng = Double.parseDouble(intent_longpeng);

        googleMaps = googleMap;
        LatLng latLng = new LatLng(latitude, longitude);
        LatLng pengguna = new LatLng(latpeng, longpeng);
        place1 = new MarkerOptions().position(latLng);
        place2 = new MarkerOptions().position(pengguna);

        new FetchURL(DetailLokasi.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

        googleMaps.addMarker(new MarkerOptions().position(latLng).title(intent_nama));
        googleMaps.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        googleMaps.getUiSettings().setAllGesturesEnabled(true);
        googleMaps.getUiSettings().setZoomGesturesEnabled(true);
        googleMaps.setTrafficEnabled(true);

        googleMaps.addMarker(new MarkerOptions().position(pengguna).title(namapeng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        googleMaps.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLng(pengguna));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(pengguna, 11));
        googleMaps.getUiSettings().setAllGesturesEnabled(true);
        googleMaps.getUiSettings().setZoomGesturesEnabled(true);
        googleMaps.setTrafficEnabled(true);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.Api_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = googleMaps.addPolyline((PolylineOptions) values[0]);
    }
}
