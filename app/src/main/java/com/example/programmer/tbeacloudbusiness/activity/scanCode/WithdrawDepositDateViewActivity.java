package com.example.programmer.tbeacloudbusiness.activity.scanCode;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * 提现详情
 */

public class WithdrawDepositDateViewActivity extends TopActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_withdraw_deposit_view);
        initTopbar("提现详情");
    }
}
