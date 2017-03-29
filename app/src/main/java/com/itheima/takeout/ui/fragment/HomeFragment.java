package com.itheima.takeout.ui.fragment;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.dagger.conponent.fragment.DaggerHomeFragmentConponent;
import com.itheima.takeout.dagger.conponent.fragment.HomeFragmentConponent;
import com.itheima.takeout.dagger.module.fragment.HomeFragmentModule;
import com.itheima.takeout.model.net.bean.HomeInfo;
import com.itheima.takeout.presenter.fragment.HomeFragmentPresenter;
import com.itheima.takeout.ui.activity.Map2Activity;
import com.itheima.takeout.ui.adapter.Bean;
import com.itheima.takeout.ui.adapter.RecycleAdapter;
import com.itheima.takeout.ui.views.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * 工作内容：
 * 1、布局
 * 2、头容器的处理
 * a、需要侵入到状态栏中
 * b、状态栏为透明
 * c、随着RecyclerView的滑动，头的透明度会变动
 * 3、RecyclerView数据加载
 * a、简单数据加载
 * b、复杂数据加载
 */
public class HomeFragment extends BaseFragment {


    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.home_tv_address)
    TextView homeTvAddress;
    @InjectView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @InjectView(R.id.ll_title_container)
    LinearLayout llTitleContainer;

    @Inject
    HomeFragmentPresenter presenter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerHomeFragmentConponent.Builder builder = DaggerHomeFragmentConponent.builder();
        builder.homeFragmentModule(new HomeFragmentModule(this));
        HomeFragmentConponent conponent = builder.build();
        conponent.in(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHome.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        //设置adapter
        rvHome.setAdapter(new RecycleAdapter(MyApplication.getContext(),initData()));
        //设置分割线
        rvHome.addItemDecoration(new RecycleViewDivider(MyApplication.getContext(),LinearLayoutManager.HORIZONTAL));
        rvHome.addOnScrollListener(listener);

    }

    private List<Bean> initData() {

        List<Bean> mData = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {

            Bean bean = new Bean();
            bean.setText((char) i + "");
            int type = i % 3;
            if (type == 0) {
                bean.setType(0);
            } else if (type == 1) {
                bean.setType(1);
            } else if (type == 2) {
                bean.setType(2);
            }

            mData.add(bean);
        }
        return mData;
    }



    @Override
    public void onResume() {
        super.onResume();

        presenter.getData();
        // 显示滚动条
    }

    private int sumY = 0;
    private float duration = 150.0f;//在0-150之间去改变头部的透明度
    private ArgbEvaluator evaluator = new ArgbEvaluator();
    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

//            System.out.println("recyclerView = [" + recyclerView + "], dx = [" + dx + "], dy = [" + dy + "]");

            sumY += dy;

            // 滚动的总距离相对0-150之间有一个百分比，头部的透明度也是从初始值变动到不透明，通过距离的百分比，得到透明度对应的值
            // 如果小于0那么透明度为初始值，如果大于150为不透明状态

            int bgColor = 0X553190E8;
            if (sumY < 0) {
                bgColor = 0X553190E8;
            } else if (sumY > 150) {
                bgColor = 0XFF3190E8;
            } else {
                bgColor = (int) evaluator.evaluate(sumY / duration, 0X553190E8, 0XFF3190E8);
            }

            llTitleContainer.setBackgroundColor(bgColor);

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void failed(String msg) {
    }

    public void success(HomeInfo info) {

    }



    @OnClick(R.id.home_tv_address)
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.home_tv_address:

                Intent intent = new Intent(getContext(), Map2Activity.class);
                startActivity(intent);

                break;




        }

    }



}
