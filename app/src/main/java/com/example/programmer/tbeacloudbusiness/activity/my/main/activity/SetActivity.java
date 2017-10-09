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
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.AddressEditListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.EditBindingPhoneActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.GeneralActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.PwdEditActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.SetBackgroundActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.SetResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.LoginActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.UpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.AppUpdateUtils;
import com.example.programmer.tbeacloudbusiness.utils.AppVersion;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设置
 */

public class SetActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        initTopbar("设置");
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                                ((TextView) findViewById(R.id.set_phone)).setText(model.data.baseinfo.mobilenumber);
                                ((TextView) findViewById(R.id.set_address)).setText(model.data.baseinfo.recvaddrnumber + "");

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


    @OnClick({R.id.set_phone, R.id.set_edit_pwd, R.id.set_address, R.id.set_background, R.id.set_general, R.id.set_my_quit,R.id.set_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_phone:
                Intent intent = new Intent(mContext, EditBindingPhoneActivity.class);
                intent.putExtra("phone", ((TextView) view).getText() + "");
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
            case R.id.set_update:
                getUpdateInfo();
                break;
            case R.id.set_my_quit:
                final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
                dialog.setText("您确定要退出么？");
                dialog.setCancelBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        ShareConfig.setConfig(mContext, Constants.ONLINE, false);
                        ShareConfig.setConfig(mContext, Constants.USERID, "");
                        finish();
                        MyApplication.instance.exit();
                        startActivity(new Intent(mContext, LoginActivity.class));

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

    /**
     * 从服务器获取是否更新
     */
    private void getUpdateInfo() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo re = (ResponseInfo) msg.obj;
                            if (re.isSuccess()) {
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                String json = gson.toJson(re.data);
                                UpdateResponseModel model = gson.fromJson(json, UpdateResponseModel.class);
                                if (model.versioninfo != null) {
                                    UpdateResponseModel.VersioninfoBean info = model.versioninfo;
                                    if ("on".equals(info.tipswitch) && info.jumpurl != null && !"".equals(info.jumpurl)) {
                                        AppVersion av = new AppVersion();
                                        av.setApkName("tbeacloudbusiness.apk");
//                                av.setSha1("FCDA0D0E1E7D620A75DA02A131E2FFEDC1742AC8");
                                        av.setUrl("http://down.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
//                                        av.setUrl(info.jumpurl);
                                        av.setContent(info.upgradedescription);
                                        av.setVerCode(info.versioncode);
                                        av.setVersionName(info.versionname);
                                        if ("yes".equals(info.mustupgrade)) {
                                            AppUpdateUtils.init(mContext, av, false, false, false);//强制升级
                                            AppUpdateUtils.upDate();
                                        } else {
                                            AppUpdateUtils.init(mContext, av, false, false, true);
                                            AppUpdateUtils.upDate();
                                        }

                                    }
                                } else {
                                    ToastUtil.showMessage(re.getMsg());
                                }

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
                        ResponseInfo model = userAction.getUpdate();
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
