package com.duan.qlsach.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    static final String dbName = "DataBook";
    static final int dbVersion = 1;

    public DbHelper(Context context){
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng tài khoản
        String createTableTaiKhoan =
                "create table TaiKhoan(" +
                        "tenDangNhap text primary key," +
                        "tenNguoiDung text not null," +
                        "matKhau text not null)";
        db.execSQL(createTableTaiKhoan);

        // Tao bang KeSach
        String createTableKeSach =
                "create table KeSach(" +
                        "maKS integer primary key autoincrement," +
                        "tenKS text not null)";
        db.execSQL(createTableKeSach);

        // Tao bang TheLoai
        String createTableTheLoai =
                "create table TheLoai (" +
                        "maLoai integer primary key autoincrement," +
                        "tenLoai text not null)";
        db.execSQL(createTableTheLoai);

        // Tao bang Sach
        String createTableSach =
                "create table Sach (" +
                        "maSach integer primary key autoincrement," +
                        "tenSach text not null," +
                        "tacGia text not null," +
                        "NXB text not null," +
                        "theLoai integer references TheLoai(maLoai)," +
                        "soTrang integer not null," +
                        "gia integer not null," +
                        "ngayNhap Date not null," +
                        "keSach integer references KeSach(maKS)," +
                        "tinhTrang text not null," +
                        "tomTat text)";
        db.execSQL(createTableSach);

        // Tao bang MuonTra
        String createTableMuonTra =
                "create table MuonTra (" +
                        "maMuon integer primary key autoincrement," +
                        "maSach integer references Sach(maSach)," +
                        "nguoiMuon text," +
                        "batDau date not null," +
                        "hanTra date not null," +
                        "tinhTrang text not null," +
                        "ghiChu text)";
        db.execSQL(createTableMuonTra);



        db.execSQL(Data_SQL.insert_TaiKhoan);
        db.execSQL(Data_SQL.insert_KeSach);
        db.execSQL(Data_SQL.insert_TheLoai);
        db.execSQL(Data_SQL.insert_Sach);
        db.execSQL(Data_SQL.insert_MuonTra);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists TaiKhoan");
        db.execSQL("drop table if exists KeSach");
        db.execSQL("drop table if exists TheLoai");
        db.execSQL("drop table if exists Sach");
        db.execSQL("drop table if exists MuonTra");
        onCreate(db);
    }
}
