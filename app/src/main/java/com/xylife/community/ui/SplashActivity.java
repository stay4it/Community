package com.xylife.community.ui;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;

import butterknife.BindView;

import static com.xylife.community.R.id.floating_btn;


public class SplashActivity extends BaseActivity implements Runnable {

    @BindView(floating_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.app_text)
    TextView mAppText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void run() {
        final View parentView = (View) mFloatingActionButton.getParent();
        float scale = (float) (Math.sqrt(parentView.getHeight() * parentView.getHeight() + parentView.getWidth() * parentView.getWidth()) / mFloatingActionButton.getHeight());
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", scale);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", scale);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mFloatingActionButton, scaleX, scaleY).setDuration(500);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                parentView.setBackgroundColor(SplashActivity.this.getResources().getColor(R.color.colorPrimary));
                mFloatingActionButton.setVisibility(View.GONE);
                mAppText.setVisibility(View.VISIBLE);
            }
        });
        PropertyValuesHolder holderA = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder holderYm = PropertyValuesHolder.ofFloat("translationY", 0, 300);
        ObjectAnimator textAnimator = ObjectAnimator.ofPropertyValuesHolder(mAppText, holderA, holderYm).setDuration(700);
        textAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        textAnimator.setStartDelay(500);

        textAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                gotoActivity(LoginActivity.class,true);
            }
        });
        objectAnimator.start();
        textAnimator.start();
    }

    @Override
    public void initView() {
        mBackBtn.setVisibility(View.GONE);
        mFloatingActionButton.postDelayed(this, 400);
    }

    @Override
    public void initData() {

    }
}
