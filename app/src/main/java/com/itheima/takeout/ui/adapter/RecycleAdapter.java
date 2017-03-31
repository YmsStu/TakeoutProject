package com.itheima.takeout.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.HomeInfo;
import com.itheima.takeout.ui.activity.SettleCenterActivity;
import com.squareup.picasso.Picasso;

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

    private Context mContext;
    private HomeInfo mHomeInfo;
    public LinearLayout mCategory;
    private LinearLayout mllcatetory_container;


    /**
     * 点击监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, SettleCenterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }


    public enum Item_Type {     //定义类别,也可以直接定义常量
        RECYCLEVIEW_ITEM_TYPE_1,    //附近商家
        RECYCLEVIEW_ITEM_TYPE_2,    //大家都在找
        RECYCLEVIEW_ITEM_TYPE_3     //头
    }

    //初始化适配器
    public RecycleAdapter(Context context, HomeInfo mHomeInfo) {
        this.mContext = context;
        this.mHomeInfo = mHomeInfo;

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
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_seller, null); //附近商家
            ViewHolderB viewHolder = new ViewHolderB(view);


            //给布局设置点击监听
            view.setOnClickListener(this);

            return viewHolder;


        } else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_2.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_division, null);    //大家都在找
            ViewHolderC viewHolder = new ViewHolderC(view);

            return viewHolder;
        } else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_3.ordinal()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_title, null);   //头
            ViewHolderA viewHolder = new ViewHolderA(view);
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

        // TODO: 2017/3/28 待修改
        if (holder instanceof ViewHolderA) {
            //添加轮播图资源
            ((ViewHolderA) holder).mSlider.removeAllSliders();
            for (int i = 0; i < mHomeInfo.head.promotionList.size(); i++) {
                TextSliderView mTextSliderView = new TextSliderView(MyApplication.getContext());
                String replace = mHomeInfo.head.promotionList.get(i).pic.replace("172.16.0.116", "10.0.2.2");
                mTextSliderView.image(replace);
                ((ViewHolderA) holder).mSlider.addSlider(mTextSliderView);    //添加图
            }

            //添加商品分类资源
            mllcatetory_container.removeAllViews();
            for (int j = 0; j < mHomeInfo.head.categorieList.size(); j = j + 2) {

                mCategory = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_home_head_category, mllcatetory_container, false);
                mllcatetory_container.addView(mCategory);

                String replace = mHomeInfo.head.categorieList.get(j).pic.replace("172.16.0.116", "10.0.2.2");
                Picasso.with(mContext).load(replace).into((ImageView) mCategory.findViewById(R.id.top_iv));
                ((TextView) mCategory.findViewById(R.id.top_tv)).setText(mHomeInfo.head.categorieList.get(j).name);
                replace = mHomeInfo.head.categorieList.get(j + 1).pic.replace("172.16.0.116", "10.0.2.2");
                Picasso.with(mContext).load(replace).into((ImageView) mCategory.findViewById(R.id.bottom_iv));
                ((TextView) mCategory.findViewById(R.id.bottom_tv)).setText(mHomeInfo.head.categorieList.get(j + 1).name);

            }


        } else if (holder instanceof ViewHolderB) {
            for (int i = 0; i <mHomeInfo.body.size() ; i++) {
                if(mHomeInfo.body.get(position-1).type == 1) {
                    i++;
                } else {
                    ((ViewHolderB) holder).text.setText(mHomeInfo.body.get(position-1).seller.name);
                }

            }

        } else if (holder instanceof ViewHolderC) {
/*            for (int i = 0; i < mHomeInfo.body.size(); i++) {
                ((ViewHolderC) holder).text0.setText("111");
            }*/
        }

    }


    /**
     * 返回值赋值给onCreateViewHolder的参数 viewType
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //判断条目的不同类型
        if (position == 0) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_3.ordinal();

        } else if (mHomeInfo.body.get(position - 1).type == 0) {    //如果type = 0 显示附近
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_1.ordinal();
        } else if (mHomeInfo.body.get(position - 1).type == 1) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_2.ordinal();     //显示大家都在找
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

        return mHomeInfo.body.size() + 1;
    }


    /**
     * 头
     */
    class ViewHolderA extends RecyclerView.ViewHolder {
        private SliderLayout mSlider;

        public ViewHolderA(View itemView) {
            super(itemView);
            mSlider = (SliderLayout) itemView.findViewById(R.id.slider);
            mllcatetory_container = (LinearLayout) itemView.findViewById(R.id.catetory_container);
        }
    }


    class ViewHolderB extends RecyclerView.ViewHolder {

        public TextView text;

        public ViewHolderB(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }

    class ViewHolderC extends RecyclerView.ViewHolder {

        public TextView text0;
        public TextView text1;
        public TextView text2;
        public TextView text3;
        public TextView text4;
        public TextView text5;

        public ViewHolderC(View itemView) {
            super(itemView);
            text0 = (TextView) itemView.findViewById(R.id.tv1_division);
            text1 = (TextView) itemView.findViewById(R.id.tv1_division);
            text2 = (TextView) itemView.findViewById(R.id.tv1_division);
            text3 = (TextView) itemView.findViewById(R.id.tv1_division);
            text4 = (TextView) itemView.findViewById(R.id.tv1_division);
            text5 = (TextView) itemView.findViewById(R.id.tv1_division);
        }

    }
}
