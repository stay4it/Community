package com.xylife.community.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.framewok.base.BaseParentActivity;
import com.xylife.community.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseTextActivity extends BaseParentActivity implements View.OnClickListener{
    @BindView(R.id.title_text)
    protected TextView mTitleText;
    @BindView(R.id.back_img)
    protected ImageView mBackBtn;
    @BindView(R.id.right_text)
    protected TextView mRightText;

    @Override
    protected void initTitleBar() {
        ButterKnife.bind(this);

    }

    @Override
    @OnClick({R.id.back_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_img:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
