package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 轮换广告操作成功
 */

public class RotateADEditSucceedActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_ad_edit_succeed);
        initTopbar("轮换广告");
    }
}
