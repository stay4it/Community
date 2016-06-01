package com.xylife.community.ui;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.android.framewok.util.Util;
import com.xylife.community.R;
import com.xylife.community.adapter.MyFragmentPagerAdapter;
import com.xylife.community.base.BaseActivity;
import com.xylife.community.fragment.HomeFragment;
import com.xylife.community.fragment.ScheduleFragment;
import com.xylife.community.fragment.UserCenterFragment;
import com.xylife.community.utils.DoubleClickExitHelper;
import com.xylife.community.widget.GradientIconView;
import com.xylife.community.widget.GradientTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    public static ViewPager mViewPager;
    @BindViews({R.id.menu_home_icon, R.id.menu_date_icon, R.id.menu_me_icon})
    List<GradientIconView> mTabIconViews;
    @BindViews({R.id.menu_home_text, R.id.menu_date_text, R.id.menu_me_text})
    List<GradientTextView> mTabTextViews;
    @BindViews({R.id.tab_home_layout, R.id.tab_schedule_layout, R.id.tab_me_layout})
    List<View> mTabViews;

    @BindView(R.id.cursor)
    ImageView mCursor;// 动画图片
    private int offset = 0;// 动画图片偏移量
    private int mCursorWidth;// 动画图片宽度
    private int mCurIndex = 0;// 当前页卡编号

    @BindArray(R.array.title_bar_labels)
    CharSequence[] mLabels;
    static ArrayList<Fragment> mFragments = new ArrayList<>();
    static {
        mFragments.add(new HomeFragment());
        //mFragments.add(new PartyFragment());
        mFragments.add(new ScheduleFragment());
        mFragments.add(new UserCenterFragment());
    }

    DoubleClickExitHelper mDoubleClickExitHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mBackBtn.setVisibility(View.GONE);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        initTabIndicator();
        mViewPager.addOnPageChangeListener(this);

        initCursor();
    }

    @Override
    public void initData() {
        mTitleText.setText(mLabels[0]);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mLabels));
        mDoubleClickExitHelper = new DoubleClickExitHelper(this);
    }

    private void initCursor() {
        mCursorWidth = 1;// 获取图片宽度(ignore)
        offset = (Util.getScreenWidth(this) / 3 - mCursorWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        mCursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    private void initTabIndicator() {
        mTabIconViews.get(0).setIconAlpha(1.0f);
        mTabTextViews.get(0).setTextViewAlpha(1.0f);
    }

    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        resetOtherTabIcons();
        resetOtherTabText();
    }

    /**
     * 重置其他的Tab text
     */
    private void resetOtherTabText() {
        for (int i = 0; i < mTabTextViews.size(); i++) {
            mTabTextViews.get(i).setTextViewAlpha(0);
        }
    }

    /**
     * 重置其他的Tab icon
     */
    private void resetOtherTabIcons() {
        for (int i = 0; i < mTabIconViews.size(); i++) {
            mTabIconViews.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            GradientIconView iconLeft = mTabIconViews.get(position);
            GradientIconView iconRight = mTabIconViews.get(position + 1);

            GradientTextView textLeft = mTabTextViews.get(position);
            GradientTextView textRight = mTabTextViews.get(position + 1);

            iconLeft.setIconAlpha(1 - positionOffset);
            textLeft.setTextViewAlpha(1 - positionOffset);
            iconRight.setIconAlpha(positionOffset);
            textRight.setTextViewAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mTitleText.setText(mLabels[position]);

        int one = offset * 2 + mCursorWidth;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        Animation animation = null;
        switch (position) {
            case 0:
                if (mCurIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (mCurIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (mCurIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (mCurIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (mCurIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (mCurIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
        }
        mCurIndex = position;
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        mCursor.startAnimation(animation);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    @OnClick({R.id.tab_home_layout,R.id.tab_schedule_layout,R.id.tab_me_layout})
    public void onClick(View v) {
        resetOtherTabs();

        switch (v.getId()) {
            case R.id.tab_home_layout:
                mTabIconViews.get(0).setIconAlpha(1.0f);
                mTabTextViews.get(0).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_schedule_layout:
                mTabIconViews.get(1).setIconAlpha(1.0f);
                mTabTextViews.get(1).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_me_layout:
                mTabIconViews.get(2).setIconAlpha(1.0f);
                mTabTextViews.get(2).setTextViewAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean flag = true;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExitHelper.onKeyDown(keyCode, mViewPager);
        }
        return flag;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        // 当 API Level > 11 调用这个方法可能导致奔溃（android.os.Build.VERSION.SDK_INT > 11）
    }
}
