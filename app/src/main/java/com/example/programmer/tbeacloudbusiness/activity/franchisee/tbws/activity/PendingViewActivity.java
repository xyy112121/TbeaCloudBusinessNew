package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

/**
 * 待处理详情
 */

public class PendingViewActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_view_pendind);
        initTopbar("预约详情", "取消", this);
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
//                        UntreatedRsponseModel re = (UntreatedRsponseModel) msg.obj;
//                        if (re.isSuccess()) {
//                            if (re.data != null) {
//                                if (re.data.electricalcheckinfo != null) {
//                                    mMode = re.data.electricalcheckinfo;
//                                    setViewText(R.id.free_test_view_code, mMode.vouchercode);
//                                    setViewText(R.id.free_test_view_time, mMode.subscribetime);
//                                    setViewText(R.id.free_test_view_status, mMode.checkstatus);
//                                }
//
//                            } else {
//                                ToastUtil.showMessage(re.getMsg());
//                            }
//
//
//                        } else {
//                            ToastUtil.showMessage("操作失败！");
//                        }

                        break;
                    case ThreadState.ERROR:
                        ToastUtil.showMessage("操作失败！");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SubscribeAction action = new SubscribeAction();
                    String id = getIntent().getStringExtra("id");
                    ResponseInfo re = action.getPendingInfo(id);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    public void setViewText(int id, String text) {
        ((PublishTextRowView) findViewById(id)).setValueText(text);
    }

    @Override
    public void onClick(View v) {

    }

}
