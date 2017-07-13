package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 编码输入
 */

public class ScanCodeInputActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacn_code_input);
        initTopbar("输入编码");
        initView();
    }

    private void initView(){
        findViewById(R.id.sacn_code_input_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ScanCodePayConfirmActivity.class);
                startActivity(intent);
            }
        });
    }
}
