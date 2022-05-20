package com.duan.qlsach.fragment.thuvien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.qlsach.R;
import com.duan.qlsach.adapter.KeSachSpinnerAdapter;
import com.duan.qlsach.adapter.SachAdapter;
import com.duan.qlsach.adapter.TheLoaiSpinnerAdapter;
import com.duan.qlsach.dao.KeSachDAO;
import com.duan.qlsach.dao.SachDAO;
import com.duan.qlsach.dao.TheLoaiDAO;
import com.duan.qlsach.model.KeSach;
import com.duan.qlsach.model.Sach;
import com.duan.qlsach.model.TheLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SachFragment extends Fragment {

    ListView lv;
    FloatingActionButton fab;

//    EditText edMaSach;
    EditText edTenSach, edTacGia, edNXB, edSoTrang, edGia, edTomTat,edTimKiem;
    ImageView timKiem;
    Spinner spTheLoai, spKeSach, spTinhTrang;
    Button btnSave, btnCancel;

    int maMaSach;

    static SachDAO dao;
    ArrayList<Sach> list;
    SachAdapter adapter;
    Sach sach;

    TheLoaiSpinnerAdapter theLoaiSpinnerAdapter;
    ArrayList<TheLoai> listTheLoai;
    TheLoaiDAO theLoaiDAO;
    int maTheLoai;
    int positonTheLoai;

    KeSachSpinnerAdapter keSachSpinnerAdapter;
    ArrayList<KeSach> listKeSach;
    KeSachDAO keSachDAO;
    int maKeSach;
    int positonKeSach;

    String mTinhTrang;
    int positionTinhTrang;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public static SachFragment newInstance() {
        return new SachFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sach, container, false);

        lv = v.findViewById(R.id.lvSach);
        fab = v.findViewById(R.id.fab);
        dao = new SachDAO(getActivity());
        edTimKiem = v.findViewById(R.id.edTimKiem);
        timKiem = v.findViewById(R.id.timKiem);

        timKiem.setOnClickListener(v1 -> {
            String str = edTimKiem.getText().toString();
            list = (ArrayList<Sach>) dao.getTimTheoTen(str);
            if (list.size() != 0){
                adapter = new SachAdapter(getActivity(), this, list);
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
                openDialong(getActivity(), 0);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sach = list.get(position);
                maMaSach = list.get(position).maSach;
                openEdit(getActivity());
                return false;
            }
        });

        return v;
    }

    private void openEdit(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edit_sach);

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
                xoa(String.valueOf(sach.maSach));
            }
        });
        dialog.show();
    }

    public void openDialong(final Context context, final int type){
        // custom dialog
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.sach_dialog);
//        edMaSach = dialog.findViewById(R.id.edMaSach);
        edTenSach = dialog.findViewById(R.id.edTenSach);
        edTacGia = dialog.findViewById(R.id.edTacGia);
        edNXB = dialog.findViewById(R.id.edNXB);
        spTheLoai = dialog.findViewById(R.id.spTheLoai);
        edSoTrang = dialog.findViewById(R.id.edSoTrang);
        edGia = dialog.findViewById(R.id.edGia);
        edTomTat = dialog.findViewById(R.id.edTomTat);
        spKeSach = dialog.findViewById(R.id.spKeSach);
        spTinhTrang = dialog.findViewById(R.id.spTinhTrang);
        btnCancel = dialog.findViewById(R.id.btnCancelSach);
        btnSave = dialog.findViewById(R.id.btnSaveSach);

        theLoaiDAO = new TheLoaiDAO(context);
        listTheLoai = (ArrayList<TheLoai>) theLoaiDAO.getAll();
        theLoaiSpinnerAdapter = new TheLoaiSpinnerAdapter(context, listTheLoai);
        spTheLoai.setAdapter(theLoaiSpinnerAdapter);
        // Lấy maTheLoai
        spTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTheLoai = listTheLoai.get(position).maLoai;
//                Toast.makeText(context,"Chọn "+listTheLoai.get(position).tenLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        keSachDAO = new KeSachDAO(context);
        listKeSach = (ArrayList<KeSach>) keSachDAO.getAll();
        keSachSpinnerAdapter = new KeSachSpinnerAdapter(context, listKeSach);
        spKeSach.setAdapter(keSachSpinnerAdapter);
        // Lấy maKeSach
        spKeSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maKeSach = listKeSach.get(position).maKS;
