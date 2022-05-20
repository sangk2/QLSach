package com.duan.qlsach.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duan.qlsach.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    BottomNavigationView navBottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        navBottom = v.findViewById(R.id.bottom_nav);

        getActivity().setTitle("Thư viện");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ThuVienFragment thuvien = new ThuVienFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent1,thuvien)
                .commit();

        navBottom.setOnItemSelectedListener(item -> {
            FragmentManager manager1 = getActivity().getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.ThuVien:
                    getActivity().setTitle("Thư viện");
                    ThuVienFragment thuVienFragment = new ThuVienFragment();
                    manager1.beginTransaction()
                            .replace(R.id.flContent1, thuVienFragment)
                            .commit();
                    break;
                case R.id.ThongKe:
                    getActivity().setTitle("Thống kê");
                    ThongKeFragment thongKeFragment = new ThongKeFragment();
                    manager1.beginTransaction()
                            .replace(R.id.flContent1, thongKeFragment)
                            .commit();
                    break;
            }
            return true;
        });
        return v;
    }
}