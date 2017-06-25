package com.example.programmer.tbeacloudbusiness.activity.my.set;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * Created by programmer on 2017/6/24.
 */

public class SetBackgroundActivity extends TopActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_background);
        initTopbar("设置背景","删除",this);
    }

    @Override
    public void onClick(View v) {

    }
}
