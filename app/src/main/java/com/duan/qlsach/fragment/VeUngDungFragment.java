package com.duan.qlsach.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duan.qlsach.R;

public class VeUngDungFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ve_ung_dung, container, false);

        TextView  tv1 = v.findViewById(R.id.tv1);
        TextView tv2 = v.findViewById(R.id.tv2);
        TextView tv3 = v.findViewById(R.id.tv3);
        TextView tv4 = v.findViewById(R.id.tv4);

        tv1.setText("Dành cho người yêu sách");
        tv2.setText("Ứng dụng là một công cụ hữu ích với chức năng quản lý một tủ sách cá nhân hay một thư viện nhỏ.");

        tv3.setText("Mọi đóng góp của bạn để cải thiện ứng dụng được tốt hơn. Xin liên hệ: tansangktu2002@gmail.com");

        tv4.setText("Cảm ơn bạn đã sử dụng TSBook");
        return v;
    }
}