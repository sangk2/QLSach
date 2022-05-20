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
import com.duan.qlsach.fragment.thuvien.TheLoaiFragment;
import com.duan.qlsach.model.TheLoai;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TheLoaiAdapter extends ArrayAdapter<TheLoai> {

    TextView tvTenTheLoai, tvSl;
    TheLoaiFragment fragment;
    private Context context;
    private ArrayList<TheLoai> listTL;

    public TheLoaiAdapter(@NonNull @NotNull Context context, TheLoaiFragment fragment, ArrayList<TheLoai> listTL) {
        super(context, 0, listTL);
        this.fragment = fragment;
        this.context = context;
        this.listTL = listTL;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.the_loai_item, null);
        }
        final TheLoai theLoai = listTL.get(position);

        if (theLoai != null){
            try {

                tvTenTheLoai = v.findViewById(R.id.tvTenTheLoai);
                tvTenTheLoai.setText(theLoai.tenLoai);

                tvSl = v.findViewById(R.id.tvSL);
                ThongKeDAO thongKeDAO = new ThongKeDAO(context);
                tvSl.setText(thongKeDAO.getSachTheLoai(theLoai.maLoai)+" sách");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}
