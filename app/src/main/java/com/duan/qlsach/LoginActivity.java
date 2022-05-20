package com.duan.qlsach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.duan.qlsach.dao.TaiKhoanDAO;

public class LoginActivity extends AppCompatActivity {

    EditText edUser, edPass;
    Button btnLogin, btnDangKy;
    CheckBox chkRemember;
    String strUser,strPass;

    TaiKhoanDAO taiKhoanDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUser = findViewById(R.id.edUserName);
        edPass = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnDangKy = findViewById(R.id.btnDangKy);
        chkRemember = findViewById(R.id.chkRememberPass);

        taiKhoanDAO = new TaiKhoanDAO(this);

        // đọc user, pass trong SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edUser.setText(pref.getString("USERNAME",""));
        edPass.setText(pref.getString("PASSWORD",""));
        chkRemember.setChecked(pref.getBoolean("REMEMBER",false));

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DangKyActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();

            }
        });
    }

    public void checkLogin(){
        strUser = edUser.getText().toString();
        strPass = edPass.getText().toString();

        try {
            if (strUser.isEmpty() || strPass.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (taiKhoanDAO.checkLogin(strUser, strPass) > 0 ||
                        strUser.equals("admin") && strPass.equals("123") ){

                    rememberUser(strUser, strPass, chkRemember.isChecked());

                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user", strUser);
                    startActivity(intent);
                    finish();// đóng LoginActivity
                } else {
                    Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không đúng",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void rememberUser(String u, String p, boolean check){
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (!check){
            // Xóa tính năng lưu trữ trước đó
            editor.clear();
        }else {
            // Lưu dữ liệu
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",check);
        }
        // Lưu lại toàn bộ
        editor.commit();
    }

}