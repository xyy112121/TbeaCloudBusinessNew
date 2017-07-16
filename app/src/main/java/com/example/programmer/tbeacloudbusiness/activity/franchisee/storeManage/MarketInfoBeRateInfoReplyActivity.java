package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 评价详情 - 回复
 */

public class MarketInfoBeRateInfoReplyActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info_be_rate_info);
        initTopbar("评价回复","发表",this);
    }

    @Override
    public void onClick(View view) {

    }
}
