package com.example.programmer.tbeacloudbusiness.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by programmer on 2017/6/7.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    private View parentLayout;
    List<String> userTypeList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        parentLayout = findViewById(R.id.register_layout);
        userTypeList.add("分销商");
        userTypeList.add("其他商家");
        userTypeList.add("物流人员");
        mContext = this;
        listener();
    }

    private void listener(){
        findViewById(R.id.register_btn).setOnClickListener(this);
        findViewById(R.id.register_user_type).setOnClickListener(this);//用户类型选择
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                Intent intent = new Intent(mContext,RegisterSuccessActivity.class);
                startActivity(intent);
                break;
            case R.id.register_user_type:
                final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
                popWindow1.init(parentLayout,R.layout.pop_window_header,R.layout.pop_window_tv,userTypeList);
                popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
                    @Override
                    public void onItemClick(String text) {


                    }
                });
                break;
        }

    }

}
