package com.xylife.community.fragment;


import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.android.framewok.base.BaseFragment;
import com.android.framewok.widget.CircleImageView;
import com.xylife.community.R;
import com.xylife.community.ui.FeedbackActivity;
import com.xylife.community.ui.UserInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCenterFragment extends BaseFragment {

    @BindView(R.id.my_info)
    AppCompatTextView myInfo;
    @BindView(R.id.avatar_image)
    CircleImageView avatarImg;
    @BindView(R.id.feedback)
    AppCompatTextView feedback;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        avatarImg.setOnClickListener(this);
        myInfo.setOnClickListener(this);
        feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar_image:
                gotoActivity(UserInfoActivity.class);
                break;
            case R.id.my_info:
                gotoActivity(UserInfoActivity.class);
                break;
            case R.id.feedback:
                gotoActivity(FeedbackActivity.class);
                break;
            default:
                break;
        }
    }
}
