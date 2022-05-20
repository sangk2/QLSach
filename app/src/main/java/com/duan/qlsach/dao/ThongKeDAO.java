package com.duan.qlsach.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.duan.qlsach.database.DbHelper;
import com.duan.qlsach.model.Sach;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public ThongKeDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
    }

//    // Thống kê top 10
//    @SuppressLint("Range")
//    public List<Top> getTop(){
//        String sqlTop = "select maSach, count(maSach) as soLuong from PhieuMuon " +
//                "group by maSach order by soLuong desc limit 10";
//        List<Top> list = new ArrayList<Top>();
//        SachDAO sachDAO = new SachDAO(context);
//        Cursor c = db.rawQuery(sqlTop, null);
//        while (c.moveToNext()){
//            Top top = new Top();
//            Sach sach = sachDAO.getID(c.getString(c.getColumnIndex("maSach")));
//            top.tenSach = sach.tenSach;
//            top.soLuong = Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
//
//            list.add(top);
//        }
//        return list;
//    }

    // Thống kê Sl sách có trong kệ sách
    @SuppressLint("Range")
    public int getSachKe(int maKS){
        String sqlSachKe = "select count(maSach) as sachKe from Sach where keSach = ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlSachKe,new String[]{String.valueOf(maKS)});

        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("sachKe"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public int getSachTheLoai(int maLoai){
        String sqlSachTheLoai = "select count(theLoai) as sachTheLoai from Sach where theLoai = ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlSachTheLoai,new String[]{String.valueOf(maLoai)});

        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("sachTheLoai"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public int getTongTien(){
        String sqlTongTien = "select sum(gia) as TongTien from Sach";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlTongTien,null);

        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("TongTien"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public int getTongSach(){
        String sqlTongSach = "select count(maSach) as TongSach from Sach";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlTongSach,null);

        while (c.moveToNext()){
            try{
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("TongSach"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

    // Thống kê giá trị sách trong khoảng thời gian
    @SuppressLint("Range")
    public int getTienTheoDK(String tuNgay, String denNgay){
        String sqlTienTheoDK = "select sum(gia) as tienTheoDK from Sach where ngayNhap between ? and ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlTienTheoDK, new String[]{tuNgay,denNgay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("tienTheoDK"))));
            }catch (Exception e){
                list.add(0);
            }
// Giá tiền chỉ in ra 1 dòng
// nếu trong khoảng thời gian mà có thì nó sẽ in ra tổng tiền
// còn nếu không có thì nó sẽ in ra 0
        }
        return list.get(0);
    }
    @SuppressLint("Range")
    public int getSachTheoDK(String tuNgay, String denNgay){
        String sqlSachTheoDK = "select count(maSach) as sachTheoDK from Sach where ngayNhap between ? and ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlSachTheoDK,new String[]{tuNgay,denNgay});

        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("sachTheoDK"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }


}
