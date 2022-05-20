package com.duan.qlsach.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duan.qlsach.R;
import com.duan.qlsach.adapter.ThuVienViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ThuVienFragment extends Fragment {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    public ThuVienFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.viewPager2);
        tabLayout = view.findViewById(R.id.tabLayout);

        ThuVienViewPager2Adapter adapter = new ThuVienViewPager2Adapter(getActivity());
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout,viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setText("Kệ Sách");
                }else if (position == 1){
                    tab.setText("Thể Loại");
                }else if (position == 2){
                    tab.setText("Sách");
                }else if (position == 3) {
                    tab.setText("Mượn");
                }
            }
        }).attach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thu_vien, container, false);
    }
}