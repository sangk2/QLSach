package com.duan.qlsach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duan.qlsach.model.TaiKhoan;
import com.duan.qlsach.dao.TaiKhoanDAO;

public class DangKyActivity extends AppCompatActivity {

    EditText edTenNguoiDung, edUser, edPass, edRePass;
    Button btnTaoTaiKhoan, btnDangNhap;

    TaiKhoanDAO taiKhoanDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        edTenNguoiDung = findViewById(R.id.edTenNguoiDung);
        edUser = findViewById(R.id.edUser);
        edPass = findViewById(R.id.edPasss);
        edRePass = findViewById(R.id.edPassRe);
        btnTaoTaiKhoan = findViewById(R.id.btnTaoTaiKhoan);
        btnDangNhap = findViewById(R.id.btnDangNhap);

        taiKhoanDAO = new TaiKhoanDAO(getApplicationContext());

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        btnTaoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.tenDangNhap = edUser.getText().toString();
                taiKhoan.tenNguoiDung = edTenNguoiDung.getText().toString();
                taiKhoan.matKhau = edPass.getText().toString();
                if (kiemTra() > 0) {
                    if (taiKhoanDAO.insert(taiKhoan) > 0) {
                        Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Tạo tài khoản thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private int kiemTra(){
        int check = 1;
        if (edTenNguoiDung.getText().length() == 0 || edUser.getText().length() == 0 ||
                edPass.getText().length() == 0 || edRePass.getText().length() == 0){
            Toast.makeText(getApplicationContext(),"Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            String pass = edPass.getText().toString();
            String repass = edRePass.getText().toString();
            if (!pass.equals(repass)){
                Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}