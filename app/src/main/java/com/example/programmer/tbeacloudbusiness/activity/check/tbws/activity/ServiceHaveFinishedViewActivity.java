package com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.info.HaveFinishedResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 已完工
 */

public class ServiceHaveFinishedViewActivity extends BaseActivity {
    private String mId;
    private String mCheckResultId;
    private String mAssignid;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_view_have_finish);
        ButterKnife.bind(this);
        initTopbar("检测详情");
        findViewById(R.id.sr_view_upload).setVisibility(View.VISIBLE);
        getDate();
    }

    public void getDate() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(re.data);
                            HaveFinishedResponseModel model = gson.fromJson(json, HaveFinishedResponseModel.class);
                            if (model.electricalcheckinfo != null) {
                                HaveFinishedResponseModel.ElectricalcheckinfoBean info = model.electricalcheckinfo;

                                ImageView headView = (ImageView) findViewById(R.id.person_info_head);
                                ImageLoader.getInstance().displayImage(info.thumbpicture, headView);
                                ((TextView) findViewById(R.id.person_info_name)).setText(info.name);
                                ((TextView) findViewById(R.id.person_info_companyname)).setText(info.info);
                                findViewById(R.id.person_info_personjobtitle).setVisibility(View.GONE);

                                setViewText(R.id.sr_view_assign_time, info.assigntime);
                                setViewText(R.id.sr_view_assign_state, info.checkstatus);
                                setViewText(R.id.sr_view_finish_time, info.finishtime);
//                                mCode = info.vouchercode;
                                setViewText(R.id.sr_view_state_subscribeCode, info.subscribecode);

                                mCheckResultId = info.checkresultid;
                                mAssignid = info.assignid;
                                code = info.subscribecode;
                            }

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
                    SubscribeAction action = new SubscribeAction();
                    mId = getIntent().getStringExtra("id");
                    ResponseInfo re = action.getHaveFinish(mId);
                    if (re == null) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    } else {
                        handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    public void setViewText(int id, String text) {
        ((PublishTextRowView) findViewById(id)).setValueText(text);
    }

    @OnClick({R.id.sr_view_state_subscribeCode, R.id.sr_view_state_checkResult,R.id.sr_view_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sr_view_state_subscribeCode:
                Intent intent = new Intent(mContext, PendingViewActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("flag","finised");
                startActivity(intent);
                break;
            case R.id.sr_view_state_checkResult:
                intent = new Intent(mContext, MyFreeTestViewDetectionActivity.class);
                intent.putExtra("checkResultId", mCheckResultId);
                startActivity(intent);
                break;
            case R.id.sr_view_upload:
                intent = new Intent(mContext, PictureUploadActivity.class);
                intent.putExtra("assignid", mAssignid);
                intent.putExtra("code", code);
                intent.putExtra("checkResultId", mCheckResultId);
                startActivity(intent);
                break;
        }
    }
}
