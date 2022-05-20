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
import com.duan.qlsach.dao.ThongKeDAO;
import com.duan.qlsach.fragment.thuvien.KeSachFragment;
import com.duan.qlsach.model.KeSach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class KeSachAdapter extends ArrayAdapter<KeSach> {

    TextView tvTenKeSach, tvSl;
    KeSachFragment fragment;
    private Context context;
    private ArrayList<KeSach> listKS;

    public KeSachAdapter(@NonNull @NotNull Context context, KeSachFragment fragment, ArrayList<KeSach> listKS) {
        super(context, 0, listKS);
        this.fragment = fragment;
        this.context = context;
        this.listKS = listKS;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.ke_sach_item, null);
        }
        final KeSach keSach = listKS.get(position);

        if (keSach != null){
            try {
                tvTenKeSach = v.findViewById(R.id.tvTenKeSach);
                tvTenKeSach.setText(keSach.tenKS);

                tvSl = v.findViewById(R.id.tvSL);
                ThongKeDAO thongKeDAO = new ThongKeDAO(context);
                tvSl.setText(thongKeDAO.getSachKe(keSach.maKS)+" sách");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Gọi function Xóa trong KeSachFragment
//                fragment.xoa(String.valueOf(keSach.maLoai));
//            }
//        });

        return v;
    }
}