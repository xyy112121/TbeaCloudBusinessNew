package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by programmer on 2017/8/2.
 */

public class MeetingPreparePlanActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare_plan);
        initTopbar("会议安排", "保存", this);
    }

    @Override
    public void onClick(View v) {
        String plan = ((EditText) findViewById(R.id.cp_meeting_prepare_plan)).getText() + "";
        Intent intent = new Intent();
        intent.putExtra("plan", plan);
        setResult(RESULT_OK, intent);
        finish();
    }
}