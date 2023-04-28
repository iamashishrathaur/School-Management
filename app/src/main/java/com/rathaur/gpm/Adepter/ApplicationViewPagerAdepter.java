package com.rathaur.gpm.Adepter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rathaur.gpm.ApplicationStudentFragment;
import com.rathaur.gpm.ApplicationTeacherFragment;

public class ApplicationViewPagerAdepter extends FragmentPagerAdapter {
    public ApplicationViewPagerAdepter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       if (position==0){
           return new ApplicationStudentFragment();
       }
       else {
           return new ApplicationTeacherFragment();
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
