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
import com.duan.qlsach.dao.KeSachDAO;
import com.duan.qlsach.fragment.thuvien.SachFragment;
import com.duan.qlsach.model.KeSach;
import com.duan.qlsach.model.Sach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter<Sach> {

    private Context context;
    SachFragment fragment;
    private ArrayList<Sach> list;

    TextView tvTenSach, tvTacGia, tvSoTrang, tvKeSach;

    KeSachDAO keSachDAO;

    public SachAdapter(@NonNull @NotNull Context context, SachFragment fragment, ArrayList<Sach> list) {
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
            v = inflater.inflate(R.layout.sach_item,null);
        }

        final Sach sach = list.get(position);

        if (sach != null){
            try {

                tvTenSach =  v.findViewById(R.id.tvTenSach);
                tvTenSach.setText(sach.tenSach);

                tvTacGia = v.findViewById(R.id.tvTacGia);
                tvTacGia.setText(sach.tacGia);

                tvSoTrang = v.findViewById(R.id.tvSoTrang);
                tvSoTrang.setText(sach.soTrang+" trang");

                keSachDAO = new KeSachDAO(context);
                KeSach keSach = keSachDAO.getID(String.valueOf(sach.keSach));
                tvKeSach = v.findViewById(R.id.tvKeSach);
                tvKeSach.setText(keSach.tenKS);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}
