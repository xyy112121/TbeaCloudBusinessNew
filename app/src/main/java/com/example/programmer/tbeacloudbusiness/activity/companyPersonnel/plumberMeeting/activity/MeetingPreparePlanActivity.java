package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.utils.Keybords;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 会议安排
 */

public class MeetingPreparePlanActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.cp_meeting_prepare_plan)
    EditText mPlanView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare_plan);
        ButterKnife.bind(this);
        String flag = getIntent().getStringExtra("flag");
        if ("view".equals(flag)) {
            mPlanView.setEnabled(false);
            initTopbar("会议安排");
        } else {
            initTopbar("会议安排", "保存", this);
        }
        String text = getIntent().getStringExtra("text");
        mPlanView.setText(text);

        findViewById(R.id.parentLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow = Keybords.isSoftInputShow(mContext);
                if (isShow) {
                    Keybords.closeKeybord(mPlanView, mContext);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String plan = mPlanView.getText() + "";
        Intent intent = new Intent();
        intent.putExtra("plan", plan);
        setResult(RESULT_OK, intent);
        finish();
    }
}
