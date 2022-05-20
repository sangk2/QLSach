package com.duan.qlsach.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.qlsach.database.DbHelper;
import com.duan.qlsach.model.MuonTra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MuonTraDAO {

    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public MuonTraDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(MuonTra obj){
        ContentValues values = new ContentValues();

//        values.put("maMuon", obj.maMuon);
        values.put("maSach", obj.maSach);
        values.put("nguoiMuon", obj.nguoiMuon);
        values.put("batDau",sdf.format(obj.batDau));
        values.put("hanTra",sdf.format(obj.hanTra));
        values.put("tinhTrang",obj.tinhTrang);
        values.put("ghiChu",obj.ghiChu);

        return db.insert("MuonTra", null, values);
    }

    public int update(MuonTra obj){
        ContentValues values = new ContentValues();

//        values.put("maMuon", obj.maMuon);
        values.put("maSach", obj.maSach);
        values.put("nguoiMuon", obj.nguoiMuon);
        values.put("batDau",sdf.format(obj.batDau));
        values.put("hanTra",sdf.format(obj.hanTra));
        values.put("tinhTrang",obj.tinhTrang);
        values.put("ghiChu",obj.ghiChu);

        return db.update("MuonTra", values,"maMuon=?", new String[]{String.valueOf(obj.maMuon)});
    }

    public int delete(String id){
        return db.delete("MuonTra","maMuon=?", new String[]{id});
    }

    // Get tất cả data
    public List<MuonTra> getAll(){
        String sql = "select * from MuonTra";
        return getData(sql);
    }

    // Get data theo id
    public MuonTra getID(String id){
        String sql = "select * from MuonTra where maMuon=?";
        List<MuonTra> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<MuonTra> getData(String sql, String...selectionArgs){

        List<MuonTra> list = new ArrayList<MuonTra>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            MuonTra obj = new MuonTra();

            obj.maMuon = Integer.parseInt(c.getString(c.getColumnIndex("maMuon")));
            obj.maSach = Integer.parseInt(c.getString(c.getColumnIndex("maSach")));
            obj.nguoiMuon = c.getString(c.getColumnIndex("nguoiMuon"));
            try {
                obj.batDau = sdf.parse(c.getString(c.getColumnIndex("batDau")));
                obj.hanTra = sdf.parse(c.getString(c.getColumnIndex("hanTra")));
            }catch (ParseException e){
                e.printStackTrace();
            }
            obj.tinhTrang = c.getString(c.getColumnIndex("tinhTrang"));
            obj.ghiChu = c.getString(c.getColumnIndex("ghiChu"));

            list.add(obj);
        }
        return list;
    }

    // Tìm kiếm theo tên
//    public List<MuonTra> getTimTheoTen(String ten){
//        String sql = "select * from MuonTra where  like ?";
//        return getData(sql,ten);
//    }
}
