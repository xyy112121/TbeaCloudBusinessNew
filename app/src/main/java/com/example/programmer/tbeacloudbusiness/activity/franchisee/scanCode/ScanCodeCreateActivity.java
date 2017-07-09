package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeCreateResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.Condition;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

/**
 * 生成二维码
 */

public class ScanCodeCreateActivity extends BaseActivity implements View.OnClickListener {
    private final int REQEST_TYPE_NORMS = 1004;
    private Condition mType;
    private Condition mNorms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code);
        mContext = this;
        initTopbar("生成返利二维码", "历史记录", this);
        initView();
    }

    private void initView() {
        findViewById(R.id.create_code_type_norms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ScanCodeCreateSelectActivity.class);
                startActivityForResult(intent, REQEST_TYPE_NORMS);
            }
        });

        findViewById(R.id.create_code_finish_bth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = ((TextView) findViewById(R.id.create_code_money)).getText() + "";
                String number = ((TextView) findViewById(R.id.create_code_number)).getText() + "";
                if (TextUtils.isEmpty(money) || TextUtils.isEmpty(number) || mType == null || mNorms == null) {
                    ToastUtil.showMessage("请填写完整资料");
                    return;
                }
                createCode(money, number);


            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(mContext, ScanCodeHistoryListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQEST_TYPE_NORMS) {
            mType = (Condition) data.getSerializableExtra("type");
            mNorms = (Condition) data.getSerializableExtra("norms");
            String type = mType != null ? mType.getName() + "  " : "";
            String norm = mNorms != null ? mNorms.getName() : "";
            ((TextView) findViewById(R.id.create_code_type_norms)).setText(type + norm);
        }
    }

    /**
     * 从服务器获取数据
     */
    private void createCode(final String money, final String number) {
        try {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("请等待...");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ScanCodeCreateResponseModel model = (ScanCodeCreateResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                Intent intent = new Intent(mContext, ScanCodeCreateSucceedActivity.class);
                                intent.putExtra("id", model.data.generateinfo.id);
                                startActivity(intent);
                                finish();

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
                        ScanCodeAction action = new ScanCodeAction();
                        ScanCodeCreateResponseModel model = action.createScanCode(money, number, mType.getId(), mNorms.getId());
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
