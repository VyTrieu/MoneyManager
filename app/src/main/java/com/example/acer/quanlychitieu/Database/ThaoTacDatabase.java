package com.example.acer.quanlychitieu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.acer.quanlychitieu.Values.KeHoachValues;
import com.example.acer.quanlychitieu.Values.ThuChiValues;

import java.util.ArrayList;

/**
 * Created by Acer on 22/05/2017.
 */

public class ThaoTacDatabase {
    Context context;
    QuanLyDatabase myData;
    SQLiteDatabase db;
    private static String TAG = "ThaoTacDatabase";

    public ThaoTacDatabase(Context context) {
        this.context = context;
        myData = new QuanLyDatabase(context);
    }
    //ThuChi_Table
    public void thuchi_add(ThuChiValues dept){
        db = myData.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuanLyDatabase.THUCHI_CATEGORY,dept.getcategory());
        values.put(QuanLyDatabase.THUCHI_DETAILS, dept.getThuchi());
        values.put(QuanLyDatabase.THUCHI_MONEY, dept.getMoney());
        values.put(QuanLyDatabase.THUCHI_DATE, dept.getDate());
        values.put(QuanLyDatabase.THUCHI_NOTE, dept.getNote());

        db.insert(QuanLyDatabase.TABLE_THUCHI,null,values);
    }
    public boolean thuchi_update(ThuChiValues dept) {
        db = myData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuanLyDatabase.THUCHI_CATEGORY,dept.getcategory());
        values.put(QuanLyDatabase.THUCHI_DETAILS, dept.getThuchi());
        values.put(QuanLyDatabase.THUCHI_MONEY, dept.getMoney());
        values.put(QuanLyDatabase.THUCHI_DATE, dept.getDate());
        values.put(QuanLyDatabase.THUCHI_NOTE, dept.getNote());
        String id = "'" + dept.getId() + "'";
        int result  = db.update(QuanLyDatabase.TABLE_THUCHI,values, QuanLyDatabase.THUCHI_ID + " = " + id, null);
        if(result < 0) return false;
        return true;
    }
    public boolean thuchi_delete (ThuChiValues dept) {
        db = myData.getWritableDatabase();
        String id = "'" + dept.getId() + "'";
        int result = db.delete(QuanLyDatabase.TABLE_THUCHI, QuanLyDatabase.THUCHI_ID + " = " + id, null);
        if(result < 0) return false;
        return true;
    }
    public ArrayList<ThuChiValues> thuchi_list(){
        ArrayList<ThuChiValues> de = new ArrayList<ThuChiValues>();
        db = myData.getReadableDatabase();

        String sql = "SELECT * FROM " + QuanLyDatabase.TABLE_THUCHI;
        Cursor cur = db.rawQuery(sql, null);
        cur.moveToPosition(-1);
        while(cur.moveToNext()){
            ThuChiValues dept = new ThuChiValues(cur.getString(cur.getColumnIndex("chitieu")),
                    cur.getString(cur.getColumnIndex("loaichitieu")),
                    cur.getString(cur.getColumnIndex("sotien")),
                    cur.getString(cur.getColumnIndex("ngay")),
                    cur.getString(cur.getColumnIndex("ghichu")));
            de.add(dept);
        }
        return de;
    }

    //KeHoach_Table
    public void kehoach_add(KeHoachValues plan_values) {
        db = myData.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuanLyDatabase.KEHOACH_NAME, plan_values.getTen());
        values.put(QuanLyDatabase.KEHOACH_TARGET, plan_values.getMuctieu());
        values.put(QuanLyDatabase.KEHOACH_NGAYBATDAU, plan_values.getNgaybatdau());
        values.put(QuanLyDatabase.KEHOACH_NGAYKETTHUC, plan_values.getNgayketthuc());
        values.put(QuanLyDatabase.KEHOACH_SOTIEN, plan_values.getSotien());
        db.insert(QuanLyDatabase.TABLE_KEHOACH, null, values);
    }
    public boolean kehoach_update(KeHoachValues plan_values) {
        db = myData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuanLyDatabase.KEHOACH_NAME, plan_values.getTen());
        values.put(QuanLyDatabase.KEHOACH_TARGET, plan_values.getMuctieu());
        values.put(QuanLyDatabase.KEHOACH_NGAYKETTHUC, plan_values.getNgayketthuc());
        values.put(QuanLyDatabase.KEHOACH_SOTIEN, plan_values.getSotien());
        String id = "'" + plan_values.getId() + "'";
        int result = db.update(QuanLyDatabase.TABLE_KEHOACH, values, QuanLyDatabase.KEHOACH_ID + " = " + id, null);
        if (result < 0) return false;
        return true;
    }
    public boolean kehoach_delete(KeHoachValues plan_values) {
        db = myData.getWritableDatabase();
        String id = "'" + plan_values.getId() + "'";
        int result = db.delete(QuanLyDatabase.TABLE_KEHOACH, QuanLyDatabase.KEHOACH_ID + " = " + id, null);
        if(result < 0) return  false;
        return true;
    }
    public ArrayList<KeHoachValues> plan_list() {
        ArrayList<KeHoachValues> list = new ArrayList<>();
        db = myData.getReadableDatabase();

        String sql = "SELECT * FROM " + QuanLyDatabase.TABLE_KEHOACH;
        Cursor cur = db.rawQuery(sql, null);
        cur.moveToPosition(-1);
        while (cur.moveToNext()) {
            KeHoachValues values = new KeHoachValues(cur.getString(cur.getColumnIndex(QuanLyDatabase.KEHOACH_NAME)),
                    cur.getString(cur.getColumnIndex(QuanLyDatabase.KEHOACH_TARGET)),
                    cur.getString(cur.getColumnIndex(QuanLyDatabase.KEHOACH_NGAYBATDAU)),
                    cur.getString(cur.getColumnIndex(QuanLyDatabase.KEHOACH_NGAYKETTHUC)),
                    cur.getString(cur.getColumnIndex(QuanLyDatabase.KEHOACH_SOTIEN)));
            list.add(values);
        }
        return list;
    }

    //Bảng thống kế (Pie Chart)
    public ArrayList<String> thongke_list_money(String s) {
        String caulenh = "'" + s + "'";
        ArrayList<String> list = new ArrayList<>();
        db = myData.getReadableDatabase();
        String sql = "SELECT "+ QuanLyDatabase.THUCHI_MONEY + " FROM "
                + QuanLyDatabase.TABLE_THUCHI + " WHERE " + QuanLyDatabase.THUCHI_CATEGORY + " = " + caulenh;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex(QuanLyDatabase.THUCHI_MONEY)));
                }while (cursor.moveToNext());
            }
        }
       return list;
    }
    public ArrayList<String> thongke_list_detail(String s) {
        String caulenh = "'" + s + "'";
        ArrayList<String> list = new ArrayList<>();
        db = myData.getReadableDatabase();
        String sql = "SELECT "+ QuanLyDatabase.THUCHI_DETAILS + " FROM "
                + QuanLyDatabase.TABLE_THUCHI + " WHERE " + QuanLyDatabase.THUCHI_CATEGORY + " = " + caulenh;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(cursor.getColumnIndex(QuanLyDatabase.THUCHI_DETAILS)));
                }while (cursor.moveToNext());
            }
        }
        return list;
    }
}
