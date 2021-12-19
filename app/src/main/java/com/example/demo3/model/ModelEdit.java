package com.example.demo3.model;

import java.io.Serializable;

public class ModelEdit implements Serializable {
    private String nama, jambuka, jamtutup, haribuka, haritutup, imgurl;
    private double latitude, longitude, latPeng, longPeng, jarak;

    private ModelEdit(){
        //empty constructor needed
    }

    private ModelEdit(String nama, String jambuka, String jamtutup, String haribuka, String haritutup, String  imgurl,
                        double latitude, double longitude, double latPeng, double longPeng, double jarak){
        this.nama = nama;
        this.jambuka = jambuka;
        this.jamtutup = jamtutup;
        this.haribuka = haribuka;
        this.haritutup = haritutup;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgurl = imgurl;
        this.latPeng = latPeng;
        this.longPeng = longPeng;
        this.jarak = jarak;
    }

    public String getNama() {
        return nama;
    }

    public String getJambuka() {
        return jambuka;
    }

    public String getJamtutup() {
        return jamtutup;
    }

    public String getHaribuka() {
        return haribuka;
    }

    public String getHaritutup() {
        return haritutup;
    }

    public String getImgurl() {
        return imgurl;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatPeng(){
        return  latPeng;
    }

    public double getLongPeng(){
        return longPeng;
    }

    public double getJarak(){return jarak;}
}
