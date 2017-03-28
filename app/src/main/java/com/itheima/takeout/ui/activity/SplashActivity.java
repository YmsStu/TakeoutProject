package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.itheima.takeout.R;

/**
 * Created by zzh on 2017/3/26.
 * 欢迎界面
 */

public class SplashActivity extends AppCompatActivity {

    private static final int ENTER_MAIN_UI = 100;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ENTER_MAIN_UI:
                    //进入主界面
                    enterMainUI();
                    break;
            }
        }
    };
    private void enterMainUI(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //过2s后进入主界面(消息机制)
        mHandler.sendEmptyMessageDelayed(ENTER_MAIN_UI,2000);
    }
}
