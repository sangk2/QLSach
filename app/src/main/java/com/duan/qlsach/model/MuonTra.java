package com.duan.qlsach.model;

import java.util.Date;

public class MuonTra {
    public int maMuon;
    public int maSach;
    public String nguoiMuon;
    public Date batDau;
    public Date hanTra;
    public String tinhTrang;
    public String ghiChu;

    public MuonTra() {
    }

    public MuonTra(int maMuon, int maSach, String nguoiMuon, Date batDau, Date hanTra, String tinhTrang, String ghiChu) {
        this.maMuon = maMuon;
        this.maSach = maSach;
        this.nguoiMuon = nguoiMuon;
        this.batDau = batDau;
        this.hanTra = hanTra;
        this.tinhTrang = tinhTrang;
        this.ghiChu = ghiChu;
    }
}
