package com.itheima.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.ui.activity.Map2Activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ruibao on 2017/3/27.
 */

public class MapAdapter extends RecyclerView.Adapter {

    private ArrayList<AddressBean> addressList = new ArrayList<>();
    private Context mContext;
    private MyViewHolder mHolder;
    private List<String> lists = new ArrayList<>();
    private ArrayList<String> mLists = new ArrayList<>();
    ;

    private String title;
    private String data;
    private String datas;



    /*public MapAdapter(Context context, ArrayList<AddressBean> addressList) {
        mContext = context;
        this.addressList = addressList;
    }*/

    public MapAdapter(Context mContext, List<String> lists,ArrayList<String>mLists) {
        this.mContext = mContext;
        this.lists = lists;
        this.mLists=mLists;
    }

    public void setAddressList(ArrayList<AddressBean> addressList) {
        this.addressList = addressList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_receipt_address, parent, false);
        mHolder = new MyViewHolder(view);

        mHolder.setIsRecyclable(true);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        /*
        实际的获取
        final AddressBean addressBean = addressList.get(position);
        title = addressBean.name;
        mHolder.mTvTitle.setText(title);
        mHolder.mTvSnippet.setText(addressBean.receiptAddress);*/

        data = lists.get(position);
        datas = mLists.get(position);

        for (int i = 0; i < 20; i++) {

            mHolder.mTvTitle.setText(data);
            mHolder.mTvSnippet.setText(datas);
        }


        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PreferenceUtils.setString(mContext, "title", title);
                Intent intent = new Intent();
                intent.putExtra("title", datas);
                ((Map2Activity) mContext).setResult(4, intent);
                ((Map2Activity) mContext).finish();

            }
        });
    }


    @Override
    public int getItemCount() {
       /* if (addressList != null) {
            Log.d("MapAdapter", "mPoiItems.size()");
            return addressList.size();
        }*/
        return lists.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv)
        ImageView mIv;
        @InjectView(R.id.tv_title)
        TextView mTvTitle;
        @InjectView(R.id.tv_snippet)
        TextView mTvSnippet;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
