package com.duan.qlsach.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duan.qlsach.LoginActivity;
import com.duan.qlsach.R;
import com.duan.qlsach.dao.TaiKhoanDAO;
import com.duan.qlsach.model.TaiKhoan;

public class DoiMatKhauFragment extends Fragment {

    EditText edPass, edNewPass, edRePass;
    Button btnThayDoiMatKhau;
    TaiKhoanDAO taiKhoanDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);

        edPass = v.findViewById(R.id.edPass);
        edNewPass = v.findViewById(R.id.edNewPass);
        edRePass = v.findViewById(R.id.edRePass);
        btnThayDoiMatKhau = v.findViewById(R.id.btnThayDoiMatKhau);

        taiKhoanDAO = new TaiKhoanDAO(getActivity());

        btnThayDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // đọc user, pass trong SharedPreferences
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = preferences.getString("USERNAME","");
                if (kiemTra() > 0){
                    TaiKhoan taiKhoan = taiKhoanDAO.getID(user);
                    taiKhoan.matKhau = edNewPass.getText().toString();

                    if (taiKhoanDAO.updatePass(taiKhoan) > 0){
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);

                    }else {
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    private int kiemTra(){
        int check = 1;
        if (edPass.getText().length() == 0 || edNewPass.getText().length() == 0 ||
                edRePass.getText().length() ==0){
            Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            // đọc user, pass trong SharedPreferences
            SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String pass = preferences.getString("PASSWORD","");

            String passOld = edPass.getText().toString();
            String passNew = edNewPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!pass.equals(passOld)){
                Toast.makeText(getContext(),"Mật khẩu cũ sai",Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!passNew.equals(rePass)){
                Toast.makeText(getContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}