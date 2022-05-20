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
import com.duan.qlsach.model.KeSach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class KeSachSpinnerAdapter extends ArrayAdapter<KeSach> {

    private Context context;
    private ArrayList<KeSach> list;
    TextView tvMaKeSach, tvTenKeSach;

    public KeSachSpinnerAdapter(@NonNull @NotNull Context context, ArrayList<KeSach> list) {
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
            v = inflater.inflate(R.layout.ke_sach_item_spinner, null);
        }
        final KeSach keSach = list.get(position);

        if (keSach != null){
            try {
                tvMaKeSach = v.findViewById(R.id.tvMaKeSachSp);
                tvMaKeSach.setText(keSach.maKS+ ". ");

                tvTenKeSach = v.findViewById(R.id.tvTenKeSachSp);
                tvTenKeSach.setText(keSach.tenKS);
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
            v = inflater.inflate(R.layout.ke_sach_item_spinner,null);
        }
        final KeSach keSach = list.get(position);

        if (keSach != null){
            try {
                tvMaKeSach = v.findViewById(R.id.tvMaKeSachSp);
                tvMaKeSach.setText(keSach.maKS+ " - ");

                tvTenKeSach = v.findViewById(R.id.tvTenKeSachSp);
                tvTenKeSach.setText(keSach.tenKS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}

