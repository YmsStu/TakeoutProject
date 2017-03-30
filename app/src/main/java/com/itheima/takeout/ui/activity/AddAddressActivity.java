package com.itheima.takeout.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.takeout.R;
import com.itheima.takeout.model.dao.DBHelper;
import com.itheima.takeout.model.dao.bean.AddressBean;
import com.itheima.takeout.model.dao.bean.UserBean;
import com.itheima.takeout.utils.SMSUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.itheima.takeout.R.id.tv_receipt_address;

/**
 * Created by zzh on 2017/3/27.
 */

public class AddAddressActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_delete_address)
    ImageButton ibDeleteAddress;
    @InjectView(R.id.rl_left)
    TextView rlLeft;
    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.rb_man)
    RadioButton rbMan;
    @InjectView(R.id.rb_women)
    RadioButton rbWomen;
    @InjectView(R.id.rg_sex)
    RadioGroup rgSex;
    @InjectView(R.id.et_phone)
    EditText etPhone;
    @InjectView(R.id.ib_delete_phone)
    ImageButton ibDeletePhone;
    @InjectView(tv_receipt_address)
    TextView tvReceiptAddress;
    @InjectView(R.id.et_detail_address)
    EditText etDetailAddress;
    @InjectView(R.id.tv_label)
    TextView tvLabel;
    @InjectView(R.id.ib_select_label)
    ImageView ibSelectLabel;
    @InjectView(R.id.bt_ok)
    Button btOk;

    //标签要用到的数组
    private String[] addressLabels;
    private int[] bgLabels;
    private AddressBean receiptAddressBean = new AddressBean();
    private static final int REQUEST_ADDRESS = 101;
    private String tvLableString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receipt_address);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int who = bundle.getInt("who");
        if (who == 0) {
            tvTitle.setText("新增地址");
        } else if (who == 1) {
            tvTitle.setText("修改地址");
        }

        initEvent();

        //标签要用到的数组
        addressLabels = new String[]{ "家", "公司", "学校","其他"};
        //家  橙色
        //公司 蓝色
        //学校   绿色
        bgLabels = new int[]{
                Color.parseColor("#fc7251"),//家  橙色
                Color.parseColor("#468ade"),//公司 蓝色
                Color.parseColor("#02c14b"),//学校 绿色
                Color.parseColor("#468ade"),//其他 蓝色
        };

        //给edtiText注册事件监听,让其文本变化的时候,显示x图片
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //当文本有变化后,如果文本内容不是空的,可以显示x号让,用户可以点击删除
                String phone = etPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)){
                    //显示x,提示用户可以删除
                    ibDeletePhone.setVisibility(View.VISIBLE);
                }else{
                    ibDeletePhone.setVisibility(View.GONE);
                }
            }
        });


        //给editText注册焦点变化的事件监听(有焦点+有文本内容的时候,x符号才会出现)
        MyOnFocusChangeListener myOnFocusChangeListener = new MyOnFocusChangeListener();
        etPhone.setOnFocusChangeListener(myOnFocusChangeListener);


    }

    private void initDB() {
        DBHelper dbHelper = DBHelper.getInstance();
        dbHelper.getWritableDatabase();
        UserBean userBean = new UserBean();
        userBean.set_id(1);
        userBean.setName(name);
        userBean.setPhone(phone);

        try {

            Dao<UserBean, Integer> dao1 = dbHelper.getDao(UserBean.class);

            dao1.create(userBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            Dao<AddressBean, Integer> dao2 = dbHelper.getDao(AddressBean.class);

            receiptAddressBean.setUser(userBean);
            dao2.create(receiptAddressBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivity(intent);
    }

    private void initEvent() {
        ibBack.setOnClickListener(this);
        tvReceiptAddress.setOnClickListener(this);
        ibSelectLabel.setOnClickListener(this);
        btOk.setOnClickListener(this);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbMan.getId() == checkedId) {
                    sex = rbMan.getText().toString();
                } else if (rbWomen.getId() == checkedId) {
                    sex = rbWomen.getText().toString();
                }
            }
        });
    }



    class MyOnFocusChangeListener implements View.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v.getId() == R.id.et_phone){
                //v就是注册此焦点变化监听的控件
                //hasFocus 在回调此方法的时候,是否获取了焦点  true 有焦点  false没有焦点
                String phone = etPhone.getText().toString();
                if (!TextUtils.isEmpty(phone) && hasFocus){
                    ibDeletePhone.setVisibility(View.VISIBLE);
                }else{
                    ibDeletePhone.setVisibility(View.GONE);
                }
            }

        }
    }

    @OnClick({R.id.ib_back, R.id.ib_delete_address, R.id.ib_delete_phone, tv_receipt_address, R.id.ib_select_label, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete_address:
                Toast.makeText(this, "  //删除地址并刷新列表", Toast.LENGTH_SHORT).show();
                //deleteAddress();
                finish();
                break;
            case R.id.ib_delete_phone:
                //置空文本
                etPhone.setText("");
                break;
            case tv_receipt_address:
                //跳转到选择地址界面
                reAddress();
                break;
            case R.id.ib_select_label:
                //显示一个对话框
                showLableDialog();
                break;
            case R.id.bt_ok:
                //检查数据是否合法
                boolean getData = checkData();
                if(getData){
                    initDB();
                }
                break;
        }
    }

    private String sex;
    private String name;
    private String phone;
    private String address;
    private String detailAddress;
    private String receiptAddress;

    //跳转到选择地址的界面
    private void reAddress() {
        Intent intent = new Intent(this, Map2Activity.class);
        startActivityForResult(intent,1);

    }

    //自定义的标签
    private void showLableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择标签");
        builder.setItems(addressLabels, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //which就是选中条目的索引值
                tvLabel.setText(addressLabels[which]);
                tvLabel.setBackgroundColor(bgLabels[which]);
                tvLableString = tvLabel.getText().toString();

            }
        });
        builder.show();
    }

    //对于输入信息合法性的校验
    private boolean checkData() {
        name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        receiptAddressBean.setName(name);

        receiptAddressBean.setSex(sex);

        phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!SMSUtil.isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }

        receiptAddressBean.setPhone(phone);

        receiptAddress = tvReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show();
            return false;
        }

        address = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        receiptAddressBean.setDetailAddress(receiptAddress+address);


        if (TextUtils.isEmpty(tvLableString)) {
            Toast.makeText(this, "请输入标签信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        receiptAddressBean.setLabel(tvLableString);

        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        receiptAddress = "昌平区北七家镇宏福科技园修正大厦";
        tvReceiptAddress.setText(receiptAddress);
        receiptAddressBean.setReceiptAddress(receiptAddress);
    }
}
