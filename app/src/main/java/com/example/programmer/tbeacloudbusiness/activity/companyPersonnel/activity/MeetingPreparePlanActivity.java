package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by programmer on 2017/8/2.
 */

public class MeetingPreparePlanActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare_plan);
        initTopbar("会议安排","保存",this);
    }

    @Override
    public void onClick(View v) {

    }
}
