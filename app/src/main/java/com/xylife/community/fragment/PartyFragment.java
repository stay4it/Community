package com.xylife.community.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.framewok.base.BaseFragment;
import com.xylife.community.R;
import com.xylife.community.adapter.MyFragmentPagerAdapter;
import com.xylife.community.base.BasePartyFragment;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SK on 2016-05-18.
 */
public class PartyFragment extends BaseFragment implements TabLayout.OnTabSelectedListener,
                                                                ViewPager.OnPageChangeListener{

    @BindArray(R.array.tab_bar_labels)
    CharSequence[] mLabels;

    @BindView(R.id.tab_layout)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tabs.setOnTabSelectedListener(this);
        for (int i = 0; i < mLabels.length; i ++) {
            tabs.addTab(tabs.newTab().setText(mLabels[i]));
            mFragments.add(new BasePartyFragment().newInstance(mLabels[i].toString()));
        }

        viewPager.setAdapter(new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mFragments, mLabels));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_viewpager;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabs.setScrollPosition(position, 0, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