//                Toast.makeText(context,"Chọn "+listKeSach.get(position).tenKS, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] tinhTrang = {"Sách mới","Sách cũ","Sách tái bản"};
        ArrayAdapter<String> adapterTinhTrang = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, tinhTrang);
//        adapterTinhTrang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTinhTrang.setAdapter(adapterTinhTrang);
        spTinhTrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionTinhTrang = position;
                getTinhTrang(position);
//                Toast.makeText(getContext(),"Chọn "+mTinhTrang,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Kiểm tra type insert 0 hay Edit 1
//        edMaSach.setEnabled(false); // tắt nhập Mã sách
        if (type != 0){
//            edMaSach.setText(String.valueOf(sach.maSach));
            edTenSach.setText(sach.tenSach);
            edTacGia.setText(sach.tacGia);
            edNXB.setText(sach.NXB);
            for (int i=0; i<listTheLoai.size();i++){
                if (sach.theLoai == (listTheLoai.get(i).maLoai)){
                    positonTheLoai = i;
                }
            }
            spTheLoai.setSelection(positonTheLoai);
            edSoTrang.setText(String.valueOf(sach.soTrang));
            edGia.setText(String.valueOf(sach.gia));
            edTomTat.setText(sach.tomTat);
            for (int i = 0; i<listKeSach.size(); i++) {
                if (sach.keSach == (listKeSach.get(i).maKS)) {
                    positonKeSach = i;
                }
            }
            spKeSach.setSelection(positonKeSach);

            spTinhTrang.setSelection(setTinhTrang(sach.tinhTrang));
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

                if (kiemTra() > 0){
                    sach = new Sach();
                    sach.tenSach = edTenSach.getText().toString();
                    sach.tacGia = edTacGia.getText().toString();
                    sach.NXB = edNXB.getText().toString();
                    sach.theLoai = maTheLoai;
                    sach.soTrang = Integer.parseInt(edSoTrang.getText().toString());
                    sach.gia = Integer.parseInt(edGia.getText().toString());

                    // ngayNhap là ngày mà thêm sách
                    sach.ngayNhap = new Date();

                    sach.tomTat = edTomTat.getText().toString();
                    sach.keSach = maKeSach;

                    sach.tinhTrang = mTinhTrang;

                    if (type == 0){ // 0 insert
                        if (dao.insert(sach) > 0){
                            Toast.makeText(context,"Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }else { // 1  update
//                        sach.maSach = Integer.parseInt(edMaSach.getText().toString());
                        sach.maSach = maMaSach;
                        if (dao.update(sach) > 0){
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

    private String getTinhTrang(int position){
        if (position == 0) mTinhTrang = "Sách mới";
        else if (position == 1) mTinhTrang = "Sách cũ";
        else if (position == 2) mTinhTrang = "Sách tái bản";
        return mTinhTrang;
    }
    private int setTinhTrang(String strTT){
        if (strTT == "Sách mới") positionTinhTrang = 0;
        else if (strTT == "Sách cũ") positionTinhTrang = 1;
        else if (strTT == "Sách tái bản")positionTinhTrang = 2;
        return positionTinhTrang;
    }
//    public void xoa(final String id){
//        // Sử dụng Alert
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Xóa Sách");
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

        Button btnYes =dialog. findViewById(R.id.btnYesDel);
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
        list = (ArrayList<Sach>) dao.getAll();
        adapter = new SachAdapter(getActivity(), this, list);
        lv.setAdapter(adapter);
    }

    public int kiemTra(){
        int check = 1;

        if (edTenSach.getText().length() == 0 ||
                edTacGia.getText().length() == 0 ||
                edNXB.getText().length() == 0 ||
                edSoTrang.getText().length() == 0 ||
                edGia.getText().length() == 0 ){
            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            int trang = Integer.parseInt(edSoTrang.getText().toString());
            int gia = Integer.parseInt(edGia.getText().toString());
            if (trang > 0 && gia > 0) {
                check = 1;
            }else {
                Toast.makeText(getContext(),"Phải lớn hơn 0",Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }

        return check;
    }
}