package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.activity.DbScanCodeMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.ScanCodeMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.WithdrawDepositDateInfoActivity;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;

/**
 * Created by programmer on 2017/7/13.
 */

public class ScanCodePaySucceedActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancode_pay_succeed);
        findViewById(R.id.top_left).setVisibility(View.GONE);
        initTopbar("支付成功", "查看", this);
        initView();
    }

    private void initView() {
        setTextViewValue(R.id.sacn_code_pay_succeed_money, getIntent().getStringExtra("money"));

        findViewById(R.id.sacn_code_pay_succee_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("distributor".equals(ShareConfig.getConfigString(mContext, Constants.USERTYPE, ""))) {//分销商
                    startActivity(new Intent(mContext, DbScanCodeMainListActivity.class));
                } else {
                    startActivity(new Intent(mContext, ScanCodeMainListActivity.class));
                }
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = getIntent();
        intent.setClass(mContext, WithdrawDepositDateInfoActivity.class);
        startActivity(intent);
        finish();
    }
}
