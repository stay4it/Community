package com.xylife.community.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;

    private CharSequence[] mLabels;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, CharSequence[] mLabels) {
        super(fm);
        this.mFragments = mFragments;
        this.mLabels = mLabels;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);

    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mLabels[position];
    }


}
