package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 *实名认证—商家分销商
 */

public class RealNameAuthenticationDistributorActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_authentication_distributor);
        initTopbar("实名认证");
    }
}