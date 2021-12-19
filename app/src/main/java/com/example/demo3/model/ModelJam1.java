package com.example.demo3.model;

public class ModelJam1 {
    private String name;
    private String jam1;

    public ModelJam1(String name, String jam1) {
        this.name = name;
        this.jam1 = jam1;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJam1() {
        return jam1;
    }

    public void setJam1(String jam1) {
        this.jam1 = jam1;
    }

    @Override
    public String toString() {
        return name;
    }
}
