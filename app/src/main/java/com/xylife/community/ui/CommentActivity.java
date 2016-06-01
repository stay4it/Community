package com.xylife.community.ui;

import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;

/**
 * Created by SK on 2016-05-30.
 */
public class CommentActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void initView() {
        mTitleText.setText(R.string.comment);
    }

    @Override
    public void initData() {

    }
}
