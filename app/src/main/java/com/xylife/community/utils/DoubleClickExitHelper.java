package com.xylife.community.utils;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;

import com.android.framewok.AppManager;

/**
 * 双击退出工具类
 *
 * @author wxc
 */
public class DoubleClickExitHelper {

    private final Activity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Snackbar snackbar;

    public DoubleClickExitHelper(Activity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
        AppManager.getAppManager().addActivity(mActivity);
    }

    /**
     * Activity onKeyDown事件
     */
    public boolean onKeyDown(int keyCode, View view) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (snackbar != null) {
                snackbar.dismiss();
            }
            AppManager.getAppManager().AppExit(mActivity);
            return true;
        } else {
            isOnKeyBacking = true;
            if (snackbar == null) {
                snackbar = Snackbar.make(view, "再次点击退出应用", Snackbar.LENGTH_SHORT);
            }
            snackbar.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    private Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    };
}
