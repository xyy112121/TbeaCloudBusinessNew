package com.example.programmer.tbeacloudbusiness.activity.my.set.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.BackgroundInfoModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.NotifyInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by programmer on 2017/6/25.
 */

public class GeneralActivity extends BaseActivity {
    @BindView(R.id.general_notify)
    CheckBox mNotifyView;
    private String mNotify = "off";//'on' 打开， 'off':关闭
    private boolean mFlag;//判断是不是第一次进入

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        ButterKnife.bind(this);
        initTopbar("通用");
        initView();
        getData();
    }

    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        NotifyInfoResponseModel re = (NotifyInfoResponseModel) msg.obj;
                        if (re.isSuccess() && re.data != null) {
                            if ("on".equals(re.data.settinginfo.notifyonoroff)) {
                                mNotifyView.setChecked(true);
                            } else {
                                mNotifyView.setChecked(false);
                            }
                        } else {
                            ToastUtil.showMessage(re.getMsg());
                        }

                        break;
                    case ThreadState.ERROR:
                        ToastUtil.showMessage("操作失败!");

                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SetAction action = new SetAction();
                    NotifyInfoResponseModel model = action.getNotify();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    private void initView() {
        mNotifyView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mNotify = "on";
                } else {
                    mNotify = "off";
                }
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                finish();
            }
        });
    }


    private void setData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SetAction action = new SetAction();
                    action.setNotify(mNotify);
                } catch (Exception e) {

                }
            }
        }).start();
    }
}
