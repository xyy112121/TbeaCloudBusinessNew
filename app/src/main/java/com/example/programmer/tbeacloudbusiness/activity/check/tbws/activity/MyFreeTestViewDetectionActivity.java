package com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.info.TestDetailsResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;


/**
 * 获得我的免费检测-­‐预约详情-­‐完工-­‐检测详情.
 */

public class MyFreeTestViewDetectionActivity extends BaseActivity {
    private String mCheckResultId;
    LinearLayout mParentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_free_test_view_detection);
        initTopbar("检测详情");
        mCheckResultId = getIntent().getStringExtra("checkResultId");
        mParentLayout = (LinearLayout) findViewById(R.id.my_free_test_view_detection_layout);
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
                        TestDetailsResponseModel model = (TestDetailsResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.electricalcheckworklist != null) {
                                for (int i = 0; i < model.data.electricalcheckworklist.size(); i++) {
                                    TestDetailsResponseModel.DataBean.ElectricalcheckworklistBean item = model.data.electricalcheckworklist.get(i);
                                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.my_free_test_view_detection_item, null);
                                    TextView textView = (TextView) layout.findViewById(R.id.my_free_test_view_detection_item_text);
                                    CheckBox ck1 = (CheckBox) layout.findViewById(R.id.my_free_test_view_detection_item_ck1);
                                    CheckBox ck2 = (CheckBox) layout.findViewById(R.id.my_free_test_view_detection_item_ck2);
                                    CheckBox ck3 = (CheckBox) layout.findViewById(R.id.my_free_test_view_detection_item_ck3);
                                    textView.setText(item.checkitem);
                                    if ("qualified".equals(item.checkresult)) {
                                        ck1.setChecked(true);
                                    } else if ("unqualified".equals(item.checkresult)) {
                                        ck2.setChecked(true);
                                    } else if ("needfix".equals(item.checkresult)) {
                                        ck3.setChecked(true);
                                    }
                                    mParentLayout.addView(layout);
                                }
                            }
                            if (model.data.checkdescriptioninfo != null) {
                                TextView noteView = (TextView) findViewById(R.id.my_free_test_view_detection_note);
                                noteView.setText("备注信息：" + model.data.checkdescriptioninfo.description);
                            }

                        } else {
                            ToastUtil.showMessage(model.getMsg());
                        }

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
                    TestDetailsResponseModel re = action.testDetails(mCheckResultId);
                    if (re != null) {
                        handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                    } else {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }

                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }
}
