package com.example.acer.quanlychitieu.Values;

import java.io.Serializable;

/**
 * Created by Acer on 22/05/2017.
 */

public class ThuChiValues implements Serializable {
    private int id;
    String category, money, date, note, thuchi;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getcategory() {
        return category;
    }

    public String getMoney() {
        return money;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public String getThuchi() {
        return thuchi;
    }

    public ThuChiValues(String category, String thuchi, String money, String date, String note) {
        this.category = category;
        this.thuchi = thuchi;
        this.money = money;
        this.date = date;
        this.note = note;
    }

    public ThuChiValues(String category, String money) {
        this.category = category;
        this.money = money;
    }
}
