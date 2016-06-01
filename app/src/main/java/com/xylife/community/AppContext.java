package com.xylife.community;

import android.app.Application;

import com.android.framewok.AppBaseContext;
import com.baidu.mapapi.SDKInitializer;

public class AppContext extends Application {

    private static AppContext instance;

    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SDKInitializer.initialize(this);
    }




}
