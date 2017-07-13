package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by programmer on 2017/7/13.
 */

public class ScanCodePaySucceedActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancode_pay_succeed);
        findViewById(R.id.top_left).setVisibility(View.GONE);
        initTopbar("支付成功","查看",this);
    }

    @Override
    public void onClick(View v) {

    }
}
