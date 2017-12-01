package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.action.RebateAccountAction;
import com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model.WithdrawCashViewResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 积分提现(二维码生成界面)
 */

public class WithdrawCashViewActivity extends BaseActivity implements View.OnClickListener {
    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratate_account_withdraw_cash_view);
        initTopbar("提现凭证", "删除", this);
        String money = getIntent().getStringExtra("money");
        String distributorid = getIntent().getStringExtra("distributorid");
        if ("".equals(money) || money == null) {
            mId = getIntent().getStringExtra("takemoneycodeid");
            getDate2(mId);
        } else {
            getDate(money, distributorid);
        }
    }

    /**
     * 根据id获取数据
     */
    public void getDate2(final String takemoneyqrcodeid) {
        final CustomDialog dialog = new CustomDialog(WithdrawCashViewActivity.this, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            Object date = re.data;
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(date);
                            WithdrawCashViewResponseModel.WithdrawCashViewModel model = gson.fromJson(json, WithdrawCashViewResponseModel.WithdrawCashViewModel.class);
                            ((TextView) findViewById(R.id.wallet_withdraw_cash_view_withdraw)).setText("提现单位：" + model.distributorinfo.name);
                            WithdrawCashViewResponseModel.TakemoneyInfo info = model.takemoneyinfo;
                            if (info != null) {
                                ImageView imageView = (ImageView) findViewById(R.id.wallet_withdraw_cash_view_qrcodepicture);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + info.qrcodepicture, imageView);
                                ((TextView) findViewById(R.id.withdraw_cash_view_takemoneycode)).setText("验证码：" + info.takemoneycode);
                                ((TextView) findViewById(R.id.withdraw_cash_view_money)).setText(info.money);
                                ((TextView) findViewById(R.id.withdraw_cash_view_validexpiredtime)).setText("有效期：" + info.validexpiredtime);
                                ((TextView) findViewById(R.id.wallet_withdraw_cash_view_note)).setText("提现单位：" + info.note);
                            }
                        } else {
                            showMessage(re.getMsg());
                            finish();
                        }

                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败！");
                        finish();
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RebateAccountAction userAction = new RebateAccountAction();
                    ResponseInfo re = userAction.createCodeById(takemoneyqrcodeid);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    /**
     * 获取数据
     */
    public void getDate(final String money, final String distributorId) {
        final CustomDialog dialog = new CustomDialog(WithdrawCashViewActivity.this, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        WithdrawCashViewResponseModel model = (WithdrawCashViewResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            WithdrawCashViewResponseModel.TakemoneyInfo info = model.data.takemoneyinfo;
                            mId  = info.id;
                            ((TextView) findViewById(R.id.wallet_withdraw_cash_view_withdraw)).setText("提现单位：" + model.data.distributorinfo.name);
                            if (info != null) {
                                ImageView imageView = (ImageView) findViewById(R.id.wallet_withdraw_cash_view_qrcodepicture);
                                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + info.qrcodepicture, imageView);
                                ((TextView) findViewById(R.id.withdraw_cash_view_takemoneycode)).setText("验证码：" + info.takemoneycode);
                                ((TextView) findViewById(R.id.withdraw_cash_view_money)).setText(info.money);
                                ((TextView) findViewById(R.id.withdraw_cash_view_validexpiredtime)).setText("有效期：" + info.validexpiredtime);
                                ((TextView) findViewById(R.id.wallet_withdraw_cash_view_note)).setText("提现单位：" + info.note);
                            }
                        } else {
                            showMessage(model.getMsg());
                            finish();
                        }

                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败！");
                        finish();
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RebateAccountAction action = new RebateAccountAction();
                    WithdrawCashViewResponseModel re = action.getWithdrawCashView(money, distributorId);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    /**
     * 删除数据
     */
    public void delect(final String id) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
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
                            showMessage(re.getMsg());
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
                    RebateAccountAction userAction = new RebateAccountAction();
                    ResponseInfo re = userAction.delectTakeMoneyCodeId(id);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
        dialog.setText("删除后提现二维码将会失效");
        dialog.setText2("如需提现请重新生成二维码");
        dialog.setConfirmBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        }, "取消");
        dialog.setCancelBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if ("".equals(mId)) {
                    showMessage("删除失败！");
                } else {
                    delect(mId);
                }
            }
        }, "删除");
        dialog.show();
    }
}
