package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by programmer on 2017/7/13.
 */

public class StoreSetActtivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_set);
        initTopbar("店铺设置");
        initView();
    }

    private void initView() {
        findViewById(R.id.store_set_store_code).setOnClickListener(this);
        findViewById(R.id.store_set_rotate_ad).setOnClickListener(this);
        findViewById(R.id.store_set_store_introduce).setOnClickListener(this);
        findViewById(R.id.store_set_store_specifications_and_models).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.store_set_rotate_ad:
                Intent intent = new Intent(mContext, RotateADListActivity.class);
                startActivity(intent);
                break;
            case R.id.store_set_store_code:
                intent = new Intent(mContext, StoreCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.store_set_store_introduce:
                intent = new Intent(mContext, StoreIntroduceActivity.class);
                startActivity(intent);
                break;
            case R.id.store_set_store_specifications_and_models:
//                intent = new Intent(mContext, SpecificationsAndModelsListActivity.class);
//                startActivity(intent);//
                intent = new Intent(mContext, OrderManageDeliverGoodsActivity.class);
                startActivity(intent);
                //kkk
                break;
        }
    }
}
