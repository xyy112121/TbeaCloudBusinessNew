package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

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

    private void initView() {
        findViewById(R.id.sacn_code_input_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = ((TextView) findViewById(R.id.scan_code_input_text)).getText() + "";
                if ("".equals(result)) {
                    ToastUtil.showMessage("请输入扫描码！");
                    return;
                }

                if (result.length() < 5) {
                    ToastUtil.showMessage("扫描码必须大于5位！");
                    return;
                }

                provingScanCode("tbscrfl_" + result);
            }
        });
    }

    public void provingScanCode(final String result) {
//        if(result.indexOf("tbscrfl") != -1){
        Intent intent = new Intent(mContext, ScanCodePayConfirmActivity.class);
        intent.putExtra("code", result);
        startActivity(intent);
//        }else {
//            //二维码无效
//            Intent intent = new Intent(mContext,CodeBraceActivity.class);
//            startActivity(intent);
//        }
    }
}
