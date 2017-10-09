package com.example.programmer.tbeacloudbusiness.activity.user;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MainActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.LoginUserModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.UpdateResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.Installation;
import com.example.programmer.tbeacloudbusiness.utils.AppUpdateUtils;
import com.example.programmer.tbeacloudbusiness.utils.AppVersion;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.DeviceIdUtil;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.permissonutil.PermissionActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 登录
 */

public class LoginActivity extends PermissionActivity {
    private Context mContext;
    private String mDeviceId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        if (ShareConfig.getConfigBoolean(mContext, Constants.ONLINE, false)) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            //单个权限获取
            checkPermission(new CheckPermListener() {
                @Override
                public void Granted() {
                    mDeviceId = DeviceIdUtil.getDeviceId();
                }

                @Override
                public void Denied() {
                    mDeviceId = Installation.id(mContext);
                    //检查是否选择了不再提醒
                }
            }, "请求获取电话权限", Manifest.permission.READ_PHONE_STATE);

            listener();
            getUpdateInfo();
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
//                                        av.setUrl("http://down.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
                                        av.setUrl(info.jumpurl);
                                        av.setContent(info.upgradedescription);
                                        av.setVerCode(info.versioncode);
                                        av.setVersionName(info.versionname);
                                        if ("yes".equals(info.mustupgrade)) {
                                            AppUpdateUtils.init(mContext, av, true, false, false);//强制升级
                                        } else {
                                            AppUpdateUtils.init(mContext, av, true, false, true);
                                        }
                                        AppUpdateUtils.upDate();
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

    private void listener() {

        final EditText pwdEd = (EditText) findViewById(R.id.login_pwd);

        findViewById(R.id.login_register_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        ((CheckBox) findViewById(R.id.login_isShow_pwd)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    pwdEd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    pwdEd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        findViewById(R.id.login_forget_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPwdPhoneActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = ((TextView) findViewById(R.id.login_phone)).getText() + "";
                final String pwd = ((TextView) findViewById(R.id.login_pwd)).getText() + "";
                if (isMobileNO(mobile) == false) {
                    ToastUtil.showMessage("请输入正确的手机号码！");
                    return;
                }
                if ("".equals(pwd)) {
                    ToastUtil.showMessage("请输入密码！");
                    return;

                }
                final CustomDialog dialog = new CustomDialog(LoginActivity.this, R.style.MyDialog, R.layout.tip_wait_dialog);
                dialog.setText("请等待...");
                dialog.show();
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        dialog.dismiss();
                        switch (msg.what) {
                            case ThreadState.SUCCESS:
                                ResponseInfo re = (ResponseInfo) msg.obj;
                                if (re.isSuccess()) {
                                    Gson gson = new GsonBuilder().serializeNulls().create();
                                    String json = gson.toJson(re.data);
                                    LoginUserModel model = gson.fromJson(json, LoginUserModel.class);
                                    if (model != null) {
                                        ShareConfig.setConfig(LoginActivity.this, Constants.ONLINE, true);
                                        ShareConfig.setConfig(LoginActivity.this, Constants.USERID, model.userinfo.id);
                                        ShareConfig.setConfig(LoginActivity.this, Constants.USERTYPE, model.userinfo.usertypeid);
                                        ShareConfig.setConfig(LoginActivity.this, Constants.ACCOUNT, model.userinfo.account);
                                        ShareConfig.setConfig(LoginActivity.this, Constants.whetheridentifiedid, model.userinfo.whetheridentifiedid);
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        ToastUtil.showMessage("操作失败！");
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
                            UserAction action = new UserAction();
                            ResponseInfo re = action.login(mobile, pwd, mDeviceId);
                            handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                        } catch (Exception e) {
                            handler.sendEmptyMessage(ThreadState.ERROR);
                        }
                    }
                }).start();

            }
        });
    }

    /**
     * 验证手机格式 false不正确
     */
    public boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][73458]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (mobiles.equals("")) return false;
        else return mobiles.matches(telRegex);
    }

}
