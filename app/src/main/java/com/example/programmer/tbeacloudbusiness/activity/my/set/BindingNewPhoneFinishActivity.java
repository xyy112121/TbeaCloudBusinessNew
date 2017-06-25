package com.example.programmer.tbeacloudbusiness.activity.my.set;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * 绑定新号码成功
 */

public class BindingNewPhoneFinishActivity extends TopActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_new_phone_finish);
        initTopbar("更换手机号码");
    }
}
