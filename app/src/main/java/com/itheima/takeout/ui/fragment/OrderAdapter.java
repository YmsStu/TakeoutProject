package com.itheima.takeout.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.Good;
import com.itheima.takeout.model.net.bean.GoodsInfo;
import com.itheima.takeout.model.net.bean.Order;
import com.itheima.takeout.ui.activity.OrderDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单页面的 adapter
 * Created by peipanpan on 2017/3/26.
 */

public class OrderAdapter extends RecyclerView.Adapter {
    private List<Order> mData;
    private Context mContext;

    public OrderAdapter(Context context, List<Order> oriderList) {
        mContext = context;
        mData = oriderList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(position);

    }




    @Override
    public int getItemCount() {
        return mData!=null?mData.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_order_item_seller_logo)
        ImageView mIvOrderItemSellerLogo;
        @InjectView(R.id.tv_order_item_seller_name)
        TextView mTvOrderItemSellerName;
        @InjectView(R.id.tv_order_item_type)
        TextView mTvOrderItemType;
        @InjectView(R.id.tv_order_item_time)
        TextView mTvOrderItemTime;
        @InjectView(R.id.tv_order_item_foods)
        TextView mTvOrderItemFoods;
        @InjectView(R.id.tv_order_item_money)
        TextView mTvOrderItemMoney;
        @InjectView(R.id.tv_order_item_multi_function)
        TextView mTvOrderItemMultiFunction;
        private Order mOrder;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }


         public void setData(final int position) {
             mOrder = mData.get(position);
             //图标
             Picasso.with(mContext).load(mOrder.getSeller().pic).into(mIvOrderItemSellerLogo);
             mTvOrderItemSellerName.setText(mOrder.getSeller().getName());
             List<GoodsInfo> goodsInfos = mOrder.getGoodsInfos();
             int size = goodsInfos.size();
             String foods = "";
             for (GoodsInfo goodsInfo : goodsInfos) {
                 foods +=goodsInfo.name+" ";
             }
             mTvOrderItemFoods.setText(foods+"共"+size+"个商品");

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(MyApplication.getContext(), position+"", Toast.LENGTH_SHORT).show();
                     new OrderDetailActivity().startActivity(MyApplication.getContext(),mOrder);
                 }
             });
         }
     }
}
