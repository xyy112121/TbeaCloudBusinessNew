package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.action.ScanAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.model.ScanCodeIndoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 支付确认
 */

public class ScanCodePayConfirmActivity extends BaseActivity {
    private ScanCodeIndoResponseModel mObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancode_pay_confirm);
        initTopbar("支付确认");
        initView();
        getDate();
    }

    private void initView() {
        findViewById(R.id.scancode_pay_confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    /**
     * 获取数据
     */
    public void getDate() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo result = (ResponseInfo) msg.obj;
                        if (result.isSuccess()) {
                            Object data = result.data;
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(data);
                            mObj = gson.fromJson(json, ScanCodeIndoResponseModel.class);
                            ScanCodeIndoResponseModel.TakeMoneyInfo obj = mObj.takemoneyinfo;
                            setTextViewValue(R.id.person_info_name, obj.personname);
                            setTextViewValue(R.id.person_info_companyname, obj.companyname);

                            String imagePath = MyApplication.instance.getImgPath();
                            ImageLoader.getInstance().displayImage(imagePath + obj.thumbpicture, ((ImageView) findViewById(R.id.person_info_head)));
                            if (!"".equals(obj.persontypeicon) && obj.persontypeicon != null) {
                                ImageLoader.getInstance().displayImage(imagePath + obj.persontypeicon, ((ImageView) findViewById(R.id.person_info_personjobtitle)));
                            }
                            findViewById(R.id.person_info_right).setVisibility(View.GONE);

                            setTextViewValue(R.id.scancode_pay_confirm_code, obj.qrcode);
                            setTextViewValue(R.id.scancode_pay_confirm_money, obj.money);
                            setTextViewValue(R.id.scancode_pay_confirm_state, obj.takemoneystatus);
                            setTextViewValue(R.id.scancode_pay_confirm_validexpiredtime, obj.validexpiredtime);

                            if ("expired".equals(obj.takemoneystatusid)) {
                                findViewById(R.id.scancode_pay_confirm_btn).setEnabled(false);
                                ((Button) findViewById(R.id.scancode_pay_confirm_btn)).setTextColor(ContextCompat.getColor(mContext, R.color.text_color1));
                            }
                        } else {
                            showMessage(result.getMsg());
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
                    ScanAction action = new ScanAction();
                    String code = getIntent().getStringExtra("code");
                    ResponseInfo re = action.getScanCodeInfo(code);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    /**
     * 显示警示框
     */
    private void showAlert() {
        if (mObj != null && mObj.takemoneyinfo != null) {
            View parentLayout = findViewById(R.id.parentLayout);
            final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
            popWindow1.init(parentLayout, R.layout.pop_window_header,
                    R.layout.activity_scancode_pay_confirm_tip, "确认提示", "支付用户：" + mObj.takemoneyinfo.personname, "支付金额：" + "￥" + mObj.takemoneyinfo.money, "确认");
            popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
                @Override
                public void onItemClick(String text) {
                    affirm();
                }
            });
        } else {
            showMessage("操作失败！");
        }
    }

    public void affirm() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo model = (ResponseInfo) msg.obj;
                        if (model.isSuccess()) {
                            Intent intent = new Intent(mContext, ScanCodePaySucceedActivity.class);
                            intent.putExtra("money", mObj.takemoneyinfo.money);
                            intent.putExtra("takeMoneyId", mObj.takemoneyinfo.takemoneyid);
                            startActivity(intent);
                            finish();
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
                    ScanAction action = new ScanAction();
                    ResponseInfo re = action.scanCodeAffirm(mObj.takemoneyinfo.takemoneyid);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }
}
