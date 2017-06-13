package com.example.programmer.tbeacloudbusiness.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomAddressPicker;
import com.example.programmer.tbeacloudbusiness.utils.AssetsUtils;

import java.util.ArrayList;

import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.popup.BottomPopup;

/**
 * Created by programmer on 2017/6/5.
 */

public class LoginActivity extends FragmentActivity {
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        listener();
    }

    private void listener(){
        findViewById(R.id.login_register_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}
