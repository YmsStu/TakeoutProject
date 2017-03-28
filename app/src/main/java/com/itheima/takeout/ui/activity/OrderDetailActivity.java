package com.itheima.takeout.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.Good;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单详情页面
 * Created by peipanpan on 2017/3/26.
 */
public class OrderDetailActivity extends Activity implements View.OnClickListener {

    @InjectView(R.id.iv_order_detail_back)
    ImageView mIvOrderDetailBack;
    @InjectView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @InjectView(R.id.tv_order_detail_time)
    TextView mTvOrderDetailTime;

    @InjectView(R.id.ll_order_detail_type_container)
    LinearLayout mLlOrderDetailTypeContainer;
    @InjectView(R.id.ll_order_detail_type_point_container)
    LinearLayout mLlOrderDetailTypePointContainer;
    private static Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);

        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();

    }

    public static void startActivity(Context context, Order order) {
        mOrder = order;
        Intent intent = new Intent(context,OrderDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void initEvent() {
        mIvOrderDetailBack.setOnClickListener(this);
    }


    private void initData() {
        mTvSellerName.setText(mOrder.getSeller().getName());
        mTvOrderDetailTime.setText(mOrder.getDetail().time);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_order_detail_back:
                clickBack();
                break;
        }
    }

    private void clickBack() {
        finish();
    }


}
