package com.itheima.takeout.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.DBHelper;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.itheima.takeout.R.id.ll_add_address;

/**
 * Created by zzh on 2017/3/27.
 */

public class AddressListActivity extends BaseActivity implements View.OnClickListener{


    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.rv_receipt_address)
    RecyclerView rvReceiptAddress;
    @InjectView(ll_add_address)
    LinearLayout llAddAddress;

    private List<AddressBean> addressBeanList = new ArrayList<>();
    private UserBean userBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_address);
        ButterKnife.inject(this);

        initData();
        initEvent();

    }

    private void initData() {
        DBHelper dbHelper = DBHelper.getInstance();
        try {
            Dao<UserBean, Integer> dao = dbHelper.getDao(UserBean.class);
            userBean = dao.queryForId(1);
            if (userBean == null) {
                return;
            }
            for (AddressBean bean : userBean.getAddressList()) {
                addressBeanList.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvReceiptAddress.setLayoutManager(manager);
        rvReceiptAddress.setAdapter(new MyAdapter());
    }


    private void initEvent() {
        ibBack.setOnClickListener(this);
        llAddAddress.setOnClickListener(this);
    }

    @OnClick({R.id.ib_back, ll_add_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case ll_add_address:
                Intent intent = new Intent(this, AddAddressActivity.class);
                intent.putExtra("who", 0);
                startActivity(intent);
                finish();
                break;
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AddressListActivity.class);
        context.startActivity(intent);
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_receipt_address, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            final AddressBean addressBean = addressBeanList.get(position);
            holder.tvName.setText(addressBean.name);
            holder.tvSex.setText(addressBean.sex);
            holder.tvPhone.setText(addressBean.phone);
            holder.tvLabel.setText(addressBean.label);
            holder.tvAddress.setText(addressBean.receiptAddress + addressBean.detailAddress);
            holder.ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                    intent.putExtra("who", 1);
                    startActivity(intent);
                    finish();
                }
            });

       /*
         跳转到订单页面
         holder.ll_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),Map2Activity.class);

                    intent.putExtra("addressBean",addressBean);

                    startActivity(intent);
                    finish();
                }
            });*/
        }

        @Override
        public int getItemCount() {
            return addressBeanList == null ? 0 : addressBeanList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_edit;
            TextView tvName;
            TextView tvSex;
            TextView tvPhone;
            TextView tvLabel;
            TextView tvAddress;
            ImageView ivEdit;


            public MyViewHolder(View itemView) {
                super(itemView);
                ll_edit = (LinearLayout) itemView.findViewById(R.id.ll_edit);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvSex = (TextView) itemView.findViewById(R.id.tv_sex);
                tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
                tvLabel = (TextView) itemView.findViewById(R.id.tv_label);
                tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
                ivEdit = (ImageView) itemView.findViewById(R.id.iv_edit);
            }
        }
    }

}
