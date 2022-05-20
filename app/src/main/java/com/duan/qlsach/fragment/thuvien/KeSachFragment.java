package com.duan.qlsach.fragment.thuvien;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.duan.qlsach.R;
import com.duan.qlsach.adapter.KeSachAdapter;
import com.duan.qlsach.dao.KeSachDAO;
import com.duan.qlsach.model.KeSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class KeSachFragment extends Fragment {

    ListView lv;
    ArrayList<KeSach> list;
    FloatingActionButton fab;
//    EditText edMaKeSach;
    EditText edTenKeSach, edTimKiem;
    ImageView timKiem;
    Button btnSave, btnCancel;

    int maMaKeSach;

    static KeSachDAO dao;
    KeSachAdapter adapter;
    KeSach keSach;

    public static KeSachFragment newInstance() {
        return new KeSachFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ke_sach, container, false);
        lv = v.findViewById(R.id.lvKeSach);
        fab = v.findViewById(R.id.fab);
        dao = new KeSachDAO(getActivity());
        edTimKiem = v.findViewById(R.id.edTimKiem);
        timKiem = v.findViewById(R.id.timKiem);

        timKiem.setOnClickListener(v1 -> {
            String str = edTimKiem.getText().toString();
            list = (ArrayList<KeSach>) dao.getTimTheoTen(str);
            if (list.size() != 0){
                adapter = new KeSachAdapter(getActivity(), this, list);
                lv.setAdapter(adapter);
            }else {
                Toast.makeText(getContext(),"Không tìm thấy",Toast.LENGTH_SHORT).show();
                capNhatLv();
            }
        });
        capNhatLv();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialong(getActivity(), 0); // 0 insert
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                keSach = list.get(position);
                maMaKeSach = list.get(position).maKS;
                openEdit(getActivity());
                return false;
            }
        });

        return v;
    }

    private void openEdit(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edit_kesach);

        dialog.setCanceledOnTouchOutside(true);

        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnDel = dialog.findViewById(R.id.btnDel);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openDialong(context, 1); // 1 edit

            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                xoa(String.valueOf(keSach.maKS));
            }
        });
        dialog.show();
    }

    public void openDialong(final Context context, final int type){
        // custom dialog
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.ke_sach_dialog);
//        edMaKeSach = dialog.findViewById(R.id.edMaKS);
        edTenKeSach = dialog.findViewById(R.id.edTenKS);
        btnCancel = dialog.findViewById(R.id.btnCancelKS);
        btnSave = dialog.findViewById(R.id.btnSaveKS);

        // Kiểm tra type insert 0 hay Edit 1
//        edMaKeSach.setEnabled(false);
        if (type != 0){
//            edMaKeSach.setText(String.valueOf(keSach.maKS));
            edTenKeSach.setText(keSach.tenKS);

        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keSach = new KeSach();
                keSach.tenKS = edTenKeSach.getText().toString();

                if (kiemTra() > 0){
                    if (type == 0){ // 0 insert
                        if (dao.insert(keSach) > 0){
                            Toast.makeText(context,"Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }else { // 1  update
//                        keSach.maKS = Integer.parseInt(edMaKeSach.getText().toString());
                        keSach.maKS = maMaKeSach;
                        if (dao.update(keSach) > 0){
                            Toast.makeText(context,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss(); // đóng dialog
                }
            }
        });
        dialog.show();
    }

//    public void xoa(final String id){
//        // Sử dụng Alert
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Xóa Loại Sách");
//        builder.setMessage("Bạn có muốn xóa không?");
//        builder.setCancelable(true);
//
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dao.delete(id);
//                capNhatLv();
//                dialog.cancel();
//            }
//        });
//        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        builder.show();
//    }

    public void xoa(final String id){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete);

        // Khi chạm bên ngoài dialog sẽ ko đóng
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes = dialog. findViewById(R.id.btnYesDel);
        Button btnNo = dialog.findViewById(R.id.btnNoDel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.delete(id);
                Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                capNhatLv();
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

    void capNhatLv(){
        list = (ArrayList<KeSach>) dao.getAll();
        adapter = new KeSachAdapter(getActivity(), this, list);
        lv.setAdapter(adapter);
    }

    public int kiemTra(){
        int check = 1;
        if(edTenKeSach.getText().length() == 0){
            edTenKeSach.setError("Không được để trống");
//            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}