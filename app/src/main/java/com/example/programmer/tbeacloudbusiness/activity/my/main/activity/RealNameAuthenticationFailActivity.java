package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.CompletionDataActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证失败
 */

public class RealNameAuthenticationFailActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_authentication_fail);
        ButterKnife.bind(this);
        initTopbar("审核拒绝");
    }

    @OnClick(R.id.identification_finish)
    public void onViewClicked() {
        startActivity(new Intent(mContext, CompletionDataActivity.class));
    }
}
