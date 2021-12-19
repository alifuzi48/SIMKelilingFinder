package com.example.demo3.model;

public class Modelhari {
    private String name;
    private String hari1;
    private String hari2;

    public Modelhari(String name, String hari1, String hari2) {
        this.name = name;
        this.hari1 = hari1;
        this.hari2 = hari2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHari1() {
        return hari1;
    }

    public void setHari1(String hari1) {
        this.hari1 = hari1;
    }

    public String getHari2() {
        return hari2;
    }

    public void setHari2(String hari2) {
        this.hari2 = hari2;
    }

    @Override
    public String toString() {
        return name;
    }
}
