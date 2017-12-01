package com.example.programmer.tbeacloudbusiness.activity.my.set.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.PwdUpdateModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


/**
 * 修改密码
 */

public class PwdEditActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_edit);
        initTopbar("修改登录密码");
        MyApplication.instance.addActivity(PwdEditActivity.this);
        listener();
    }

    public void listener() {
        findViewById(R.id.pwd_edit_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpwd = ((TextView) findViewById(R.id.pwd_edit_old)).getText() + "";
                String newPwd = ((TextView) findViewById(R.id.pwd_edit_new)).getText() + "";
                String confirmPwd = ((TextView) findViewById(R.id.pwd_edit_confirm)).getText() + "";

                if ("".equals(oldpwd)) {
                    showMessage("当前密码不能为空！");
                    return;
                }

                if ("".equals(newPwd)) {
                    showMessage("新密码不能为空！");
                    return;
                }

                if (newPwd.length() < 6 || newPwd.length() > 32) {
                    showMessage("密码长度6到32位！");
                    return;
                }

                if (!newPwd.equals(confirmPwd)) {
                    showMessage("两次密码不一致！");
                    return;
                }
                updatePwd(oldpwd, newPwd);
            }
        });

    }

    public void updatePwd(final String oldPwd, final String newPwd) {
        final CustomDialog dialog = new CustomDialog(PwdEditActivity.this, R.style.MyDialog, R.layout.tip_wait_dialog);
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
                            Intent intent = new Intent(PwdEditActivity.this, PwdEditSucceedActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showMessage(re.getMsg());
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
                    SetAction userAction = new SetAction();
                    ResponseInfo re = userAction.updatePwd(oldPwd, newPwd);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }
}
