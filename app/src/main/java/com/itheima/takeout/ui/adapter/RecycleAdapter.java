package com.itheima.takeout.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.takeout.R;

import java.util.List;

/**
 * Created by JAVA on 2017/3/27.
 * 描述:
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    /**
     * RecycleViewAdapter 要素点：
     * 1，ViewHolder必须继承RecyclerView.ViewHolder
     * 2，RecycleView.Adapter的泛型为自定义ViewHolder
     */


    private List<Bean> mData;   //数据
    private Context mContext;

    /**
     * 点击监听
     * @param v
     */
    @Override
    public void onClick(View v) {

    }


    public enum Item_Type {     //定义类别,也可以直接定义常量
        RECYCLEVIEW_ITEM_TYPE_1,
        RECYCLEVIEW_ITEM_TYPE_2,
        RECYCLEVIEW_ITEM_TYPE_3
    }

    //初始化适配器
    public RecycleAdapter(Context context, List<Bean> mData) {
        this.mData = mData;
        this.mContext = context;
    }


    /**
     * 创建ViewHolder
     * 根据不同ItemView的类型返回不同ViewHolder
     *
     * @param parent
     * @param viewType :不同ItemView的类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据不同ItemView的类型返回不同ViewHolder
        if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_1.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_slider, null);
            ViewHolderA viewHolder = new ViewHolderA(view);

            return viewHolder;

        } else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_2.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_head_category, null);
            ViewHolderB viewHolder = new ViewHolderB(view);

            return viewHolder;
        } else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_3.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_nearthebusiness, null);
            ViewHolderC viewHolder = new ViewHolderC(view);

            //给布局设置点击监听
            view.setOnClickListener(this);

            return viewHolder;
        }

        return null;
    }


    /**
     * 将数据与item视图进行绑定
     * 绑定数据：可以直接拿到已经绑定控件的Viewholder对象
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderA) {

        } else if (holder instanceof ViewHolderB) {
            ((ViewHolderB) holder).imageView1.setImageResource(R.drawable.a);
            ((ViewHolderB) holder).imageView2.setImageResource(R.drawable.app_icon);
            ((ViewHolderB) holder).text1.setText("美食");
        } else if (holder instanceof ViewHolderC) {
            ((ViewHolderC) holder).text.setText(mData.get(position).getText() + "------样式三");
            //((ViewHolderC) holder).text.setText("附近商家");
        }

    }


    /**
     * 返回值赋值给onCreateViewHolder的参数 viewType
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (mData.get(position).getType() == 0) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_1.ordinal();
        } else if (mData.get(position).getType() == 1) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_2.ordinal();
        } else if (mData.get(position).getType() == 2) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_3.ordinal();
        }
        return -1;
    }

    /**
     * 获取数据的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {

        return mData.size();
    }


    class ViewHolderA extends RecyclerView.ViewHolder {

        public ViewHolderA(View itemView) {
            super(itemView);
        }
    }


    class ViewHolderB extends RecyclerView.ViewHolder {
        public ImageView imageView1;
        public ImageView imageView2;
        public TextView text1;
        public TextView text2;

        public ViewHolderB(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.top_iv);
            imageView2 = (ImageView) itemView.findViewById(R.id.bottom_iv);
            text1 = (TextView) itemView.findViewById(R.id.top_tv);
            text2 = (TextView) itemView.findViewById(R.id.bottom_tv);
        }
    }

    class ViewHolderC extends RecyclerView.ViewHolder {

        public TextView text;

        public ViewHolderC(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_nearthebusiness);
        }
    }
}
