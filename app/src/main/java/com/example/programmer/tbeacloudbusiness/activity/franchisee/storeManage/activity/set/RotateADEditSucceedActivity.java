package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 轮换广告操作成功
 */

public class RotateADEditSucceedActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_ad_edit_succeed);
        ButterKnife.bind(this);
        initTopbar("轮换广告");
    }

    @OnClick(R.id.rotate_ad_edit_succeed_btn)
    public void onViewClicked() {
        startActivity(new Intent(mContext, RotateADListActivity.class));
        finish();
    }
}
