package com.itheima.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.itheima.takeout.R;
import com.itheima.takeout.ui.activity.Map2Activity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by HASEE on 2017/1/16.
 */
public class NearByAdapter extends RecyclerView.Adapter {
    private Context ctx;
    private List<PoiItem> data;

    public NearByAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(ctx, R.layout.item_select_receipt_address, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tvTitle.setText(data.get(position).getTitle());
        ((ViewHolder) holder).tvSnippet.setText(data.get(position).getSnippet());

        ((ViewHolder) holder).setPosition(position);
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    public void setData(List<PoiItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.iv)
        ImageView iv;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_snippet)
        TextView tvSnippet;
        private int position;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PoiItem poiItem = data.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("title",poiItem.getTitle());
                    intent.putExtra("snippet",poiItem.getSnippet());
                    ((Map2Activity)ctx).setResult(101,intent);
                    ((Map2Activity)ctx).finish();
                }
            });
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}
