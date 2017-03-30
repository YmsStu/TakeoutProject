package com.itheima.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.ui.activity.AddressListActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zzh on 2017/3/29.
 */

public class AddressListAdapter extends RecyclerView.Adapter {

    private static  int RESULT_CODE = 100;
    private Context ctx;
    private  String[] addressLabels;
    private List<AddressBean> mList;
    private  int[] bgLabels;



    public AddressListAdapter(Context ctx) {
        this.ctx = ctx;

        addressLabels = new String[]{ "家", "公司", "学校"};
        //家  橙色
        //公司 蓝色
        //学校   绿色
        bgLabels = new int[]{
                Color.parseColor("#fc7251"),//家  橙色
                Color.parseColor("#468ade"),//公司 蓝色
                Color.parseColor("#02c14b"),//学校   绿色
        };


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(ctx, R.layout.item_receipt_address, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddressBean receiptAddressBean = mList.get(position);
        ((ViewHolder)holder).tvName.setText(receiptAddressBean.getName());
        ((ViewHolder)holder).tvSex.setText(receiptAddressBean.getSex());
        if (!TextUtils.isEmpty(receiptAddressBean.getPhone())){
            ((ViewHolder)holder).tvPhone.setText(receiptAddressBean.getPhone());
        } else{
            ((ViewHolder)holder).tvPhone.setText(receiptAddressBean.getPhone());
        }
        ((ViewHolder)holder).tvAddress.setText(receiptAddressBean.getReceiptAddress()+receiptAddressBean.getDetailAddress());

        String label = receiptAddressBean.getLabel();
        int index = getIndex(label);
        ((ViewHolder)holder).tvLabel.setVisibility(View.VISIBLE);
        ((ViewHolder)holder).tvLabel.setText(label);
        ((ViewHolder)holder).tvLabel.setBackgroundColor(bgLabels[index]);
        ((ViewHolder)holder).ivEdit.setVisibility(View.VISIBLE);
        ((ViewHolder)holder).setPosition(position);


    }

    private int getIndex(String label) {
        int index = 0;
        for (int i = 0; i < addressLabels.length; i++) {
            if (label.equals(addressLabels[i]) ){
                //一致,则设置背景,展示即可
                index = i;
                break;
            }
        }
        return index;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
        @InjectView(R.id.iv_edit)
        ImageView ivEdit;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }



        public void setPosition(int position) {
            this.position = position;
        }
        @OnClick({R.id.iv_edit})
        public void onClick(View view){
            switch (view.getId()){
                case R.id.iv_edit:
                    Intent intent = new Intent(ctx, AddressListActivity.class);
                    AddressBean receiptAddressBean = mList.get(position);
                    intent.putExtra("address", (Serializable) receiptAddressBean);
                    ctx.startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() > 0) {
            return mList.size();
        }
        return 0;
    }

    public void setData(List<AddressBean> addressBeanList) {
        this.mList = addressBeanList;
        notifyDataSetChanged();
    }
}
