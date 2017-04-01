package com.administrator.projectsdemon;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * 作者：zuo
 * 时间：2017/4/1:18:22
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
    }
}
