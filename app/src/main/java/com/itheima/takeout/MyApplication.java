package com.itheima.takeout;

import android.app.Application;
import android.content.Context;

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

    }
}
