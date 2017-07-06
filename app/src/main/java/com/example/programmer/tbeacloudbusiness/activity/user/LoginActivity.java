package com.example.programmer.tbeacloudbusiness.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MainActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.Installation;
import com.example.programmer.tbeacloudbusiness.service.impl.UserAction;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.DeviceIdUtil;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.Map;

/**
 * Created by programmer on 2017/6/5.
 */

public class LoginActivity extends FragmentActivity {
    private Context mContext;
    private String mDeviceId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mDeviceId = DeviceIdUtil.getDeviceId();
        //861138028941751
        if(TextUtils.isEmpty(mDeviceId)){
            mDeviceId = Installation.id(mContext);
        }

        listener();
    }

    private void listener(){
        findViewById(R.id.login_register_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = ((TextView)findViewById(R.id.login_phone)).getText()+"";
                final String pwd = ((TextView)findViewById(R.id.login_pwd)).getText()+"";
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
                                BaseResponseModel re = (BaseResponseModel) msg.obj;
                                if (re.isSuccess()) {
                                    Map<String,Object> data = (Map<String,Object>)re.getData();
                                    Map<String,String> userinfo = (Map<String,String>)data.get("userinfo");
                                    ShareConfig.setConfig(LoginActivity.this, Constants.ONLINE, true);
                                    ShareConfig.setConfig(LoginActivity.this, Constants.USERID, userinfo.get("id"));
                                    Intent intent = new Intent(mContext, MainActivity.class);
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
                            BaseResponseModel re = userAction.login(mobile, pwd,mDeviceId);
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
