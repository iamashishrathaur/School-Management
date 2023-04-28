package com.rathaur.gpm.Adepter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rathaur.gpm.CompalintStudentFragment;
import com.rathaur.gpm.CompalintTeachertFragment;

public class ComplaintsViewPagerAdepter extends FragmentPagerAdapter {
    public ComplaintsViewPagerAdepter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new CompalintStudentFragment();
        }
        else {
            return new CompalintTeachertFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Student";
        }
        else {
            return "Teacher";
        }
    }
}
