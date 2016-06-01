package com.xylife.community.ui;

import com.xylife.community.R;
import com.xylife.community.base.BaseTextActivity;

/**
 * Created by SK on 2016-06-01.
 */

public class FeedbackActivity extends BaseTextActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_feedback;
    }

    @Override
    public void initView() {
        mTitleText.setText(R.string.title_feedback);
        mRightText.setText(R.string.send);
    }

    @Override
    public void initData() {

    }
}
