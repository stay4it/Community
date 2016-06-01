package com.android.framewok;

import android.app.Application;

public class AppBaseContext extends Application {

    private static AppBaseContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static AppBaseContext getInstance() {
        return instance;
    }

}
