package com.example.demo3.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JamOperasional extends AppCompatActivity {

    Toolbar tbJamOperasional;
    private TextView status;
    private TextView jambaru;
    private TextView jamnya;
    private TextView harinya;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("jam/operasional");
    String hari, jam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad  = "id"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_jamoperasional);
        AndroidThreeTen.init(this);
        tbJamOperasional = findViewById(R.id.toolbar_waktuoper);
        tbJamOperasional.setTitle("Jam Operasional");
        setSupportActionBar(tbJamOperasional);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        status = findViewById(R.id.waktuoper99);
        jambaru = findViewById(R.id.tvHariTanggal);
        jamnya = findViewById(R.id.tvjamnya);
        harinya = findViewById(R.id.tvharinya);

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String haribuka = documentSnapshot.getString("haribuka");
                            String haritutup = documentSnapshot.getString("haritutup");
                            String jambuka = documentSnapshot.getString("jambuka");
                            String jamtutup = documentSnapshot.getString("jamtutup");

                            if (haritutup.equals("nihil")){
                                hari = "> " + haribuka;
                            } else {
                                hari = "> " + haribuka + " - " + haritutup;
                            }
                            jam = "> " + jambuka + " - " + jamtutup;
                            jamnya.setText(jam);
                            harinya.setText(hari);
                        }
                    }
                });

        getDay();
        settime();
    }

    private void settime() {
        // zone time
        ZoneId zone = ZoneId.of("UTC+7");

        // date today
        LocalDate today = LocalDate.now(zone);
        Calendar cal = Calendar.getInstance();
        int doWeek = cal.get(Calendar.DAY_OF_WEEK);

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String haribuka = documentSnapshot.getString("haribuka");
                            String haritutup = documentSnapshot.getString("haritutup");
                            String jambuka = documentSnapshot.getString("jambuka");
                            String jamtutup = documentSnapshot.getString("jamtutup");

                            String[] parts2 = jambuka.split("[.]");
                            String part3 = parts2[0];
                            String part4 = parts2[1];
                            String[] parts3 = jamtutup.split("[.]");
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
                            if (haribuka.equals("Senin") && haritutup.equals("Minggu")){
                                if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                    status.setText("Close");
                                } else {
                                    status.setText("Open");
                                }
                            } else if (haribuka.equals("Senin") && haritutup.equals("Sabtu")){
                                if( doWeek >= Calendar.MONDAY && doWeek <= Calendar.SATURDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Senin") && haritutup.equals("Jumat")){
                                if( doWeek >= Calendar.MONDAY && doWeek <= Calendar.FRIDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Sabtu") && haritutup.equals("Minggu")){
                                if( doWeek == Calendar.SUNDAY || doWeek == Calendar.SATURDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Senin") && haritutup.equals("nihil")){
                                if( doWeek == Calendar.MONDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Selasa") && haritutup.equals("nihil")){
                                if( doWeek == Calendar.TUESDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Rabu") && haritutup.equals("nihil")){
                                if( doWeek == Calendar.WEDNESDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Kamis") && haritutup.equals("nihil")){
                                if( doWeek == Calendar.THURSDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Jumat") && haritutup.equals("nihil")){
                                if( doWeek == Calendar.FRIDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Sabtu") && haritutup.equals("nihil")){
                                if( doWeek == Calendar.SATURDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else if (haribuka.equals("Minggu") && haritutup.equals("nihil")){
                                if( doWeek == Calendar.SUNDAY ) {
                                    if (simstart.isAfter(zdt) || simend.isBefore(zdt)) {
                                        status.setText("Close");
                                    } else {
                                        status.setText("Open");
                                    }
                                } else {
                                    status.setText("Close");
                                }
                            } else {
                                status.setText("Eror");
                            }
                        }
                    }
                });
    }

    private void getDay() {
        Date dateNow = Calendar.getInstance().getTime();
        String hariIni = (String) DateFormat.format("EEEE", dateNow);
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d MMMM yyyy", date);
        String formatFix = hariIni + ", " + tanggal;
        jambaru.setText(formatFix);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), DetailInfo.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
