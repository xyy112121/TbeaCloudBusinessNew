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
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.action.RebateAccountAction;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.RebateAccountInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.action.ScanAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.model.ScanCodeIndoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
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

    private void initView(){
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
        final CustomDialog dialog = new CustomDialog(mContext,R.style.MyDialog,R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        mObj = (ScanCodeIndoResponseModel) msg.obj;
                        if (mObj.isSuccess() && mObj.data != null) {
                            ScanCodeIndoResponseModel.TakeMoneyInfo obj = mObj.data.takemoneyinfo;
                            setTextViewValue(R.id.person_info_name,obj.personname);
                            setTextViewValue(R.id.person_info_companyname,obj.companyname);

                            String imagePath = MyApplication.instance.getImgPath();
                            ImageLoader.getInstance().displayImage(imagePath + obj.thumbpicture, ((ImageView) findViewById(R.id.person_info_head)));
                            if(!"".equals(obj.persontypeicon) && obj.persontypeicon != null){
                                ImageLoader.getInstance().displayImage(imagePath + obj.persontypeicon, ((ImageView)findViewById(R.id.person_info_personjobtitle)));
                            }

                            setTextViewValue(R.id.scancode_pay_confirm_code,obj.qrcode);
                            setTextViewValue(R.id.scancode_pay_confirm_money,obj.money);
                            setTextViewValue(R.id.scancode_pay_confirm_state,obj.takemoneystatus);
                            setTextViewValue(R.id.scancode_pay_confirm_validexpiredtime,obj.validexpiredtime);

                            if ("expired".equals(obj.takemoneystatusid)) {
                                findViewById(R.id.scancode_pay_confirm_btn).setEnabled(false);
                                ((Button)findViewById(R.id.scancode_pay_confirm_btn)).setTextColor(ContextCompat.getColor(mContext,R.color.text_color1));
                            }
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
                    ScanAction action = new ScanAction();
                    String code = getIntent().getStringExtra("code");
                    ScanCodeIndoResponseModel re = action.getScanCodeInfo(code);
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
        if(mObj != null && mObj.data != null){
            View parentLayout = findViewById(R.id.parentLayout);
            final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
            popWindow1.init(parentLayout, R.layout.pop_window_header,
                    R.layout.activity_scancode_pay_confirm_tip, "确认提示", "支付用户："+mObj.data.takemoneyinfo.personname,"支付金额："+mObj.data.takemoneyinfo.money, "确认");
            popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
                @Override
                public void onItemClick(String text) {
                    affirm();
                }
            });
        }else {
            ToastUtil.showMessage("操作失败！");
        }
    }

    public void affirm(){
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        BaseResponseModel  model = (BaseResponseModel) msg.obj;
                        if (model.isSuccess()) {
                            Intent intent = new Intent(mContext,ScanCodePaySucceedActivity.class);
                            intent.putExtra("money",mObj.data.takemoneyinfo.money);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showMessage("操作失败！");
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
                    ScanAction action = new ScanAction();
                    BaseResponseModel re = action.scanCodeAffirm(mObj.data.takemoneyinfo.takemoneyid);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }
}
