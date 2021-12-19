package com.example.demo3.model;

public class ModelJam2 {
    private String name;
    private String jam2;

    public ModelJam2(String name, String jam2) {
        this.name = name;
        this.jam2 = jam2;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJam2() {
        return jam2;
    }

    public void setJam2(String jam2) {
        this.jam2 = jam2;
    }

    @Override
    public String toString() {
        return name;
    }
}
