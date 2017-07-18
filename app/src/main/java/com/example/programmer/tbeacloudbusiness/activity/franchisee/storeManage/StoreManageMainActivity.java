package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 店铺管理首页
 */

public class StoreManageMainActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manage_main);
        initTopbar("店铺管理");
        initView();
    }

    private void initView(){
        findViewById(R.id.shop_manage_layout).setOnClickListener(this);
        findViewById(R.id.order_manage_layout).setOnClickListener(this);
        findViewById(R.id.commodity_manage_layout).setOnClickListener(this);
        findViewById(R.id.shop_dynamic_state_manage_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_manage_layout:
                //店铺设置
                Intent intent = new Intent(mContext,StoreSetActtivity.class);
                startActivity(intent);
                break;
            case R.id.order_manage_layout:
                //订单管理
                 intent = new Intent(mContext,OrderManageDeliverGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.commodity_manage_layout:
                //商品管理
                intent = new Intent(mContext,StoreCommodityManageListActivity.class);
                startActivity(intent);
                break;
            case R.id.shop_dynamic_state_manage_layout:
                //店铺动态
                intent = new Intent(mContext,ShopDynamicListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
