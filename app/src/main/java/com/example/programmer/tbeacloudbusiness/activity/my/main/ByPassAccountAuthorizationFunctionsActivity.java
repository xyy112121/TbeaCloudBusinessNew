package com.example.programmer.tbeacloudbusiness.activity.my.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * 授权功能
 */

public class ByPassAccountAuthorizationFunctionsActivity extends TopActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bypass_account_authorization_functions);
        initTopbar("授权功能");
    }
}
