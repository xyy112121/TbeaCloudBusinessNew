package com.example.programmer.tbeacloudbusiness.activity.companyPerson.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by DELL on 2017/8/13.
 */

public class MeetingPrepareSummaryActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare_plan);
        initTopbar("会议纪要", "保存", this);
        ((TextView)findViewById(R.id.cp_meeting_prepare_plan_tv)).setText("录入");
    }

    @Override
    public void onClick(View v) {
        String plan = ((EditText) findViewById(R.id.cp_meeting_prepare_plan)).getText() + "";
        Intent intent = new Intent();
        intent.putExtra("summary", plan);
        setResult(RESULT_OK, intent);
        finish();
    }
}