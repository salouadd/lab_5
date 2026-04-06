package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new TempFragment();
            case 1: return new DistanceFragment();
            case 2: return new HistoryFragment();
            default: return new TempFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Now 3 tabs: Temp, Distance, History
    }
}