package com.example.programmer.tbeacloudbusiness.activity.plumberMeeting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * Created by programmer on 2017/6/4.
 */

public class ViewActivity extends TopActivity {
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
                Intent intent = new Intent(mContext,ParticipantListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.plumber_meeting_view_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SignInListActivity.class);
                intent.putExtra("flag","meetingSignIn");
                startActivity(intent);
            }
        });



    }
}
