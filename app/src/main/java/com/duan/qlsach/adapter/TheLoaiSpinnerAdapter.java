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
import com.duan.qlsach.model.TheLoai;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TheLoaiSpinnerAdapter extends ArrayAdapter<TheLoai> {

    private Context context;
    private ArrayList<TheLoai> list;
    TextView tvMaTheLoai, tvTenTheLoai;

    public TheLoaiSpinnerAdapter(@NonNull @NotNull Context context, ArrayList<TheLoai> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    // Hiển thị item của Spiner lên Dialog
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.the_loai_item_spinner, null);
        }
        final TheLoai theLoai = list.get(position);

        if (theLoai != null){
            try {
                tvMaTheLoai = v.findViewById(R.id.tvMaTheLoaiSp);
                tvMaTheLoai.setText(theLoai.maLoai + ". ");

                tvTenTheLoai = v.findViewById(R.id.tvTenTheLoaiSp);
                tvTenTheLoai.setText(theLoai.tenLoai);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }

    // Hiển thị Spinner

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.the_loai_item_spinner,null);
        }
        final TheLoai theLoai = list.get(position);

        if (theLoai != null){
            try {
                tvMaTheLoai = v.findViewById(R.id.tvMaTheLoaiSp);
                tvMaTheLoai.setText(theLoai.maLoai+ " - ");

                tvTenTheLoai = v.findViewById(R.id.tvTenTheLoaiSp);
                tvTenTheLoai.setText(theLoai.tenLoai);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}

