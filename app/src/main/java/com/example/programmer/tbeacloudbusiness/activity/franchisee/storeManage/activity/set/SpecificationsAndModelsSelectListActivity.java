package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 规格型号选择
 */

public class SpecificationsAndModelsSelectListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code_select);
        initTopbar("添加规格型号");
        findViewById(R.id.create_code_select_title).setVisibility(View.GONE);
        initView();
    }

    private void initView() {

        findViewById(R.id.create_code_select_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SpecificationsAndModelsListActivity.class);
                intent.putExtra("title", "型号");
                intent.putExtra("method", "TBEAYUN001002001000");
                intent.putExtra("methodName", "TBEAYUN001002001001");
                startActivity(intent);

//                Intent intent = new Intent(mContext, SpecificationsAndModelsEditActivity.class);
//                intent.putExtra("title", "型号");
//                intent.putExtra("methodName", "TBEAYUN001002001001");
//                startActivity(intent);
            }
        });

        findViewById(R.id.create_code_select_norms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SpecificationsAndModelsListActivity.class);
                intent.putExtra("title", "规格");
                intent.putExtra("method", "TBEAYUN001002002000");
                intent.putExtra("methodName", "TBEAYUN001002002001");
                startActivity(intent);

//                Intent intent = new Intent(mContext, SpecificationsAndModelsEditActivity.class);
//                intent.putExtra("title", "规格");
//                intent.putExtra("methodName", "TBEAYUN001002002001");
//                startActivity(intent);
            }
        });

        findViewById(R.id.top_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}