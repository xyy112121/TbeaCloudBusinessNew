package com.example.programmer.tbeacloudbusiness.activity.my.set.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.user.LoginActivity;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by programmer on 2017/8/18.
 */

public class PwdEditSucceedActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_edit_succeed);
        ButterKnife.bind(this);
        initTopbar("密码设置");
    }

    @OnClick(R.id.binding_finish_login)
    public void onViewClicked() {
        MyApplication.instance.exit();
        ShareConfig.setConfig(PwdEditSucceedActivity.this, Constants.ONLINE, false);
        startActivity(new Intent(mContext, LoginActivity.class));
    }
}
