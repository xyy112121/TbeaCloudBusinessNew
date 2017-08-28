package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 轮换广告修改
 */

public class RotateADEditActivity extends BaseActivity implements View.OnClickListener {
    private String mFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_ad_edit);
        mFlag = getIntent().getStringExtra("flag");


        initTopbar("轮换广告", "保存", this);
        initView();
    }

    private void initView() {
        findViewById(R.id.rotate_ad_edit_link_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RotateADLinkPageListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
