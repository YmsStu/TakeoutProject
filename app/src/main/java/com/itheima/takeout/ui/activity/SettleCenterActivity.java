package com.itheima.takeout.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.GoodsBean;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by wang on 2017/3/27.
 */

public class SettleCenterActivity extends AppCompatActivity {


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.rl_right)
    ImageView rlRight;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.tv_phone)
    TextView tvPhone;
    @InjectView(R.id.tv_label)
    TextView tvLabel;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.ll_selected_address_container)
    LinearLayout llSelectedAddressContainer;
    @InjectView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @InjectView(R.id.rl_location)
    RelativeLayout rlLocation;
    @InjectView(R.id.rl_left)
    ImageView rlLeft;
    @InjectView(R.id.iv_logo)
    ImageView ivLogo;
    @InjectView(R.id.tv_seller_name)
    TextView tvSellerName;
    @InjectView(R.id.goods_recyclerView)
    RecyclerView mRecyclerView;
    @InjectView(R.id.ll_select_goods)
    LinearLayout llSelectGoods;
    @InjectView(R.id.tv_send_price)
    TextView tvSendPrice;
    @InjectView(R.id.tv_count_price)
    TextView tvCountPrice;
    @InjectView(R.id.tv_submit)
    TextView tvSubmit;
    private ArrayList<GoodsBean> Datas = new ArrayList<>();
    private MyViewHolder holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_center);
//        ButterKnife.inject(this)
        ButterKnife.inject(this);
        init();

    }

    //初始化界面
    private void init() {

        //如果有默认地址
        if (true) {
            llSelectedAddressContainer.setVisibility(View.VISIBLE);
            tvSelectAddress.setVisibility(View.GONE);
        } else {
            llSelectedAddressContainer.setVisibility(View.GONE);
            tvSelectAddress.setVisibility(View.VISIBLE);
        }

        //初始化已选商品栏
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter());

    }

    //初始化商品栏


    @OnClick({R.id.ib_back, R.id.rl_location, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rl_location:
//                //跳转到收货地址

                break;
            case R.id.tv_submit:
                //跳转到在线支付
                Intent intent = new Intent(SettleCenterActivity.this, OnlinePayActivity.class);

                startActivity(intent);
                break;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SettleCenterActivity.this).inflate(R.layout.item_settle_center_goods, parent, false);
            holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder) holder).tvName.setText(Datas.get(position).shopname);
            ((MyViewHolder) holder).tvCount.setText(Datas.get(position).count);
            ((MyViewHolder) holder).tvPrice.setText(Datas.get(position).goodsprice);
        }

        @Override
        public int getItemCount() {
            return Datas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        private TextView tvCount;
        private TextView tvPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

        }
    }

}
