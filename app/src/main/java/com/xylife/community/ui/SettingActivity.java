package com.xylife.community.ui;

import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;

/**
 * Created by SK on 2016-05-17.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        mTitleText.setText(R.string.setting);
    }

    @Override
    public void initData() {

    }
}
