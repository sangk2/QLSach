package com.duan.qlsach.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duan.qlsach.R;
import com.duan.qlsach.dao.SachDAO;
import com.duan.qlsach.dao.TaiKhoanDAO;
import com.duan.qlsach.fragment.thuvien.MuonTraFragment;
import com.duan.qlsach.model.MuonTra;
import com.duan.qlsach.model.Sach;
import com.duan.qlsach.model.TaiKhoan;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MuonTraAdapter extends ArrayAdapter<MuonTra> {

    private Context context;
    MuonTraFragment fragment;
    private ArrayList<MuonTra> list;

    TextView tvMaMuon, tvTenSachMuon, tvNguoiMuon, tvBatDau, tvHanTra;
    SachDAO sachDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    public MuonTraAdapter(@NonNull @NotNull Context context, MuonTraFragment fragment, ArrayList<MuonTra> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.muon_tra_item,null);
        }

        final MuonTra muonTra = list.get(position);

        if (muonTra != null){
            try {

                sachDAO = new SachDAO(context);
                Sach sach = sachDAO.getID(String.valueOf(muonTra.maSach));
                tvTenSachMuon = v.findViewById(R.id.tvTenSachMuon);
                tvTenSachMuon.setText(sach.tenSach);

                SharedPreferences pref = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String ten = pref.getString("USERNAME","");
                TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(context);
                TaiKhoan taiKhoan = taiKhoanDAO.getID(ten);

                tvNguoiMuon = v.findViewById(R.id.tvNguoiMuon);
                if (muonTra.nguoiMuon.length() == 0){
                    tvNguoiMuon.setText(taiKhoan.tenNguoiDung+" mượn");
                    tvNguoiMuon.setTextColor(Color.RED);
                }else {
                    tvNguoiMuon.setText(muonTra.nguoiMuon+" mượn");
                    tvNguoiMuon.setTextColor(Color.rgb(33,150,243));
                }

                tvBatDau = v.findViewById(R.id.tvBatDau);
                tvBatDau.setText(sdf.format(muonTra.batDau));

                tvHanTra = v.findViewById(R.id.tvHanTra);
                tvHanTra.setText(sdf.format(muonTra.hanTra));

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}
