package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.AddressEditListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.EditBindingPhoneActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.GeneralActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.PwdEditActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.SetBackgroundActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.SetResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设置
 */

public class SetActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        initTopbar("设置");
        getData();
    }

    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            SetResponseModel model = (SetResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                ((TextView)findViewById(R.id.set_phone)).setText(model.data.baseinfo.mobilenumber);
                                ((TextView)findViewById(R.id.set_address)).setText(model.data.baseinfo.recvaddrnumber+"");

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
                        SetAction action = new SetAction();
                        SetResponseModel model = action.getSetInfo();
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


    @OnClick({R.id.set_phone, R.id.set_edit_pwd, R.id.set_address, R.id.set_background, R.id.set_general})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_phone:
                Intent intent = new Intent(mContext, EditBindingPhoneActivity.class);
                intent.putExtra("phone",((TextView)view).getText()+"");
                startActivity(intent);
                break;
            case R.id.set_edit_pwd:
                startActivity(new Intent(mContext, PwdEditActivity.class));
                break;
            case R.id.set_address:
                startActivity(new Intent(mContext, AddressEditListActivity.class));
                break;
            case R.id.set_background:
                startActivity(new Intent(mContext, SetBackgroundActivity.class));
                break;
            case R.id.set_general:
                startActivity(new Intent(mContext, GeneralActivity.class));
                break;
        }
    }
}
