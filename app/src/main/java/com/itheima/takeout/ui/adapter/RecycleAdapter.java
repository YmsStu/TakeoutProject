package com.itheima.takeout.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.itheima.takeout.MyApplication;
import com.itheima.takeout.R;
import com.itheima.takeout.model.net.bean.HomeInfo;

import java.util.HashMap;

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


    /**
     * 点击监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

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
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_title, null);   //轮播图
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

            //((ViewHolderA) holder).imageViewSlider.setImageResource(R.drawable.a);
//            ((ViewHolderA) holder).mTextSliderView.image(R.drawable.app_icon);

        } else if (holder instanceof ViewHolderB) {

        } else if (holder instanceof ViewHolderC) {
            //((ViewHolderC) holder).text.setText(mData.get(position).getText() + "------样式三");
            //((ViewHolderC) holder).text.setText("附近商家");
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
     * 轮播图
     */
    class ViewHolderA extends RecyclerView.ViewHolder {
        public SliderLayout mSlider;

        public ViewHolderA(View itemView) {
            super(itemView);
            mSlider = (SliderLayout) itemView.findViewById(R.id.slider);

            HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
            file_maps.put("id1", R.drawable.a);  //假数据
            file_maps.put("id2", R.drawable.address_add_btn_icon);
            file_maps.put("id3", R.drawable.app_icon);

            for (String name : file_maps.keySet()) {
                TextSliderView mTextSliderView = new TextSliderView(MyApplication.getContext());
                mTextSliderView.image(file_maps.get(name));
                mSlider.addSlider(mTextSliderView);    //添加图
            }


        }
    }


    class ViewHolderB extends RecyclerView.ViewHolder {
/*        public ImageView imageView1;
        public ImageView imageView2;
        public TextView text1;
        public TextView text2;*/

        public ViewHolderB(View itemView) {
            super(itemView);
/*            imageView1 = (ImageView) itemView.findViewById(R.id.top_iv);
            imageView2 = (ImageView) itemView.findViewById(R.id.bottom_iv);
            text1 = (TextView) itemView.findViewById(R.id.top_tv);
            text2 = (TextView) itemView.findViewById(R.id.bottom_tv);*/
        }
    }

    class ViewHolderC extends RecyclerView.ViewHolder {

        public TextView text;

        public ViewHolderC(View itemView) {
            super(itemView);

        }
    }
}
