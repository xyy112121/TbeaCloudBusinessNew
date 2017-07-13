package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;

/**
 * 支付确认
 */

public class ScanCodePayConfirmActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancode_pay_confirm);
        initTopbar("支付确认");
        initView();
    }

    private void initView(){
        findViewById(R.id.scancode_pay_confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    /**
     * 显示警示框
     */
    private void showAlert() {
        View parentLayout = findViewById(R.id.parentLayout);
        final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header,
                R.layout.activity_scancode_pay_confirm_tip, "确认提示", "支付用户：测试","支付金额：￥555.0", "确认");
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                Intent intent = new Intent(mContext,ScanCodePaySucceedActivity.class);
                startActivity(intent);
            }
        });
    }
}
