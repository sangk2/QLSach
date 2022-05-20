package com.duan.qlsach.model;

import java.util.Date;

public class Sach {
    public int maSach;
    public String tenSach;
    public String tacGia;
    public String NXB;
    public int theLoai;
    public int soTrang;
    public int gia;
    public Date ngayNhap;
    public String tomTat;
    public int keSach;
    public String tinhTrang;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, String tacGia, String NXB, int theLoai, int soTrang, int gia, Date ngayNhap, String tomTat, int keSach, String tinhTrang) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.NXB = NXB;
        this.theLoai = theLoai;
        this.soTrang = soTrang;
        this.gia = gia;
        this.ngayNhap = ngayNhap;
        this.tomTat = tomTat;
        this.keSach = keSach;
        this.tinhTrang = tinhTrang;
    }
}
