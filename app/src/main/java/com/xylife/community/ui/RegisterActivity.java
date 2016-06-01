package com.xylife.community.ui;

import android.view.View;
import android.widget.Button;

import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;
import com.xylife.community.utils.FlipitAnimation;

import butterknife.BindView;

/**
 * Created by SK on 2016-05-17.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.input_phone_layout)
    View inputPhoneLayout;
    @BindView(R.id.input_code_layout)
    View inputCodeLayout;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.completed)
    Button completed;

    private FlipitAnimation flipitAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        mTitleText.setText(R.string.register);

        next.setOnClickListener(this);
        completed.setOnClickListener(this);

        flipitAnimation = new FlipitAnimation(inputPhoneLayout, inputCodeLayout);
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
            case R.id.completed:
                flipitAnimation.flipit();
                break;
            default:
                break;
        }
    }
}
