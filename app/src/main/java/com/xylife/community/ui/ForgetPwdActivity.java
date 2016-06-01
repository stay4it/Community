package com.xylife.community.ui;

import android.view.View;
import android.widget.Button;

import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;
import com.xylife.community.base.BaseTextActivity;
import com.xylife.community.utils.FlipitAnimation;

import butterknife.BindView;

/**
 * Created by SK on 2016-05-17.
 */
public class ForgetPwdActivity extends BaseTextActivity {

    @BindView(R.id.next)
    Button next;
    @BindView(R.id.reset_pwd)
    Button resetPwd;
    @BindView(R.id.retrieve_pwd_layout)
    View retrievePwdLayout;
    @BindView(R.id.reset_pwd_layout)
    View resetPwdLayout;

    private FlipitAnimation flipitAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {
        mTitleText.setText(R.string.retrieve_pwd);
        mRightText.setText(R.string.register);

        next.setOnClickListener(this);
        resetPwd.setOnClickListener(this);
        mRightText.setOnClickListener(this);

        flipitAnimation = new FlipitAnimation(retrievePwdLayout, resetPwdLayout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.next:
                flipitAnimation.flipit();
                break;
            case R.id.reset_pwd:
                flipitAnimation.flipit();
                break;
            case R.id.right_text:
                gotoActivity(RegisterActivity.class);
                break;
            default:
                break;
        }
    }


}
