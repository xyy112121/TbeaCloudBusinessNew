package com.example.programmer.tbeacloudbusiness.activity.my.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.PwdEditActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.AddressEditListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.EditBindingPhoneActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.GeneralActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.SetBackgroundActivity;


/**
 * 设置
 */

public class SetActivity  extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initTopbar("设置");
        listener();
    }

    private void listener(){
        findViewById(R.id.set_phone).setOnClickListener(this);
        findViewById(R.id.set_edit_pwd).setOnClickListener(this);
        findViewById(R.id.set_address).setOnClickListener(this);
        findViewById(R.id.set_background).setOnClickListener(this);
        findViewById(R.id.set_general).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_phone:
                startActivity(new Intent(mContext,EditBindingPhoneActivity.class));
                break;
            case R.id.set_edit_pwd:
                startActivity(new Intent(mContext,PwdEditActivity.class));
                break;
            case R.id.set_address:
                startActivity(new Intent(mContext,AddressEditListActivity.class));
                break;
            case R.id.set_background:
                startActivity(new Intent(mContext,SetBackgroundActivity.class));
                break;
            case R.id.set_general:
                startActivity(new Intent(mContext,GeneralActivity.class));
                break;
        }
    }
}
