package com.itheima.takeout.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.Order;
import com.itheima.takeout.presenter.fragment.OrderFragmentPresenter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 订单页面
 * Created by ywf on 2017/3/24.
 */
public class OrderFragment extends Fragment {
    @InjectView(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    @InjectView(R.id.srl_order)
    SwipeRefreshLayout mSrlOrder;
    private OrderFragmentPresenter mPresenter;
    private List<Order> mOriderList;
    private OrderAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.inject(this, view);
        toNet();
        return view;
    }

    private void toNet() {
        mPresenter = new OrderFragmentPresenter(this, 1);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSrlOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toNet();

                mSrlOrder.setRefreshing(false);
                if (mAdapter!=null){
                    //刷新数据
                    mAdapter.notifyDataSetChanged();
                }

            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    /**
     * presenter 请求数据回传
     *
     * @param oriderList
     */
    public void setData(List<Order> oriderList) {
        mOriderList = oriderList;
        initRV();
    }


    /**
     * 初始化 RV
     */
    private void initRV() {

        mRvOrderList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new OrderAdapter(getActivity(), mOriderList);
        mRvOrderList.setAdapter(mAdapter);
    }

    public void error() {
        Toast.makeText(getContext(), "订单获取失败!", Toast.LENGTH_SHORT).show();
    }
}
