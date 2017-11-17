package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PlumberInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

/**
 * 水电工个人资料
 */

public class PlumberManagePersonViewActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_manage_person_view);
        initTopbar("");
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
                            PlumberInfoResponseModel model = (PlumberInfoResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                PlumberInfoResponseModel.DataBean.ElectricianpersonalinfoBean info = model.data.electricianpersonalinfo;
                                setTextView(R.id.plumber_info_name, info.personname);
                                setTextView(R.id.plumber_info_sex, info.sex);
                                setTextView(R.id.plumber_info_phone, info.mobilenumber);
                                setTextView(R.id.plumber_info_age, info.age);
                                setTextView(R.id.plumber_info_workAge, info.workyear);
                                setTextView(R.id.plumber_info_area, info.belongtocompany);
                                setTextView(R.id.plumber_info_whetheridentify, info.whetheridentifyname);
                                setTextView(R.id.plumber_info_intro, info.introduce);
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
                        PlumberManageAction action = new PlumberManageAction();
                        String id = getIntent().getStringExtra("id");
                        PlumberInfoResponseModel model = action.getPersonInfo(id);
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

    private void setTextView(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }
}
