package com.duan.qlsach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.qlsach.database.DbHelper;
import com.duan.qlsach.model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {

    private SQLiteDatabase db;

    public TheLoaiDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(TheLoai obj){
        ContentValues values = new ContentValues();

//        values.put("maLoai", obj.maLoai);
        values.put("tenLoai", obj.tenLoai);

        return db.insert("TheLoai", null, values);
    }

    public int update(TheLoai obj){
        ContentValues values = new ContentValues();

//        values.put("maLoai", obj.maLoai);
        values.put("tenLoai", obj.tenLoai);

        return db.update("TheLoai", values,"maLoai=?", new String[]{String.valueOf(obj.maLoai)});
    }

    public int delete(String id){
        return db.delete("TheLoai","maLoai=?", new String[]{id});
    }

    // Get tất cả data
    public List<TheLoai> getAll(){
        String sql = "select * from TheLoai";
        return getData(sql);
    }

    // Get data theo id
    public TheLoai getID(String id){
        String sql = "select * from TheLoai where maLoai=?";
        List<TheLoai> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<TheLoai> getData(String sql, String...selectionArgs){

        List<TheLoai> list = new ArrayList<TheLoai>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            TheLoai obj = new TheLoai();

            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            obj.tenLoai = c.getString(c.getColumnIndex("tenLoai"));

            list.add(obj);
        }
        return list;
    }

    public List<TheLoai> getTimTheoTen(String ten){
        String sql = "select * from TheLoai where tenLoai like ?";
        return getData(sql,ten);
    }
}
