package com.example.programmer.tbeacloudbusiness.activity.scanCode;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * 扫码返利详情
 */

public class ViewActivity extends TopActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_rebate_view);
        initTopbar("扫码详情");
    }
}
