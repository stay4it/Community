package com.xylife.community.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.xylife.community.R;
import com.xylife.community.adapter.MyFragmentPagerAdapter;
import com.xylife.community.base.BaseActivity;
import com.xylife.community.fragment.AttendNotesFragment;
import com.xylife.community.fragment.CommentFragment;
import com.xylife.community.fragment.PartyDetailFragment;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * Created by SK on 2016-05-27.
 */
public class PartyDetailActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
                                                                        ViewPager.OnPageChangeListener{


    @BindArray(R.array.tab_bar_party_detail)
    CharSequence[] mLabels;

    @BindView(R.id.tab_layout)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_party_detail;
    }

    @Override
    public void initView() {
        mTitleText.setText(R.string.title_party_detail);

        tabs.setOnTabSelectedListener(this);
        for (int i = 0; i < mLabels.length; i ++) tabs.addTab(tabs.newTab().setText(mLabels[i]));

        mFragments.add(new PartyDetailFragment());
        mFragments.add(new AttendNotesFragment());
        //mFragments.add(new CommentFragment());

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mLabels));
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

        Toast.makeText(this, tab.getText(), Toast.LENGTH_SHORT).show();
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
