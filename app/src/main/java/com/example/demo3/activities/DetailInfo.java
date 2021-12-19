package com.example.demo3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo3.R;
import com.example.demo3.adapter.DetailAdapter;
import com.example.demo3.decoration.LayoutMarginDecoration;
import com.example.demo3.model.ModelInfo;
import com.example.demo3.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class DetailInfo extends AppCompatActivity implements DetailAdapter.onSelectData{

    LayoutMarginDecoration gridMargin;
    List<ModelInfo> lsInfoMenu = new ArrayList<>();
    RecyclerView rvDetailInfo;
    Toolbar tbDetailInfo;
    ModelInfo mdlModelInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailinfo);
        tbDetailInfo = findViewById(R.id.toolbar_detailinfo);
        tbDetailInfo.setTitle("Info Perpanjang SIM");
        setSupportActionBar(tbDetailInfo);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvDetailInfo = findViewById(R.id.rvDetailInfo);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1,
                RecyclerView.VERTICAL, false);
        rvDetailInfo.setLayoutManager(mLayoutManager);
        gridMargin = new LayoutMarginDecoration(1, Tools.dp2px(this, 4));
        rvDetailInfo.addItemDecoration(gridMargin);
        rvDetailInfo.setHasFixedSize(true);

        setMenu();
    }

    private void setMenu() {
        mdlModelInfo = new ModelInfo("Informasi Perpanjang SIM A", R.drawable.ic_sim);
        lsInfoMenu.add(mdlModelInfo);
        mdlModelInfo = new ModelInfo("Informasi Perpanjang SIM C", R.drawable.ic_sim);
        lsInfoMenu.add(mdlModelInfo);
        mdlModelInfo = new ModelInfo("Informasi Waktu Operasional", R.drawable.ic_jam);
        lsInfoMenu.add(mdlModelInfo);
        mdlModelInfo = new ModelInfo("Informasi Biaya Perpanjang", R.drawable.ic_rupiah);
        lsInfoMenu.add(mdlModelInfo);

        DetailAdapter myAdapter = new DetailAdapter(lsInfoMenu, this);
        rvDetailInfo.setAdapter(myAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onSelected(ModelInfo mdlInfo) {
        switch (mdlInfo.getTxtName()) {
            case "Informasi Perpanjang SIM A":
                startActivityForResult(new Intent(DetailInfo.this, InfoSimA.class), 1);
                finish();
                break;
            case "Informasi Perpanjang SIM C":
                startActivityForResult(new Intent(DetailInfo.this, InfoSimC.class), 1);
                finish();
                break;
            case "Informasi Waktu Operasional":
                startActivityForResult(new Intent(DetailInfo.this, JamOperasional.class), 1);
                finish();
                break;
            case "Informasi Biaya Perpanjang":
                startActivityForResult(new Intent(DetailInfo.this, HitungBiaya.class), 1);
                finish();
                break;
        }
    }
}
