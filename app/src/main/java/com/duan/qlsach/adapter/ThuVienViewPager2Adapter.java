package com.duan.qlsach.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.duan.qlsach.fragment.thuvien.KeSachFragment;
import com.duan.qlsach.fragment.thuvien.MuonTraFragment;
import com.duan.qlsach.fragment.thuvien.SachFragment;
import com.duan.qlsach.fragment.thuvien.TheLoaiFragment;

import org.jetbrains.annotations.NotNull;

public class ThuVienViewPager2Adapter extends FragmentStateAdapter {
    public ThuVienViewPager2Adapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = KeSachFragment.newInstance();
        }else if (position == 1) {
            fragment = TheLoaiFragment.newInstance();
        }else if (position == 2) {
            fragment = SachFragment.newInstance();
        }else if (position == 3) {
            fragment = MuonTraFragment.newInstance();
        }
        return fragment;
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}
