package com.xylife.community.ui;

import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;

/**
 * Created by SK on 2016-05-18.
 */
public class ChargingPileActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charging_pile;
    }

    @Override
    public void initView() {
        mTitleText.setText(R.string.charging_pile);
    }

    @Override
    public void initData() {

    }
}
