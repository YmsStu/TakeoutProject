package com.itheima.takeout.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.utils.TimeUtils;


import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.itheima.takeout.R.id.text;

/**
 * Created by wang on 2017/3/27.
 */

public class OnlinePayActivity extends AppCompatActivity {

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_residualTime)
    TextView tvResidualTime;
    @InjectView(R.id.tv_order_name)
    TextView tvOrderName;
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.tv_order_detail)
    TextView tvOrderDetail;
    @InjectView(R.id.iv_triangle)
    ImageView ivTriangle;
    @InjectView(R.id.ll_order_toggle)
    RelativeLayout llOrderToggle;
    @InjectView(R.id.tv_receipt_connect_info)
    TextView tvReceiptConnectInfo;
    @InjectView(R.id.tv_receipt_address_info)
    TextView tvReceiptAddressInfo;
    @InjectView(R.id.ll_goods)
    LinearLayout llGoods;
    @InjectView(R.id.ll_order_detail)
    LinearLayout llOrderDetail;
    @InjectView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @InjectView(R.id.cb_pay_selected)
    CheckBox cbPaySelected;
    @InjectView(R.id.iv_pay_logo)
    ImageView ivPayLogo;
    @InjectView(R.id.tv_pay_name)
    TextView tvPayName;
    @InjectView(R.id.cb_pay_selected_wechat)
    CheckBox cbPaySelectedWechat;
    @InjectView(R.id.ll_pay_type_container)
    LinearLayout llPayTypeContainer;
    @InjectView(R.id.bt_confirm_pay)
    Button btConfirmPay;
    @InjectView(R.id.ll_linnear)
    LinearLayout llLinnear;
    private Handler mHandler = new Handler();
    private View view;
    private int time=0;
    private AlertDialog isExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_pay);
        ButterKnife.inject(this);
        initPayTime();
    }

    private void initPayTime() {
        TimeCount timeCount=new TimeCount(900000,1000);
        timeCount.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){

            //设置对话框
            getAlerDialog();

        }
        return false;
    }

    private boolean isClicked = false;
    private boolean isClickedrTiangle = false;
    private boolean isClickedwechat = false;

    @OnClick({R.id.ib_back, R.id.iv_triangle, R.id.cb_pay_selected, R.id.cb_pay_selected_wechat, R.id.bt_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                getAlerDialog();
                break;
            case R.id.iv_triangle:
                if (isClickedrTiangle == false) {
                    isClickedrTiangle = true;
                    llOrderDetail.setVisibility(View.VISIBLE);
                } else {
                    isClickedrTiangle = false;
                    llOrderDetail.setVisibility(View.GONE);

                }
                break;
            case R.id.cb_pay_selected:
                if (isClicked == false) {
                    isClicked = true;
                    cbPaySelectedWechat.setChecked(false);
                    isClickedwechat = false;
                } else {
                    isClicked = false;
                }
                initPay();
                break;
            case R.id.cb_pay_selected_wechat:
                if (isClickedwechat == false) {
                    isClickedwechat = true;
                    cbPaySelected.setChecked(false);
                    isClicked = false;
                } else {
                    isClickedwechat = false;
                }
                initPay();
                break;
            case R.id.bt_confirm_pay:
                //加载加载中画面
                addLoadingUI();
                break;
            default:

        }
    }

    private void addLoadingUI() {

        view = getLayoutInflater().inflate(R.layout.loading, (ViewGroup) llLinnear.getRootView(),true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OnlinePayActivity.this, "付款成功", Toast.LENGTH_SHORT).show();

                finish();
            }
        },5000);
    }

    //设置支付键
    private void initPay() {
        if (isClicked == true || isClickedwechat == true) {
            btConfirmPay.setEnabled(true);
        } else {
            btConfirmPay.setEnabled(false);
        }
    }

    public void getAlerDialog() {
        isExit = new AlertDialog.Builder(OnlinePayActivity.this)
                .setTitle("系统提示")//设置对话框标题
                .setMessage("确认要放弃支付么！")//设置显示的内容
                .setCancelable(false)
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        finish();
                    }

                }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件

                    }

                }).show();

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {// 计时完毕时触发
           finish();
        }
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            String time = TimeUtils.formatTime(millisUntilFinished);
            tvResidualTime.setText("请在"+time+"内完成付款");
        }
    }
}
