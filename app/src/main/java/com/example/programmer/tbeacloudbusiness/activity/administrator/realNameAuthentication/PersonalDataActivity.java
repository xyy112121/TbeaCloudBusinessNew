package com.example.programmer.tbeacloudbusiness.activity.administrator.realNameAuthentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * 个人资料
 */

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        initTopbar("","保存",this);
    }

    @Override
    public void onClick(View view) {

    }
}
