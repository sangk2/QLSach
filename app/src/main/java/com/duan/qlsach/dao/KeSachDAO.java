package com.duan.qlsach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.qlsach.database.DbHelper;
import com.duan.qlsach.model.KeSach;

import java.util.ArrayList;
import java.util.List;

public class KeSachDAO {

    private SQLiteDatabase db;

    public KeSachDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(KeSach obj){
        ContentValues values = new ContentValues();

//        values.put("maKS", obj.maKS);
        values.put("tenKS", obj.tenKS);

        return db.insert("KeSach", null, values);
    }

    public int update(KeSach obj){
        ContentValues values = new ContentValues();

//        values.put("maKS", obj.maKS);
        values.put("tenKS", obj.tenKS);

        return db.update("KeSach", values,"maKS=?", new String[]{String.valueOf(obj.maKS)});
    }

    public int delete(String id){
        return db.delete("KeSach","maKS=?", new String[]{id});
    }

    // Get tất cả data
    public List<KeSach> getAll(){
        String sql = "select * from KeSach";
        return getData(sql);
    }

    // Get data theo id
    public KeSach getID(String id){
        String sql = "select * from KeSach where maKS=?";
        List<KeSach> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<KeSach> getData(String sql, String...selectionArgs){

        List<KeSach> list = new ArrayList<KeSach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            KeSach obj = new KeSach();

            obj.maKS = Integer.parseInt(c.getString(c.getColumnIndex("maKS")));
            obj.tenKS = c.getString(c.getColumnIndex("tenKS"));

            list.add(obj);
        }
        return list;
    }

    // Tìm kiếm theo tên
    public List<KeSach> getTimTheoTen(String ten){
        String sql = "select * from KeSach where tenKS like ?";
        return getData(sql,ten);
    }
}
