package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.action.RebateAccountAction;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.RebateAccountInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 积分提现
 */

public class RebateAccountWithdrawCashActivity extends BaseActivity {
    protected int mCanexChangeMoney;
    private String mDistributorid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebate_account_withdraw_cash);
        initTopbar("提现");
        initView();
        getDate();
    }

    public void initView() {
        findViewById(R.id.rebate_account_withdraw_cash_finsh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String money = ((EditText) findViewById(R.id.rebate_account_withdraw_cash_money)).getText() + "";
                    if ("".equals(money) || "0".equals(money)) {
                        ToastUtil.showMessage("请填写提现金额！");
                        return;
                    }

                    Double mo = Double.parseDouble(money);
                    if (mo > mCanexChangeMoney) {
                        ToastUtil.showMessage("不能大于最大提现金额！");
                        return;
                    }
                    showAlert(money);
                } catch (Exception e) {

                }

            }
        });

        findViewById(R.id.wallet_withdraw_cash_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText) findViewById(R.id.rebate_account_withdraw_cash_money)).setText(mCanexChangeMoney + "");
            }
        });
    }

    private void showAlert(final String money) {
        View parentLayout = findViewById(R.id.parentLayout);
        final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header,
                R.layout.activity_scancode_pay_confirm_tip, "确认提示", "是否提现，请确认！", "确认", 0);
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                Intent intent = new Intent(RebateAccountWithdrawCashActivity.this, WithdrawCashViewActivity.class);
                intent.putExtra("money", money);
                intent.putExtra("distributorid", mDistributorid);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 获取数据
     */
    public void getDate() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo model = (ResponseInfo) msg.obj;
                        if (model.isSuccess()) {
                            if (model.isSuccess() && model.data != null) {
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                String json = gson.toJson(model.data);
                                RebateAccountInfoResponseModel obj = gson.fromJson(json, RebateAccountInfoResponseModel.class);
                                if (obj != null) {
                                    mCanexChangeMoney = obj.mymoneyinfo.canexchangemoney;
                                    ((TextView) findViewById(R.id.rebate_account_withdraw_cash_info)).setText("当前可提现金额：￥" + obj.mymoneyinfo.canexchangemoney);
                                }
                                if (obj.firstdistributorinfo != null) {
                                    RebateAccountInfoResponseModel.FirstDistriButorinfo info = obj.firstdistributorinfo;
                                    mDistributorid = info.id;
                                    ((TextView) findViewById(R.id.person_info_name)).setText(info.personname);
                                    ((TextView) findViewById(R.id.person_info_companyname)).setText(info.companyname);
                                    findViewById(R.id.person_info_right).setVisibility(View.GONE);
                                    String imagePath = MyApplication.instance.getImgPath();
                                    ImageLoader.getInstance().displayImage(imagePath + info.thumbpicture, ((ImageView) findViewById(R.id.person_info_head)));
                                    if (!"".equals(info.persontypeicon)) {
                                        ImageLoader.getInstance().displayImage(imagePath + info.persontypeicon, ((ImageView) findViewById(R.id.person_info_personjobtitle)));
                                    }
                                }

                            }
                        } else {
                            ToastUtil.showMessage(model.getMsg());
                            findViewById(R.id.rebate_account_withdraw_cash_top).setVisibility(View.GONE);
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
                    RebateAccountAction action = new RebateAccountAction();
                    ResponseInfo re = action.getRebateAccountInfo();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

}
