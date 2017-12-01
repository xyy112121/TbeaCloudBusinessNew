package com.example.programmer.tbeacloudbusiness.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.my.set.action.SetAction;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.NetWebViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RegisterRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RegisterResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.UserTypeResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.mic.etoast2.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by programmer on 2017/6/7.
 */

public class RegisterActivity extends Activity {
    @BindView(R.id.register_phone)
    EditText mPhoneView;
    @BindView(R.id.register_pwd)
    EditText mPwdView;
    @BindView(R.id.register_confirm_pwd)
    EditText mConfirmPwdView;
    @BindView(R.id.register_code)
    EditText mCodeView;
    @BindView(R.id.register_send)
    TextView mSendView;
    @BindView(R.id.register_agree)
    CheckBox mAgreeView;


    private View parentLayout;

    List<KeyValueBean> userTypeList = new ArrayList<>();
    private MyCount mc;
    private RegisterRequestModel mRequest = new RegisterRequestModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        parentLayout = findViewById(R.id.register_layout);
        getUserTypeList();
        initView();
    }

    private void initView() {
        ((CheckBox) findViewById(R.id.register_confirm_isShow_pwd)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    mConfirmPwdView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    mConfirmPwdView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        ((CheckBox) findViewById(R.id.register_isShow_pwd)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    mPwdView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    mPwdView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        findViewById(R.id.register_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, NetWebViewActivity.class);
                intent.putExtra("title","用户注册协议");
                intent.putExtra("parameter","userregisteragreement");//URL后缀
                startActivity(intent);
            }
        });
    }

    private void getUserTypeList() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        UserTypeResponseModel model = (UserTypeResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.usertypelist != null) {
                                userTypeList = model.data.usertypelist;
                            }

                        } else {
                            showMessage(model.getMsg());
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
                    SetAction action = new SetAction();
                    UserTypeResponseModel model = action.getUserType();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }


    @OnClick({R.id.top_left, R.id.register_send, R.id.register_user_type, R.id.register_btn, R.id.register_layout})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.top_left:
                finish();
                break;
            case R.id.register_send:
                sendCode();
                break;
            case R.id.register_user_type:
                final CustomPopWindow1 popWindow1 = new CustomPopWindow1(RegisterActivity.this);
                popWindow1.init(parentLayout, R.layout.pop_window_header, R.layout.pop_window_tv, userTypeList, "");
                popWindow1.setItemClick2(new CustomPopWindow1.ItemClick2() {
                    @Override
                    public void onItemClick2(KeyValueBean bean) {
                        mRequest.usertypeid = bean.getKey();
                        ((TextView) view).setText(bean.getValue());
                    }
                });
                break;
            case R.id.register_btn:
                register();
                break;
            case R.id.register_layout:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                            0);
                }
                break;

        }
    }

    private void sendCode() {
        final String mobile = mPhoneView.getText() + "";
        if (isMobileNO(mobile) == false) {
            showMessage("请输入正确的手机号码");
            return;
        }
        mc = new MyCount(60000, 1000);//倒计时60秒
        mc.start();

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess() == false) {
                            mc.cancel();
                            mSendView.setText("获取验证码");
                        }
                        showMessage(re.getMsg());
                        break;
                    case ThreadState.ERROR:
                        showMessage("获取验证失败，请重试！");
                        mc.cancel();
                        mSendView.setText("获取验证码");
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserAction action = new UserAction();
                try {
                    ResponseInfo result = action.sendCode(mobile, "TBEAYUN003001002000");
                    handler.obtainMessage(ThreadState.SUCCESS, result).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
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

    private class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mSendView.setText("获取验证码");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mSendView.setText(millisUntilFinished / 1000 + "秒");

        }
    }

    public void register() {
        mRequest.mobilenumber = mPhoneView.getText() + "";
        mRequest.verifycode = mCodeView.getText() + "";
        mRequest.password = mPwdView.getText() + "";
        if (isMobileNO(mRequest.mobilenumber) == false) {
            showMessage("请输入正确的手机号码！");
            return;
        }
        if ("".equals(mRequest.verifycode)) {
            showMessage("验证码不能为空！");
            return;
        }
        if ("".equals(mRequest.password)) {
            showMessage("密码不能为空！");
            return;
        }

        if (mRequest.password.length() < 6 || mRequest.password.length() > 32) {
            showMessage("密码长度6到32位！");
            return;
        }

        if (!mRequest.password.equals(mConfirmPwdView.getText() + "")) {
            showMessage("两次密码输入不一致！");
            return;
        }

        if ("".equals(mRequest.usertypeid) || mRequest.usertypeid == null) {
            showMessage("用户类型不能为空！");
            return;
        }

        if (mAgreeView.isChecked() == false) {
            showMessage("你需同意用户协议");
            return;
        }

        final CustomDialog dialog = new CustomDialog(RegisterActivity.this, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        RegisterResponseModel re = (RegisterResponseModel) msg.obj;
                        if (re.isSuccess()) {
                            RegisterResponseModel.DataBean.UserinfoBean model = re.data.userinfo;
                            ShareConfig.setConfig(RegisterActivity.this, Constants.USERTYPE, model.usertypeid);
                            ShareConfig.setConfig(RegisterActivity.this, Constants.whetheridentifiedid, model.whetheridentifiedid);
                            ShareConfig.setConfig(RegisterActivity.this, Constants.USERID, model.id);
                            ShareConfig.setConfig(RegisterActivity.this, Constants.ACCOUNT, model.account);
                            Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
                            intent.putExtra("mobilenumber",mRequest.mobilenumber);
                            startActivity(intent);
                            finish();

                        } else {
                            showMessage(re.getMsg());
                        }
                        break;
                    case ThreadState.ERROR:
                        showMessage("注册失败！");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserAction userAction = new UserAction();
                    RegisterResponseModel re = userAction.register(mRequest);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    private void  showMessage(String msg){
        Toast.makeText(RegisterActivity.this, msg , android.widget.Toast.LENGTH_SHORT).show();
    }
}
