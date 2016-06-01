package com.xylife.community.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xylife.community.R;
import com.xylife.community.base.BaseActivity;


public class FragmentDetailsActivity extends BaseActivity {
	public static final int DISPLAY_NEWS = 0;
	public static final String BUNDLE_KEY_DISPLAY_TYPE = "BUNDLE_KEY_DISPLAY_TYPE";

	@Override
	protected int getLayoutId() {
		return R.layout.activity_fragment_details;
	}

	@Override
	public void initView() {
		mTitleText.setText("操作统计");
	}

	@Override
	public void initData() {

		Fragment fragment = null;
		Bundle bundle = getIntent().getExtras();
		int displayType = bundle.getInt(BUNDLE_KEY_DISPLAY_TYPE, DISPLAY_NEWS);
		switch (displayType) {
			case DISPLAY_NEWS:
				fragment = new Fragment();
				break;
			default:
				break;
		}

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.fragment, fragment);
		transaction.commit();
	}
}
