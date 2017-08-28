package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 店铺介绍
 */

public class StoreIntroduceActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_introduce);
        initTopbar("店铺介绍","保存",this);
    }

    @Override
    public void onClick(View v) {

    }
}
