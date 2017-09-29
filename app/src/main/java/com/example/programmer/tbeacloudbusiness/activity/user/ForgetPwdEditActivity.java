package com.example.programmer.tbeacloudbusiness.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;


/**
 * 重置密码
 */

public class ForgetPwdEditActivity extends BaseActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_edit);
        initTopbar("重置密码");
        mContext = this;
        findViewById(R.id.activity_old_pwd_layout).setVisibility(View.GONE);
        findViewById(R.id.activity_old_pwd_view).setVisibility(View.GONE);
        listener();
    }

    public void listener() {
        findViewById(R.id.pwd_edit_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPwd = ((TextView) findViewById(R.id.pwd_edit_new)).getText() + "";
                String confirmPwd = ((TextView) findViewById(R.id.pwd_edit_confirm)).getText() + "";

                if ("".equals(newPwd)) {
                    ToastUtil.showMessage("新密码不能为空！");
                    return;
                }

                if (newPwd.length() < 6 || newPwd.length() > 10) {
                    ToastUtil.showMessage("密码长度6到10位！");
                    return;
                }


                if (!newPwd.equals(confirmPwd)) {
                    ToastUtil.showMessage("两次密码不一致！");
                    return;
                }
                String mobile = getIntent().getStringExtra("mobile");
                updatePwd(mobile, newPwd);
            }
        });

    }

    public void updatePwd(final String mobile, final String newPwd) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showMessage(re.getMsg());
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
                    UserAction userAction = new UserAction();
                    ResponseInfo re = userAction.resetPwd(mobile, newPwd);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }
}
