package com.duan.qlsach.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.duan.qlsach.R;
import com.duan.qlsach.adapter.KeSachAdapter;
import com.duan.qlsach.adapter.TaiKhoanAdapter;
import com.duan.qlsach.dao.TaiKhoanDAO;
import com.duan.qlsach.model.KeSach;
import com.duan.qlsach.model.TaiKhoan;

import java.util.ArrayList;

public class QuanLyTaiKhoanFragment extends Fragment {

    ListView lv;
    ArrayList<TaiKhoan> list;

    EditText edTenDangNhap, edTenNguoidung, edMatKhau;
    Button btnCancel, btnSave;

    static TaiKhoanDAO dao;
    TaiKhoanAdapter adapter;
    TaiKhoan taiKhoan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quan_ly_tai_khoan, container, false);

        lv = v.findViewById(R.id.lvTaiKhoan);
        dao = new TaiKhoanDAO(getActivity());

        capNhatLv();

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                taiKhoan = list.get(position);
                openEdit(getActivity());
                return false;
            }
        });
        return v;
    }
    private void openEdit(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edit_taikhoan);

        dialog.setCanceledOnTouchOutside(true);

        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnDel = dialog.findViewById(R.id.btnDel);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sua(context); // 1 edit

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                xoa(String.valueOf(taiKhoan.tenDangNhap));
            }
        });
        dialog.show();
    }

    public void sua(final Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.tai_khoan_dialog);
        edTenDangNhap = dialog.findViewById(R.id.edTenDangNhap);
        edTenNguoidung = dialog.findViewById(R.id.edTenNguoidung);
        edMatKhau = dialog.findViewById(R.id.edMatKhau);
        btnCancel = dialog.findViewById(R.id.btnCancelTaiKhoan);
        btnSave = dialog.findViewById(R.id.btnSaveTaiKhoan);

        edTenDangNhap.setEnabled(false);
        edTenDangNhap.setText(taiKhoan.tenDangNhap);
        edTenNguoidung.setText(taiKhoan.tenNguoiDung);
        edMatKhau.setText(taiKhoan.matKhau);

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        btnSave.setOnClickListener(v -> {
            taiKhoan = new TaiKhoan();
            taiKhoan.tenNguoiDung = edTenNguoidung.getText().toString();
            taiKhoan.matKhau = edMatKhau.getText().toString();

            if (kiemTra() > 0){
                taiKhoan.tenDangNhap = edTenDangNhap.getText().toString();
                if (dao.updatePass(taiKhoan) > 0){
                    Toast.makeText(context,"Sửa thành công",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_SHORT).show();
                }
            capNhatLv();
            dialog.dismiss(); // đóng dialog
            }
        });
        dialog.show();
    }

    public void xoa(final String id){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete);

        // Khi chạm bên ngoài dialog sẽ ko đóng
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog. findViewById(R.id.btnYesDel);
        Button btnNo = dialog.findViewById(R.id.btnNoDel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.delete(id);
                Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                capNhatLv();
                dialog.cancel();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    void capNhatLv(){
        list = (ArrayList<TaiKhoan>) dao.getAll();
        adapter = new TaiKhoanAdapter(getActivity(), this, list);
        lv.setAdapter(adapter);
    }

    int kiemTra(){
        int check = 1;
        if(edTenNguoidung.getText().length() == 0 || edMatKhau.getText().length() == 0){
            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}