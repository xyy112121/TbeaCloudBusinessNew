package com.example.programmer.tbeacloudbusiness.activity.my.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 *实名认证—水电工
 */

public class RealNameAuthenticationPlumberActivity extends TopActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_authentication_plumber);
        initTopbar("实名认证");
    }
}
