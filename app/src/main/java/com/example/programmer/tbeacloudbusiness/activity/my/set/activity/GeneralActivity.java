package com.example.programmer.tbeacloudbusiness.activity.my.set.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.NotifyInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.example.programmer.tbeacloudbusiness.utils.cache.DataCleanManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                            showMessage(re.getMsg());
                        }

                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败!");

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

        try {
            String size = DataCleanManager.getTotalCacheSize(MyApplication.instance);
            ((TextView) findViewById(R.id.cache_size)).setText(size);
        } catch (Exception e) {

        }

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

    @OnClick({R.id.top_left, R.id.cache_size})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                setData();
                finish();
                break;
            case R.id.cache_size:
                final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
                dialog.setText("清除缓存？");
                dialog.setCancelBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
//                        cleanInternalCache(getApplicationContext());
//                        cleanExternalCache(mContext);
                        DataCleanManager.clearAllCache(MyApplication.instance);
//                        String size = getCacheSize(getApplicationContext().getExternalCacheDir());

                        ((TextView) findViewById(R.id.cache_size)).setText("0KB");
                        showMessage("清除成功！");
                    }
                }, "确定");
                dialog.setConfirmBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                }, "取消");
                dialog.show();
                break;
        }
    }
}
