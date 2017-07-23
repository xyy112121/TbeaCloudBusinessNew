package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 商品添加
 */

public class StoreCommodityManageAddActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_commodity_manage_add);
        initTopbar("商品添加","保存",this);
    }

    @Override
    public void onClick(View view) {

    }
}
