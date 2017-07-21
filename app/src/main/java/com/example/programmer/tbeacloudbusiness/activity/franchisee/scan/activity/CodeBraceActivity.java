package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 二维码无效
 */

public class CodeBraceActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacncode_brace);
        initTopbar("提示");
    }
}
