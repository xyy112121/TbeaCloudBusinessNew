package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司人员-会议发起成功
 */

public class MeetingSponsorSuccessActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_sponsor_success);
        ButterKnife.bind(this);
        initTopbar("发起成功", "查看", this);
        findViewById(R.id.top_left).setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.completion_finish)
    public void onViewClicked() {
        Intent intent = new Intent(mContext,PlumberMeetingListActivity.class);
        startActivity(intent);
    }
}
