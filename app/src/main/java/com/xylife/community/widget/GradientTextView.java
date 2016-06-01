package com.xylife.community.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xylife.community.R;


/**
 * Created by David Wong on 2015/10/21.
 */
public class GradientTextView extends FrameLayout {

    private TextView mTopTextView;
    private TextView mBottomTextView;

    private static final String INSTANCE_STATE = "instance_state";
    private static final String STATE_ALPHA = "state_alpha";

    private float mAlpha = 0f;

    public GradientTextView(Context context) {
        this(context, null, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context);

        // Get attributes
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.GradientTextView);

        int color;

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {

            int attr = a.getIndex(i);
            if(attr == R.styleable.GradientTextView_top_text_color) {
                color = a.getColor(attr, Color.TRANSPARENT);
                setTopTextViewColor(color);
            }
            else if(attr == R.styleable.GradientTextView_bottom_text_color) {
                color = a.getColor(attr, Color.BLACK);
                setBottomTextViewColor(color);
            }
            else if(attr == R.styleable.GradientTextView_text_content) {
                String text = a.getString(attr);
                setGradientText(text);
            }
            else if(attr == R.styleable.GradientTextView_text_size) {
                int textSize = (int) a.getDimension(attr, TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
                                getResources().getDisplayMetrics()));
                setGradientTextSize(textSize);
            }
        }

        a.recycle();
        setTextViewAlpha(mAlpha);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gradient_textview_layout, this, true);
        mTopTextView = (TextView) findViewById(R.id.top_textview);
        mBottomTextView = (TextView) findViewById(R.id.bottom_textview);
    }

    public void setTopTextViewColor(int color) {
        mTopTextView.setTextColor(color);
    }

    public void setBottomTextViewColor(int color) {
        mBottomTextView.setTextColor(color);
    }

    public void setTextViewAlpha(float alpha) {
        mTopTextView.setAlpha(alpha);
        mBottomTextView.setAlpha(1 - alpha);
        this.mAlpha = alpha;
    }

    public void setGradientText(String text) {
        mTopTextView.setText(text);
        mBottomTextView.setText(text);
    }

    public void setGradientTextSize(int textSize) {
        mTopTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mBottomTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
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
