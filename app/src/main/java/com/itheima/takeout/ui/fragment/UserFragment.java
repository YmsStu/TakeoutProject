package com.itheima.takeout.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.ui.activity.AddressListActivity;
import com.itheima.takeout.ui.activity.LoginActivity;
import com.itheima.takeout.ui.activity.SettleCenterActivity;
import com.itheima.takeout.utils.Constant;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ywf on 2017/3/24.
 */
public class UserFragment extends Fragment {

    @InjectView(R.id.tv_user_setting)
    ImageView mTvUserSetting;
    @InjectView(R.id.iv_user_notice)
    ImageView mIvUserNotice;
    @InjectView(R.id.login)
    ImageView mLogin;
    @InjectView(R.id.username)
    TextView mUsername;
    @InjectView(R.id.phone)
    TextView mPhone;
    @InjectView(R.id.ll_userinfo)
    LinearLayout mLlUserinfo;
    @InjectView(R.id.address)
    ImageView mAddress;
    private SharedPreferences mSp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.inject(this, view);
        mAddress.setEnabled(true);
        mTvUserSetting.setEnabled(true);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_user_setting, R.id.iv_user_notice, R.id.login, R.id.address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_setting:
                clickSetting();
                break;
            case R.id.iv_user_notice:
                clickNotice();
                break;
            case R.id.login:
                clickLogin();
                break;
            case R.id.address:
                clickAddress();
                break;
        }
    }

    private void clickNotice() {
    }


    private void clickSetting() {
        //设置图标的点击事件,先做成退出登录
//        DialogFragment dialogFragment = new DialogFragment();
//        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE,0);
//        dialogFragment.show(getFragmentManager(), dialogFragment.getTag());
    }

    private void clickAddress() {
        //Toast.makeText(getContext(), "点击了地址", Toast.LENGTH_SHORT).show();
        //TODO 点击地址的操作
        Intent intent = new Intent(getContext(), AddressListActivity.class);
        startActivity(intent);
    }

    /**
     * 用户点击了登录
     */
    private void clickLogin() {
        getContext().startActivity(new Intent(getContext(), LoginActivity.class));

    }

    @Override
    public void onResume() {
        super.onResume();
        mSp = getActivity().getSharedPreferences("panpan", Context.MODE_PRIVATE);
        boolean isLogin = mSp.getBoolean(Constant.ISLOGIN, false);
        String userName = mSp.getString(Constant.USERNAME, "");

        if (isLogin) {
            //如果登录成功
            mLogin.setVisibility(View.INVISIBLE);
            mLlUserinfo.setVisibility(View.VISIBLE);
            mPhone.setText(userName);
        }

    }



}
