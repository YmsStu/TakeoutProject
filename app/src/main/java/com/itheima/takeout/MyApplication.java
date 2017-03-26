package com.itheima.takeout;

import android.app.Application;
import android.content.Context;

import cn.smssdk.SMSSDK;


/**
 * Created by itheima.
 */

public class MyApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initSMSSDK();

    }

    /**
     * 短信验证初始化
     */
    private void initSMSSDK() {
        SMSSDK.initSDK(this,"1c6fd202d629c","02df80de170b634304ae4241159ddeda");
    }
}
