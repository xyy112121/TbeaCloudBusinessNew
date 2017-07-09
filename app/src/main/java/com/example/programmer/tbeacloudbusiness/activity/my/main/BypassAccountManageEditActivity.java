package com.example.programmer.tbeacloudbusiness.activity.my.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 子账号管理增加
 */

public class BypassAccountManageEditActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bypass_account_manage_edit);
        initTopbar("子账号管理", "删除", this);
        listener();
    }

    private void listener(){
        findViewById(R.id.authorization_functions).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.authorization_functions:
               startActivity(new Intent(mContext,ByPassAccountAuthorizationFunctionsActivity.class));
               break;
       }
    }
}
