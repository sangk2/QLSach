package com.duan.qlsach.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.duan.qlsach.R;
import com.duan.qlsach.dao.TheLoaiDAO;
import com.duan.qlsach.dao.ThongKeDAO;
import com.duan.qlsach.model.TheLoai;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ThongKeFragment extends Fragment {

    Button btnTuNgay, btnDenNgay, btnThongKeSachMua;
    TextView tvTongTien, tvTongSach, tvThongKeSachMua, tvChiTietThongKeSachMua;
    EditText edTuNgay, edDenNgay;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        tvTongTien = v.findViewById(R.id.tvTongTien);
        tvTongSach = v.findViewById(R.id.tvTongSach);
        edTuNgay = v.findViewById(R.id.edTuNgay);
        edDenNgay = v.findViewById(R.id.edDenNgay);
        btnTuNgay = v.findViewById(R.id.btnTuNgay);
        btnDenNgay = v.findViewById(R.id.btnDenNgay);
        btnThongKeSachMua = v.findViewById(R.id.btnThongKeSachMua);
        tvThongKeSachMua = v.findViewById(R.id.tvThongKeSachMua);
        tvChiTietThongKeSachMua = v.findViewById(R.id.tvChiTietThongKeSachMua);

        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());

        tvTongTien.setText(DecimalFormat(thongKeDAO.getTongTien())+" VND");
        tvTongSach.setText(thongKeDAO.getTongSach()+"");
        thongKeTienTheoDK();
        thongKeTheLoai(v);

        return v;
    }

    public String DecimalFormat(int cost){
        try {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            DecimalFormat df  = new DecimalFormat("##,###", dfs);
            return df.format(Integer.parseInt(cost+""));
        }catch (Exception e){
            return cost + "";
        }
    }
    private void thongKeTienTheoDK(){
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                        edTuNgay.setText(sdf.format(c.getTime()));
                    }
                }, mYear, mMonth, mDay);
                d.show();
            }
        });
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
                        edDenNgay.setText(sdf.format(c.getTime()));
                    }
                }, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnThongKeSachMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edTuNgay.getText().toString();
                String denNgay = edDenNgay.getText().toString();

                if (kiemTra() > 0){
                    ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
                    tvThongKeSachMua.setText(thongKeDAO.getTienTheoDK(tuNgay,denNgay)+" VND");
                    String giaTien = tvThongKeSachMua.getText().toString();
                    tvChiTietThongKeSachMua.setText("Mua "+thongKeDAO.getSachTheoDK(tuNgay,denNgay)+
                            " sách trị giá "+giaTien);
                }
            }
        });
    }
    int kiemTra(){
        int check = 1;
        try {
            Date tuNgay = sdf.parse(edTuNgay.getText().toString());
            Date denNgay = sdf.parse(edDenNgay.getText().toString());

            if (tuNgay.after(denNgay)){
                Toast.makeText(getContext(),"Ngày sau phải lớn hơn ngày trước",Toast.LENGTH_SHORT).show();
                check = -1;
            }

        }catch (ParseException e){
            e.printStackTrace();
        }
        return check;
    }

    private void thongKeTheLoai(@NonNull View v){
        AnyChartView anyChartView = v.findViewById(R.id.any_chart_view_TL);
        // Hiển thị xoay xoay cho đến khi nạp đủ dữ liệu để hiển thị
        anyChartView.setProgressBar(v.findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.
                OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(),
                        event.getData().get("x") + ": " + event.getData().get("value")+" sách",
                        Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());

        TheLoaiDAO theLoaiDAO = new TheLoaiDAO(getActivity());
        ArrayList<TheLoai> listTheLoai = (ArrayList<TheLoai>) theLoaiDAO.getAll();
        for (int i=0; i< listTheLoai.size();i++){

            String name = listTheLoai.get(i).tenLoai;
            int value = thongKeDAO.getSachTheLoai(i+1);

            data.add(new ValueDataEntry(name,value));
        }


        pie.data(data);

        pie.animation(true);

        pie.title("Thể loại phổ biến");
        pie.title()
                .fontColor("black")
                .align(Align.LEFT)
                .margin(0d,0d,15d,0d);

        // Hiển thị chỉ số ra ngoài
        pie.labels()
                .fontColor("black")
                .position("outside");

        // Thông tin chú thích
//
        pie.legend().title("Thể loại").enabled(true);
        pie.legend().title()
                .text("Các thể loại")
                .fontColor("black")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .fontColor("black")
                .position("right")
                .itemsLayout(LegendLayout.VERTICAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }
}