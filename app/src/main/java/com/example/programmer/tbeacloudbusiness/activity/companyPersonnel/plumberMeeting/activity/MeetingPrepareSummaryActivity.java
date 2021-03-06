package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 会议纪要
 */

public class MeetingPrepareSummaryActivity extends BaseActivity implements View.OnClickListener {
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
            initTopbar("会议纪要");
        } else {
            initTopbar("会议纪要", "保存", this);
        }
        String text = getIntent().getStringExtra("text");
        mPlanView.setText(text);
        ((TextView) findViewById(R.id.cp_meeting_prepare_plan_tv)).setText("录入");
    }

    @Override
    public void onClick(View v) {
        String content = mPlanView.getText() + "";
        save(content);
    }

    //保存会议纪要
    private void save(final String content) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            BaseResponseModel model = (BaseResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                Intent intent = new Intent();
                                intent.putExtra("summary", content);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                        String id = getIntent().getStringExtra("meetingid");
                        BaseResponseModel model = action.saveSummary(id, content);
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}