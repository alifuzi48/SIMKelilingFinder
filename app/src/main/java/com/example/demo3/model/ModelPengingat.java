package com.example.demo3.model;

public class ModelPengingat {
    int id;
    private String title;
    private String date;
    private String time;

    public ModelPengingat(int id, String title, String date, String time) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
