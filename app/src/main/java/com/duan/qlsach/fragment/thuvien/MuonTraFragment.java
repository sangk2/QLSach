package com.duan.qlsach.fragment.thuvien;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.qlsach.R;
import com.duan.qlsach.adapter.MuonTraAdapter;
import com.duan.qlsach.adapter.SachSpinnerAdapter;
import com.duan.qlsach.dao.MuonTraDAO;
import com.duan.qlsach.dao.SachDAO;
import com.duan.qlsach.model.MuonTra;
import com.duan.qlsach.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MuonTraFragment extends Fragment {

    ListView lv;
    FloatingActionButton fab;

    TextView tvTinhTrang;
//    EditText edMaMuon;
    EditText edNguoiMuon, edBatDau, edHanTra, edGhiChu;
    Spinner spMaSach;
    ImageView imgBatDau, imgHanTra;
    Button btnSave, btnCancel;

    int maMaMuonTra;

    static MuonTraDAO dao;
    ArrayList<MuonTra> list;
    MuonTraAdapter adapter;
    MuonTra muonTra;

    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    int maSach, positonSach;
    String tinhTrang;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;

    public static MuonTraFragment newInstance() {
        return new MuonTraFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_muon_tra, container, false);

        lv = v.findViewById(R.id.lvMuonTra);
        fab = v.findViewById(R.id.fab);
        dao = new MuonTraDAO(getActivity());

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
                muonTra = list.get(position);
                maMaMuonTra = list.get(position).maMuon;
                openEdit(getActivity());
                return false;
            }
        });

        return v;
    }

    private void openEdit(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edit_muontra);

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
                xoa(String.valueOf(muonTra.maMuon));
            }
        });
        dialog.show();
    }

    public void openDialong(final Context context, final int type){
        // custom dialog
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.muon_tra_dialog);
//        edMaMuon = dialog.findViewById(R.id.edMaMuon);
        spMaSach = dialog.findViewById(R.id.spMaSach);
        edNguoiMuon = dialog.findViewById(R.id.edNguoiMuon);
        edBatDau = dialog.findViewById(R.id.edBatDau);
        edHanTra = dialog.findViewById(R.id.edHanTra);
        imgBatDau = dialog.findViewById(R.id.imgBatDau);
        imgHanTra = dialog.findViewById(R.id.imgHanTra);
        tvTinhTrang = dialog.findViewById(R.id.tvTinhTrang);
        edGhiChu = dialog.findViewById(R.id.edGhiChu);
        btnCancel = dialog.findViewById(R.id.btnCancelMuon);
        btnSave = dialog.findViewById(R.id.btnSaveMuon);

        sachDAO = new SachDAO(context);
        listSach = (ArrayList<Sach>) sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context, listSach);
        spMaSach.setAdapter(sachSpinnerAdapter);
        // Lấy maSach
        spMaSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).maSach;
                tinhTrang = listSach.get(position).tinhTrang;
                tvTinhTrang.setText("Tình trạng: "+tinhTrang);
                Toast.makeText(context,listSach.get(position).tenSach,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(context,
                        0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                        edBatDau.setText(sdf.format(c.getTime()));
                    }
                }, mYear, mMonth, mDay);
                d.show();
            }
        });
        imgHanTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(context,
                        0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                        edHanTra.setText(sdf.format(c.getTime()));
                    }
                }, mYear, mMonth, mDay);
                d.show();
            }
        });

        // Kiểm tra type insert 0 hay Edit 1
//        edMaMuon.setEnabled(false);
        if (type != 0){
//            edMaMuon.setText(String.valueOf(muonTra.maMuon));
            for (int i=0; i<listSach.size();i++){
                if (muonTra.maSach == (listSach.get(i).maSach)){
                    positonSach = i;
                }
            }
            spMaSach.setSelection(positonSach);
            edNguoiMuon.setText(muonTra.nguoiMuon);
            edBatDau.setText(sdf.format(muonTra.batDau));
            edHanTra.setText(sdf.format(muonTra.hanTra));
            tvTinhTrang.setText(muonTra.tinhTrang);
            edGhiChu.setText(muonTra.ghiChu);
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
                muonTra = new MuonTra();
                muonTra.maSach = maSach;
                muonTra.nguoiMuon = edNguoiMuon.getText().toString();
                try {
                    muonTra.batDau = sdf.parse(edBatDau.getText().toString());
                    muonTra.hanTra = sdf.parse(edHanTra.getText().toString());
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                muonTra.tinhTrang = tinhTrang;
                muonTra.ghiChu = edGhiChu.getText().toString();

                if (kiemTra() > 0){
                    if (type == 0){ // 0 insert
                        if (dao.insert(muonTra) > 0){
                            Toast.makeText(context,"Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }else { // 1  update
//                        muonTra.maMuon = Integer.parseInt(edMaMuon.getText().toString());
                        muonTra.maMuon = maMaMuonTra;
                        if (dao.update(muonTra) > 0){
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
        list = (ArrayList<MuonTra>) dao.getAll();
        adapter = new MuonTraAdapter(getActivity(), this, list);
        lv.setAdapter(adapter);
    }

    public int kiemTra(){
        int check = 1;
        if (edBatDau.getText().length() == 0 || edHanTra.getText().length() == 0 ){
            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }
//        if (edHanTra.getText().length() < edBatDau.getText().length()){
//            Toast.makeText(getContext(),"Hạn trả phải hợp lệ",Toast.LENGTH_SHORT).show();
//            check = -1;
//        }
        else {
            String mbatDau = edBatDau.getText().toString();
            String mhanTra = edHanTra.getText().toString();
            if (!mbatDau.matches("[0-9]{4}/[0-9]{1,2}/[0-9]{1,2}")||
                    !mhanTra.matches("[0-9]{2,4}/[0-9]{1,2}/[0-9]{1,2}")){
                Toast.makeText(getContext(),"Sai định dạng năm/tháng/ngày",Toast.LENGTH_SHORT).show();
                check = -1;
            }else {
            try {
                Date batDau = sdf.parse(edBatDau.getText().toString());
                Date hanTra = sdf.parse(edHanTra.getText().toString());

                if (batDau.after(hanTra)){
                    Toast.makeText(getContext(),"Hạn trả không hợp lệ",Toast.LENGTH_SHORT).show();
                    check = -1;
                }

            }catch (ParseException e){
                e.printStackTrace();
            }
            }
        }
        return check;
    }
}