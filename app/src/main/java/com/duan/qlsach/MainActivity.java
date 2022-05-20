package com.duan.qlsach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duan.qlsach.fragment.DoiMatKhauFragment;
import com.duan.qlsach.fragment.HomeFragment;
import com.duan.qlsach.fragment.QuanLyTaiKhoanFragment;
import com.duan.qlsach.fragment.ThongKeFragment;
import com.duan.qlsach.fragment.ThuVienFragment;
import com.duan.qlsach.fragment.VeUngDungFragment;
import com.duan.qlsach.model.TaiKhoan;
import com.duan.qlsach.dao.TaiKhoanDAO;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;

    View mHeaderView;
    TextView tvTenNguoiDung;
    TaiKhoanDAO taiKhoanDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);

        //set Toolbar thay cho Actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // dùng fragment ThuVien làm home
        setTitle("Thư Viện");
        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent,homeFragment)
                .commit();

        // Hiển thị tên người dùng trên Header
        NavigationView nav = findViewById(R.id.navView);

        mHeaderView = nav.getHeaderView(0);
        tvTenNguoiDung = mHeaderView.findViewById(R.id.tvTenNguoiDung);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        taiKhoanDAO = new TaiKhoanDAO(this);
        try {
            TaiKhoan taiKhoan = taiKhoanDAO.getID(user);
            String tenNguoiDung = taiKhoan.tenNguoiDung;
            tvTenNguoiDung.setText("Xin chào "+tenNguoiDung+"!");
        }catch (Exception e){
            e.printStackTrace();
        }

        // admin có quyền xóa tài khoản đăng ký
        if (user.equalsIgnoreCase("admin")){
            nav.getMenu().findItem(R.id.sub_QuanLyTaiKhoan).setVisible(true);
        }

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();

                switch (item.getItemId()){
                    case R.id.nav_ThuVien:
                        setTitle("Thư Viện");
                        HomeFragment homeFragment = new HomeFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,homeFragment)
                                .commit();
                        break;
                    case R.id.sub_ThongKe:
                        setTitle("Thống kê");
                        ThongKeFragment thongKeFragment = new ThongKeFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,thongKeFragment)
                                .commit();
                        break;
                    case R.id.sub_DoiMatKhau:
                        setTitle("Đổi mật khẩu");
                        DoiMatKhauFragment doiMatKhauFragment = new DoiMatKhauFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent, doiMatKhauFragment)
                                .commit();
                        break;
                    case R.id.sub_QuanLyTaiKhoan:
                        setTitle("Quản lý tài khoản");
                        QuanLyTaiKhoanFragment quanLyTaiKhoanFragment = new QuanLyTaiKhoanFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent, quanLyTaiKhoanFragment)
                                .commit();
                        break;
                    case R.id.sub_VeUngDung:
                        setTitle("Về ứng dụng");
                        VeUngDungFragment veUngDungFragment = new VeUngDungFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,veUngDungFragment)
                                .commit();
                        break;

                    case R.id.sub_DangXuat:
                        dangXuat();
                        break;
                    case R.id.sub_Thoat:
                        thoat();
                        break;
                }

                drawer.closeDrawers();
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    public void dangXuat(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_dangxuat);

        // Khi chạm bên ngoài dialog sẽ ko đóng
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes =dialog. findViewById(R.id.btnYesExit);
        Button btnNo = dialog.findViewById(R.id.btnNoExit);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
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
    public void thoat(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_thoat);

        // Khi chạm bên ngoài dialog sẽ ko đóng
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes =dialog. findViewById(R.id.btnYesExit);
        Button btnNo = dialog.findViewById(R.id.btnNoExit);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}