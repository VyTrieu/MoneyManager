package com.example.acer.quanlychitieu.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Acer on 22/05/2017.
 */

public class QuanLyDatabase extends SQLiteOpenHelper {
    //Thông tin Database

    private static final String DATABASE_NAME = "QuanLyChiTieu.db";
    private static Integer DATABASE_VERSION = 1;

    //Tên bảng
    public static final String TABLE_THUCHI = "ThuChi_Table";
    public static final String TABLE_KEHOACH = "KeHoach_Table";

    //Tên các cột của bảng ThuChi_Table
    public static final String THUCHI_ID = "id";
    public static final String THUCHI_CATEGORY = "chitieu";
    public static final String THUCHI_DETAILS = "loaichitieu";
    public static final String THUCHI_MONEY = "sotien";
    public static final String THUCHI_DATE = "ngay";
    public static final String THUCHI_NOTE = "ghichu";

    //Tên các cột của bảng KeHoach_Table
    public static final String KEHOACH_ID = "id";
    public static final String KEHOACH_NAME = "ten";
    public static final String KEHOACH_TARGET = "muctieu";
    public static final String KEHOACH_SOTIEN = "sotien";
    public static final String KEHOACH_NGAYBATDAU = "ngaybatdau";
    public static final String KEHOACH_NGAYKETTHUC = "ngayketthuc";

    //Tên các cột của bảng NguoiDung_Table
    public static final String NGUOIDUNG_ID = "id";
    public static final String NGUOIDUNG_EMAIL = "email";
    public static final String NGUOIDUNG_MATKHAU = "matkhau";

    public QuanLyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_THUCHI_TABLE = "CREATE TABLE " + TABLE_THUCHI + "(" +
                THUCHI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                THUCHI_CATEGORY + " TEXT, " +
                THUCHI_DETAILS + " TEXT, " +
                THUCHI_MONEY + " INTEGER, " +
                THUCHI_DATE + " TEXT, " +
                THUCHI_NOTE + " TEXT " + " )";
        String CREATE_KEHOACH_TABLE = "CREATE TABLE " + TABLE_KEHOACH + "(" +
                KEHOACH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEHOACH_NAME + " TEXT, " +
                KEHOACH_TARGET + " INTEGER, " +
                KEHOACH_NGAYBATDAU + " TEXT, " +
                KEHOACH_NGAYKETTHUC + " TEXT, " +
                KEHOACH_SOTIEN + " INTEGER " + ")";
        db.execSQL(CREATE_THUCHI_TABLE);
        db.execSQL(CREATE_KEHOACH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUCHI);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_KEHOACH);
            onCreate(db);
        }
    }
}
