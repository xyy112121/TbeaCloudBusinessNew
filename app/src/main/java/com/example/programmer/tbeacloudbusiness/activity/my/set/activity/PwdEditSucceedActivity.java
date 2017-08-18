package com.example.programmer.tbeacloudbusiness.activity.my.set.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by programmer on 2017/8/18.
 */

public class PwdEditSucceedActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_edit_succeed);
        initTopbar("密码设置");
    }
}
