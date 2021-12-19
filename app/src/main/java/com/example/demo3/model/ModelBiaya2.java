package com.example.demo3.model;

public class ModelBiaya2 {
    private String name;
    private int biayasim;
    private int biayaasuransi;
    private int biayacek;
    private int total;

    public ModelBiaya2(){

    }

    public ModelBiaya2(String name, int biayasim, int biayaasuransi, int biayacek, int total) {
        this.name = name;
        this.biayasim = biayasim;
        this.biayaasuransi = biayaasuransi;
        this.biayacek = biayacek;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public int getBiayasim() {
        return biayasim;
    }

    public int getBiayaasuransi() {
        return biayaasuransi;
    }

    public int getBiayacek() {
        return biayacek;
    }

    public int getTotal() {
        return total;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiayasim(int biayasim) {
        this.biayasim = biayasim;
    }

    public void setBiayaasuransi(int biayaasuransi) {
        this.biayaasuransi = biayaasuransi;
    }

    public void setBiayacek(int biayacek) {
        this.biayacek = biayacek;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}