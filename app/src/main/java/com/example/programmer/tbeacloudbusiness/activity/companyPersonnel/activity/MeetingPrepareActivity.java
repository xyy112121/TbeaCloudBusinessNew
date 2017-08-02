package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司人员-会议准备
 */

public class MeetingPrepareActivity extends BaseActivity {
    @BindView(R.id.cp_meeting_prepare_hold_time)
    PublishTextRowView mHoldTimeView;
    @BindView(R.id.cp_meeting_prepare_hold_monad)
    LinearLayout mHoldMonadView;
    @BindView(R.id.cp_meeting_prepare_hold_addr)
    PublishTextRowView mHoldAddrView;
    @BindView(R.id.cp_meeting_prepare_state)
    PublishTextRowView cpMeetingPrepareState;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.person_info_head)
    CircleImageView personInfoHead;
    @BindView(R.id.person_info_name)
    TextView personInfoName;
    @BindView(R.id.person_info_personjobtitle)
    ImageView personInfoPersonjobtitle;
    @BindView(R.id.person_info_companyname)
    TextView personInfoCompanyname;
    @BindView(R.id.cp_meeting_prepare_participant)
    PublishTextRowView mParticipantView;
    @BindView(R.id.cp_meeting_prepare_plan)
    PublishTextRowView mPlanView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare);
        ButterKnife.bind(this);
        initTopbar("会议准备");
    }

    @OnClick({R.id.cp_meeting_prepare_hold_time, R.id.cp_meeting_prepare_hold_monad, R.id.cp_meeting_prepare_participant, R.id.cp_meeting_prepare_plan,R.id.cp_meeting_prepare_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cp_meeting_prepare_hold_time:
                startActivity(new Intent(mContext, DateSelectActivity.class));
                break;
            case R.id.cp_meeting_prepare_hold_monad:
                startActivity(new Intent(mContext, FranchiserSelectListActivity.class));
                break;
            case R.id.cp_meeting_prepare_participant:
                startActivity(new Intent(mContext, FranchiserSelectListActivity.class));
                break;
            case R.id.cp_meeting_prepare_plan:
                startActivity(new Intent(mContext, MeetingPreparePlanActivity.class));
                break;
            case R.id.cp_meeting_prepare_finish:
                showAlert();
                break;
        }
    }

    /**
     * 显示警示框
     */
    private void showAlert() {
        View parentLayout = findViewById(R.id.parentLayout);
        final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header,
                R.layout.view_cp_meeting_prepare_alert, "提示");
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
               Intent intent = new Intent(mContext,MeetingSponsorSuccessActivity.class);
                startActivity(intent);

            }
        });
    }

}
