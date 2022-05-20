package com.duan.qlsach.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duan.qlsach.R;
import com.duan.qlsach.fragment.QuanLyTaiKhoanFragment;
import com.duan.qlsach.model.TaiKhoan;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TaiKhoanAdapter extends ArrayAdapter<TaiKhoan> {
    TextView tvTenNguoiDung, tvTenDangNhap, tvMatKhau;
    QuanLyTaiKhoanFragment fragment;
    private Context context;
    private ArrayList<TaiKhoan> list;

    public TaiKhoanAdapter(@NonNull @NotNull Context context, QuanLyTaiKhoanFragment fragment, ArrayList<TaiKhoan> list){
        super(context, 0 , list);
        this.fragment = fragment;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.tai_khoan_item, null);
        }
        final TaiKhoan taiKhoan = list.get(position);
        if (taiKhoan != null){
            try{
                tvTenNguoiDung = v.findViewById(R.id.tvTen);
                tvTenNguoiDung.setText("Người dùng: "+taiKhoan.tenNguoiDung);

                tvTenDangNhap = v.findViewById(R.id.tvTenDangNhap);
                tvTenDangNhap.setText("Tài khoản: "+taiKhoan.tenDangNhap);

                tvMatKhau = v.findViewById(R.id.tvMatKhau);
                tvMatKhau.setText("Mật khẩu: "+taiKhoan.matKhau);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return v;
    }
}
