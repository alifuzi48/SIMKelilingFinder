package com.example.demo3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo3.R;

public class InfoSimC extends AppCompatActivity {
    private Button pindahJam;
    private Button pindahBiaya;
    Toolbar tbSIMB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infosimc);
        tbSIMB = findViewById(R.id.toolbar_simC);
        tbSIMB.setTitle("Perpanjang SIM C");
        setSupportActionBar(tbSIMB);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pindahJam = findViewById(R.id.btnCMoveJam);
        pindahBiaya = findViewById(R.id.btnCMoveBiaya);

        pindahJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JamOperasional.class);
                startActivity(intent);
                finish();
            }
        });

        pindahBiaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HitungBiaya.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), DetailInfo.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
