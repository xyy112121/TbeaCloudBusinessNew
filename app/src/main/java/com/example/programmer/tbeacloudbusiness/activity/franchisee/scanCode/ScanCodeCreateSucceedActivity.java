package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by DELL on 2017/7/9.
 */

public class ScanCodeCreateSucceedActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code_succeed);
        initTopbar("生成返利二维码", "查看", this);
        initView();
    }

    private void initView() {
        findViewById(R.id.create_code_succeed_finish_bth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ScanCodeHistoryListActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        String id = getIntent().getStringExtra("id");
        Intent intent = new Intent(mContext, ScanCodeCreateInfoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();

    }
}
