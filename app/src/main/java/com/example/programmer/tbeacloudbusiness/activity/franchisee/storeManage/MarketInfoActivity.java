package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;

/**
 * 销售详情
 */

public class MarketInfoActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info);
        initTopbar("销售详情","商品编辑",this);
        initView();
    }

    private void initView(){
        ((PublishTextRowView)findViewById(R.id.market_info_be_sold)).setValueText("88");
        (findViewById(R.id.market_info_be_sold)).setOnClickListener(this);
        (findViewById(R.id.market_info_be_rate)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.market_info_be_sold:
                Intent intent = new Intent(mContext,MarketInfoBeSoldActivity.class);
                startActivity(intent);
                break;
            case R.id.market_info_be_rate:
                 intent = new Intent(mContext,MarketInfoBeRateActivity.class);
                startActivity(intent);
                break;
        }

    }
}
