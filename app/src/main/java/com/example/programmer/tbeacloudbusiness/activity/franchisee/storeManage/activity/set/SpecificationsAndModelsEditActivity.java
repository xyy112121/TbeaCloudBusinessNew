package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加规格型号
 */

public class SpecificationsAndModelsEditActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.specifications_models_edit_name)
    PublishTextRowView mNameView;
    private String mMethodName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifications_and_models_edit);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        mMethodName = getIntent().getStringExtra("methodName");
        initTopbar("添加商品" + title, "保存", this);
    }

    @Override
    public void onClick(View v) {
        save();
    }

    private void save() {
        final String name = mNameView.getValueText();
        if ("".equals(name)) {
            ToastUtil.showMessage("请型号规格名称");
            return;
        }
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请稍等...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            setResult(RESULT_OK);
                            finish();
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
                    StoreManageAction action = new StoreManageAction();
                    ResponseInfo re = action.saveModelSpec(name, mMethodName);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }
}
