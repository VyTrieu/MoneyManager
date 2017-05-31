package com.example.acer.quanlychitieu.Values;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Acer on 22/05/2017.
 */

public class KeHoachValues implements Serializable {
    private int id;
    String ten, muctieu, ngaybatdau, ngayketthuc, sotien;

    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getMuctieu() {
        return muctieu;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public String getNgayketthuc() {
        return ngayketthuc;
    }

    public String getSotien() {
        return sotien;
    }

    public KeHoachValues(int id, String ten, String muctieu, String ngayketthuc, String sotien) {
        this.id = id;
        this.ten = ten;
        this.muctieu = muctieu;
        this.ngayketthuc = ngayketthuc;
        this.sotien = sotien;
    }

    public KeHoachValues(String ten, String muctieu, String ngaybatdau, String ngayketthuc, String sotien) {
        this.ten = ten;
        this.muctieu = muctieu;
        this.ngayketthuc = ngayketthuc;
        this.sotien = sotien;
        this.ngaybatdau = ngaybatdau;
    }

    public String autoupdate_date() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();

        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
        String strDate = dft.format(cal3.getTime());
        if(strDate.equals(getNgaybatdau())) {
            Date date1 = stringtodate(getNgaybatdau()); cal1.setTime(date1);
        }
        else {
            Date date1 = stringtodate(strDate); cal1.setTime(date1);
        }
        Date date2 = stringtodate(getNgayketthuc()); cal2.setTime(date2);

        long songay = cal2.getTime().getTime() - cal1.getTime().getTime();
        return String.valueOf(Math.round(songay/1000/60/60/24));
    }
    private Date stringtodate(String s) {
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
        Date date = null;
        try {
            date = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
