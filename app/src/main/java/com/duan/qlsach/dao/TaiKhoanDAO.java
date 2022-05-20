package com.duan.qlsach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.qlsach.database.DbHelper;
import com.duan.qlsach.model.TaiKhoan;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {

    private SQLiteDatabase db;

    public TaiKhoanDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(TaiKhoan obj){
        ContentValues values = new ContentValues();
        values.put("tenDangNhap",obj.tenDangNhap);
        values.put("tenNguoiDung",obj.tenNguoiDung);
        values.put("matKhau",obj.matKhau);

        return db.insert("TaiKhoan", null, values);
    }

    public int updatePass(TaiKhoan obj){
        ContentValues values = new ContentValues();
        values.put("tenNguoiDung",obj.tenNguoiDung);
        values.put("matKhau",obj.matKhau);

        return db.update("TaiKhoan",values,"tenDangNhap=?",
                new String[]{obj.tenDangNhap});
    }

    public int delete(String id){
        return db.delete("TaiKhoan","tenDangNhap=?",
                new String[]{id});
    }

    public List<TaiKhoan> getAll(){
        String sql = "select * from TaiKhoan";
        return getData(sql);
    }

    public TaiKhoan getID(String id){
        String sql = "select * from TaiKhoan where tenDangNhap = ?";
        List<TaiKhoan> list = getData(sql, id);
        return list.get(0);
    }


    @SuppressLint("Range")
    public List<TaiKhoan> getData(String sql, String...selectionArgs){
        List<TaiKhoan> list = new ArrayList<TaiKhoan>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            TaiKhoan obj = new TaiKhoan();

            obj.tenDangNhap = c.getString(c.getColumnIndex("tenDangNhap"));
            obj.tenNguoiDung = c.getString(c.getColumnIndex("tenNguoiDung"));
            obj.matKhau = c.getString(c.getColumnIndex("matKhau"));

            list.add(obj);
        }
        return list;
    }

    // Check login
    public int checkLogin(String id, String p){
        String sql = "select * from TaiKhoan where tenDangNhap=? and matKhau=?";
        List<TaiKhoan> list = getData(sql, id, p);
        if (list.size() ==  0){
            return -1;
        }
        return 1;
    }
}
