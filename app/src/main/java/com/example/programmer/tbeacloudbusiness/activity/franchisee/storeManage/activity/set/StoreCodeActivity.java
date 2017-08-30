package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 店铺二维码
 */

public class StoreCodeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_code);
        initTopbar("店铺二维码");
    }
}
