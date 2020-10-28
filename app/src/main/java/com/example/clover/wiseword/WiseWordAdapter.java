package com.example.clover.wiseword;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class WiseWordAdapter extends FragmentStateAdapter {
    public WiseWordAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: return ConfidenceActivity.getInstance();
            case 1: return HappyActivity.getInstance();
            case 2: return HopeActivity.getInstance();
            case 3: return LoveActivity.getInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
