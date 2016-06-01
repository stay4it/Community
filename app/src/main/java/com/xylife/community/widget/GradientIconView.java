package com.xylife.community.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xylife.community.R;

/**
 * Created by David Wong on 2015/10/20.
 */
public class GradientIconView extends FrameLayout {

    private ImageView mTopIconView;
    private ImageView mBottomIconView;

    private static final String INSTANCE_STATE = "instance_state";
    private static final String STATE_ALPHA = "state_alpha";

    private float mAlpha = 0f;

    public GradientIconView(Context context) {
        this(context, null, 0);
    }

    public GradientIconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientIconView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);

        // Get attributes
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.GradientIconView);

        BitmapDrawable drawable;

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {

            int attr = a.getIndex(i);
            if (attr == R.styleable.GradientIconView_top_icon) {
                drawable = (BitmapDrawable) a.getDrawable(attr);
                setTopIconView(drawable);
            } else if (attr == R.styleable.GradientIconView_bottom_icon) {
                drawable = (BitmapDrawable) a.getDrawable(attr);
                setBottomIconView(drawable);
            }
        }

        a.recycle();
        setIconAlpha(mAlpha);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gradient_icon_layout, this, true);
        mTopIconView = (ImageView) findViewById(R.id.top_icon_view);
        mBottomIconView = (ImageView) findViewById(R.id.bottom_icon_view);
    }

    public void setIconAlpha(float alpha) {
        mTopIconView.setAlpha(alpha);
        mBottomIconView.setAlpha(1 - alpha);
        this.mAlpha = alpha;
    }

    public void setTopIconView(Drawable drawable) {
        mTopIconView.setBackgroundDrawable(drawable);
    }

    public void setBottomIconView(Drawable drawable) {
        mBottomIconView.setBackgroundDrawable(drawable);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(STATE_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATE_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
        } else {
            super.onRestoreInstanceState(state);
        }

    }

}
