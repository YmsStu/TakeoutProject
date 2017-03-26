package com.itheima.takeout.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.utils.Constant;
import com.itheima.takeout.utils.SMSUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by peipanpan on 2017/3/26.
 */

public class LoginActivity extends Activity {
    @InjectView(R.id.iv_user_back)
    ImageView mIvUserBack;
    @InjectView(R.id.iv_user_password_login)
    TextView mIvUserPasswordLogin;
    @InjectView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @InjectView(R.id.tv_user_code)
    TextView mTvUserCode;
    @InjectView(R.id.et_user_code)
    EditText mEtUserCode;
    @InjectView(R.id.login)
    TextView mLogin;
    @InjectView(R.id.qq_login)
    ImageView mQqLogin;
    private MyCountDownTimer mCountDownTimer;
    private EventHandler mEh;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        initSMSSDK();
    }


    /**
     * SMS回调
     */
    private void initSMSSDK() {
        mEh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.d("TAG","提交验证码成功");
                        //提交验证码成功
                        phoneLogin();
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        Log.d("TAG","获取验证码成功");
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(mEh);//注册短信回调
    }

    private void phoneLogin() {
        if (mPhone != null){
            loginOK(mPhone);
        }
        Log.d("TAG","调用了但是空值");
    }

    //验证成功,跳转到新界面
    private void loginOK(final String userName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, "登录成功了!", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("panpan",MODE_PRIVATE);

                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean(Constant.ISLOGIN,true);
                edit.putString(Constant.USERNAME,userName);
                edit.commit();
                finish();
            }
        });

    }

    @OnClick({R.id.iv_user_back, R.id.iv_user_password_login, R.id.tv_user_code, R.id.login, R.id.qq_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_back:
                clickBack();
                break;
            case R.id.iv_user_password_login:
                clickUserPasswordLogin();
                break;
            case R.id.tv_user_code:
                clickGetCode();
                break;
            case R.id.login:
                clickLogin();
                break;
            case R.id.qq_login:
                clickQQLogin();
                break;
        }
    }

    /**
     * 校验手机号码
     */
    private boolean isMobileNO(String num){
        return  SMSUtil.isMobileNO(num);
    }

    /**
     * QQ登录
     */
    private void clickQQLogin() {
        Toast.makeText(this, "三方登录努力开发中,请耐心等待...", Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录
     */
    private void clickLogin() {
        String phone = mEtUserPhone.getText().toString().trim();
        String code = mEtUserCode.getText().toString().trim();
        if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(code)){
            mLogin.setEnabled(false);
            Toast.makeText(this, "登录信息不完整,请检查!", Toast.LENGTH_SHORT).show();
        } else {
            mLogin.setEnabled(true);
            //TODO 登录
            Log.d("TAG","提交了验证码");
            SMSSDK.submitVerificationCode("86",phone,code);//提交短信验证码，在监听中返回
        }
        mLogin.setEnabled(true);
    }

    /**
     * 获手机取验证码
     */
    private void clickGetCode() {
        String phone = mEtUserPhone.getText().toString().trim();
        if (!isMobileNO(phone)){
            Toast.makeText(this, "您录入的手机号码不正确,请检查!", Toast.LENGTH_SHORT).show();
        } else {
            mPhone = phone;
            //Toast.makeText(this, "获取验证码", Toast.LENGTH_SHORT).show();
            mTvUserCode.setEnabled(false);
            SMSSDK.getVerificationCode("86",phone);//请求获取短信验证码，在监听中返回



            mCountDownTimer = new MyCountDownTimer(60000,1000);
            mCountDownTimer.start();

        }

        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.registerEventHandler(mEh);

    }

    /**
     * 实现倒计时
     */
    class MyCountDownTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTvUserCode.setText("重新获取"+"("+millisUntilFinished / 1000 +")");

        }

        @Override
        public void onFinish() {
            mTvUserCode.setEnabled(true);
            mTvUserCode.setText("获取验证码");

        }
    }

    /**
     * 用户密码登录
     */
    private void clickUserPasswordLogin() {
        Toast.makeText(this, "用户密码登录开发中", Toast.LENGTH_SHORT).show();
    }

    /**
     * 返回键
     */
    private void clickBack() {
        finish();
    }


}
