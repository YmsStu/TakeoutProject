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
import com.itheima.takeout.utils.SMSUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zzh on 2017/3/27.
 */

public class AddAddressActivity extends BaseActivity {

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
    @InjectView(R.id.tv_receipt_address)
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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receipt_address);
        ButterKnife.inject(this);

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

    @OnClick({R.id.ib_back, R.id.ib_delete_address, R.id.ib_delete_phone, R.id.tv_receipt_address, R.id.ib_select_label, R.id.bt_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_delete_address:
                Toast.makeText(this, "  //删除逻辑还没实现", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.ib_delete_phone:
                //置空文本
                etPhone.setText("");
                break;
            case R.id.tv_receipt_address:
                //跳转到选择地址界面
                reAddress();
                break;
            case R.id.ib_select_label:
                //显示一个对话框
                showLableDialog();
                break;
            case R.id.bt_ok:
                //检查数据是否合法
                if(checkData()){

finish();

                }
                break;
        }
    }



    private void reAddress() {
      /*  Intent intent = new Intent(this, AddressLocationActivity.class);
        startActivityForResult(intent,100);*/

        Intent intent = new Intent(this, Map2Activity.class);
        startActivityForResult(intent,100);

    }

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

            }
        });
        builder.show();
    }


    private boolean checkData() {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请填写联系人", Toast.LENGTH_SHORT).show();
            return false;
        }
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请填写手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!SMSUtil.isMobileNO(phone)) {
            Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
/*        String receiptAddress = tvReceiptAddress.getText().toString().trim();
        if (TextUtils.isEmpty(receiptAddress)) {
            Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        String address = etDetailAddress.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        int checkedRadioButtonId = rgSex.getCheckedRadioButtonId();
        if (checkedRadioButtonId != R.id.rb_man && checkedRadioButtonId != R.id.rb_women) {
            //2个不相等，则说明没有选中任意一个
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
            return false;
        }

        String tvLableString = tvLabel.getText().toString();
        if (TextUtils.isEmpty(tvLableString)) {
            Toast.makeText(this, "请输入标签信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
