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
import com.example.demo3.adapter.AdminAdapter;

import com.example.demo3.decoration.LayoutMarginDecoration;
import com.example.demo3.model.ModelAdmin;
import com.example.demo3.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class MenuAdmin extends AppCompatActivity implements AdminAdapter.onSelectData{

    LayoutMarginDecoration gridMargin;
    List<ModelAdmin> lsMenuadmin = new ArrayList<>();
    RecyclerView rvMenuadmin;
    Toolbar tbMenuadmin ;
    ModelAdmin mdlMenuadmin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuadmin);
        tbMenuadmin = findViewById(R.id.toolbar_Menuadmin);
        tbMenuadmin.setTitle("Menu Admin");
        setSupportActionBar(tbMenuadmin);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvMenuadmin = findViewById(R.id.rvMenuadmin);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1,
                RecyclerView.VERTICAL, false);
        rvMenuadmin.setLayoutManager(mLayoutManager);
        gridMargin = new LayoutMarginDecoration(1, Tools.dp2px(this, 4));
        rvMenuadmin.addItemDecoration(gridMargin);
        rvMenuadmin.setHasFixedSize(true);

        setMenu();
    }

    private void setMenu() {
        mdlMenuadmin = new ModelAdmin("Tambah Lokasi SIM Keliling", R.drawable.ic_update);
        lsMenuadmin.add(mdlMenuadmin);
        mdlMenuadmin = new ModelAdmin("Edit Lokasi SIM Keliling", R.drawable.ic_update);
        lsMenuadmin.add(mdlMenuadmin);
        mdlMenuadmin = new ModelAdmin("Hapus Lokasi SIM Keliling", R.drawable.ic_update);
        lsMenuadmin.add(mdlMenuadmin);
        mdlMenuadmin = new ModelAdmin("Edit Harga SIM Keliling", R.drawable.ic_update);
        lsMenuadmin.add(mdlMenuadmin);
        mdlMenuadmin = new ModelAdmin("Edit Seluruh Jam SIM Keliling", R.drawable.ic_update);
        lsMenuadmin.add(mdlMenuadmin);

        AdminAdapter myAdapter = new AdminAdapter(lsMenuadmin, this);
        rvMenuadmin.setAdapter(myAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    public void onSelected(ModelAdmin mdlMenuadmin) {
        switch (mdlMenuadmin.getTxtName()) {
            case "Tambah Lokasi SIM Keliling":
                startActivityForResult(new Intent(MenuAdmin.this, AddData.class), 1);
                finish();
                break;
            case "Edit Lokasi SIM Keliling":
                startActivityForResult(new Intent(MenuAdmin.this, Editdata.class), 1);
                finish();
                break;
            case "Hapus Lokasi SIM Keliling":
                startActivityForResult(new Intent(MenuAdmin.this, UpdateData.class), 1);
                finish();
                break;
            case "Edit Harga SIM Keliling":
                startActivityForResult(new Intent(MenuAdmin.this, EditHarga.class), 1);
                finish();
                break;
            case "Edit Seluruh Jam SIM Keliling":
                startActivityForResult(new Intent(MenuAdmin.this, EditJam.class), 1);
                finish();
                break;
        }
    }
}
