package com.duan.qlsach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.qlsach.database.DbHelper;
import com.duan.qlsach.model.Sach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public SachDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Sach obj){
        ContentValues values = new ContentValues();

//        values.put("maSach", obj.maSach);
        values.put("tenSach", obj.tenSach);
        values.put("tacGia",obj.tacGia);
        values.put("NXB",obj.NXB);
        values.put("theLoai",obj.theLoai);
        values.put("soTrang",obj.soTrang);
        values.put("gia",obj.gia);
        values.put("ngayNhap", sdf.format(obj.ngayNhap));
        values.put("tomTat",obj.tomTat);
        values.put("keSach",obj.keSach);
        values.put("tinhTrang",obj.tinhTrang);

        return db.insert("Sach", null, values);
    }

    public int update(Sach obj){
        ContentValues values = new ContentValues();

//        values.put("maSach", obj.maSach);
        values.put("tenSach", obj.tenSach);
        values.put("tacGia",obj.tacGia);
        values.put("NXB",obj.NXB);
        values.put("theLoai",obj.theLoai);
        values.put("soTrang",obj.soTrang);
        values.put("gia",obj.gia);
//        values.put("ngayNhap", sdf.format(obj.ngayNhap));
        values.put("tomTat",obj.tomTat);
        values.put("keSach",obj.keSach);
        values.put("tinhTrang",obj.tinhTrang);

        return db.update("Sach", values,"maSach=?", new String[]{String.valueOf(obj.maSach)});
    }

    public int delete(String id){
        return db.delete("Sach","maSach=?", new String[]{id});
    }

    // Get tất cả data
    public List<Sach> getAll(){
        String sql = "select * from Sach";
        return getData(sql);
    }

    // Get data theo id
    public Sach getID(String id){
        String sql = "select * from Sach where maSach=?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<Sach> getData(String sql, String...selectionArgs){

        List<Sach> list = new ArrayList<Sach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();

            obj.maSach = Integer.parseInt(c.getString(c.getColumnIndex("maSach")));
            obj.tenSach = c.getString(c.getColumnIndex("tenSach"));
            obj.tacGia = c.getString(c.getColumnIndex("tacGia"));
            obj.NXB = c.getString(c.getColumnIndex("NXB"));
            obj.theLoai = Integer.parseInt(c.getString(c.getColumnIndex("theLoai")));
            obj.soTrang = Integer.parseInt(c.getString(c.getColumnIndex("soTrang")));
            obj.gia = Integer.parseInt(c.getString(c.getColumnIndex("gia")));
            try {
                obj.ngayNhap = sdf.parse(c.getString(c.getColumnIndex("ngayNhap")));
            }catch (ParseException e){
                e.printStackTrace();
            }
            obj.tomTat = c.getString(c.getColumnIndex("tomTat"));
            obj.keSach = Integer.parseInt(c.getString(c.getColumnIndex("keSach")));
            obj.tinhTrang = c.getString(c.getColumnIndex("tinhTrang"));

            list.add(obj);
        }
        return list;
    }
    // Tìm kiếm theo tên
    public List<Sach> getTimTheoTen(String ten){
        String sql = "select * from Sach where tenSach like ?";
        return getData(sql,ten);
    }
}
