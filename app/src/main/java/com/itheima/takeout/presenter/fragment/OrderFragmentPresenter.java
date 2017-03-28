package com.itheima.takeout.presenter.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itheima.takeout.model.net.bean.Order;
import com.itheima.takeout.model.net.bean.ResponseInfo;
import com.itheima.takeout.presenter.BasePresenter;
import com.itheima.takeout.ui.fragment.OrderFragment;
import java.util.List;

import retrofit2.Call;
/**
 * 订单 presenter
 * Created by peipanpan on 2017/3/27.
 */

public class OrderFragmentPresenter extends BasePresenter {
    private int mUserId;
    private OrderFragment mFragment;

    public OrderFragmentPresenter(OrderFragment fragment,int userId) {
        mFragment = fragment;
        mUserId = userId;
        getData();
    }

    /**
     * 使用retrofit 请求数据
     */
    public void getData(){
        Call<ResponseInfo> responseInfoCall = responseInfoAPI.orderList(mUserId);
        responseInfoCall.enqueue(new CallbackAdapter());
    }

    /**
     * 请求错误是返回
     * @param msg
     */
    @Override
    protected void failed(String msg) {
        Log.d("TAG",msg);
        mFragment.error();

    }

    /**
     * 请求成功的返回
     * @param data
     */
    @Override
    protected void parserData(String data) {
       // android.util.Log.d("TAG",data);
        Gson gson = new Gson();
        //因为 data 是对象数组,这里使用泛型解析
        List<Order> OriderList = gson.fromJson(data, new TypeToken<List<Order>>() {
        }.getType());
        //将数据这只给 fragment
        mFragment.setData(OriderList);
    }
}
