package com.xylife.community.ui;

import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;

/**
 * Created by SK on 2016-05-27.
 */
public class UserInfoActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initView() {
        mTitleText.setText("资料修改");
    }

    @Override
    public void initData() {

    }


}
