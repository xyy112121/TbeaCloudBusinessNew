package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

/**
 * Created by programmer on 2017/6/4.
 */

public class PlumberMeetingViewActivity extends BaseActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_view);
        initTopbar("会议详情");
        mContext = this;
        listener();
    }

    private void listener(){
        findViewById(R.id.plumber_meeting_view_participant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlumberMeetingParticipantListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.plumber_meeting_view_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlumberMeetingSignInListActivity.class);
                intent.putExtra("flag","meetingSignIn");
                startActivity(intent);
            }
        });



    }
}
