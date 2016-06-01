package com.xylife.community.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.framewok.base.BaseFragment;
import com.xylife.community.R;
import com.xylife.community.ui.CommentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SK on 2016-05-27.
 */
public class CommentFragment extends BaseFragment{

    @BindView(R.id.comment_btn)
    Button comment;

    public CommentFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
    }

    @Override
    public void initView(View view) {
        comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_btn:
                gotoActivity(CommentActivity.class);
                break;
            default:
                break;
        }
    }
}
