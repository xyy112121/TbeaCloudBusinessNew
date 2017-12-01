package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomOptionPicker;


import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 订单管理--发货
 */

public class OrderManageDeliverGoodsActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage_deliver_goods);
        initTopbar("发货", "提交", this);
        initView();
    }

    private void initView(){
        findViewById(R.id.order_manage_deliver_goods_logistic_company).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] types = new String[]{"圆通快递","顺丰快递","中通快递","远成物流"};
                CustomOptionPicker optionPicker = new CustomOptionPicker((Activity) mContext,"物流公司",types);
                optionPicker.setTextSize(14);
                optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        showMessage(option);
                    }
                });
                optionPicker.show();
                optionPicker.setGravity(Gravity.CENTER);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
