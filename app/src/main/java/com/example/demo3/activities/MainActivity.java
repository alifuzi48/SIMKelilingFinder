package com.example.demo3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo3.R;
import com.example.demo3.adapter.MainAdapter;
import com.example.demo3.model.ModelMain;
import com.example.demo3.decoration.LayoutMarginDecoration;
import com.example.demo3.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.onSelectData {

    RecyclerView rvMainMenu;
    LayoutMarginDecoration gridMargin;
    ModelMain mdlMainMenu;
    List<ModelMain> lsMainMenu = new ArrayList<>();
    TextView tvWelcome;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 22) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        tvWelcome = findViewById(R.id.tvOpening);
        rvMainMenu = findViewById(R.id.rvMainMenu);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1,
                RecyclerView.VERTICAL, false);
        rvMainMenu.setLayoutManager(mLayoutManager);
        gridMargin = new LayoutMarginDecoration(1, Tools.dp2px(this, 4));
        rvMainMenu.addItemDecoration(gridMargin);
        rvMainMenu.setHasFixedSize(true);

        //get Time Now
        //Date dateNow = Calendar.getInstance().getTime();
        //hariIni = (String) DateFormat.format("EEEE", dateNow);
        //getToday();
        setMenu();
        statusCheck();
    }

    public boolean scrollToTop(View v) {
        Intent myIntent = new Intent(getApplicationContext(), LoginAdmin.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }

    private void setMenu() {
        mdlMainMenu = new ModelMain("Informasi Perpanjang SIM", R.drawable.ic_pengenalan);
        lsMainMenu.add(mdlMainMenu);
        mdlMainMenu = new ModelMain("Lokasi SIM Keliling", R.drawable.ic_location);
        lsMainMenu.add(mdlMainMenu);
        mdlMainMenu = new ModelMain("Pengingat Perpanjang SIM", R.drawable.ic_reminder);
        lsMainMenu.add(mdlMainMenu);
        mdlMainMenu = new ModelMain("Info Aplikasi", R.drawable.ic_info);
        lsMainMenu.add(mdlMainMenu);

        MainAdapter myAdapter = new MainAdapter(lsMainMenu, this);
        rvMainMenu.setAdapter(myAdapter);
    }

    @Override
    public void onSelected(ModelMain mdlMain) {
        switch (mdlMain.getTxtName()) {
            case "Informasi Perpanjang SIM":
                startActivityForResult(new Intent(MainActivity.this, DetailInfo.class), 1);
                finish();
                break;
            case "Lokasi SIM Keliling":
                startActivityForResult(new Intent(MainActivity.this, Lokasi.class), 1);
                finish();
                break;
            case "Pengingat Perpanjang SIM":
                startActivityForResult(new Intent(MainActivity.this, PengingatSimKel.class), 1);
                finish();
                break;
            case "Info Aplikasi":
                startActivityForResult(new Intent(MainActivity.this, InfoApk.class), 1);
                finish();
                break;
        }
    }

    //set Transparent Status bar
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finishAffinity();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}