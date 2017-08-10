package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity.rebateAccount.activity;

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
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
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

    public void initView(){
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
                    if(mo > mCanexChangeMoney){
                        ToastUtil.showMessage("不能大于最大提现金额！");
                        return;
                    }
                    Intent intent = new Intent(RebateAccountWithdrawCashActivity.this, WithdrawCashViewActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("distributorid",mDistributorid );
                    startActivity(intent);
                }catch (Exception e){

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
    /**
     * 获取数据
     */
    public void getDate() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        RebateAccountInfoResponseModel model = (RebateAccountInfoResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if(model.data.mymoneyinfo != null){
                                mCanexChangeMoney = model.data.mymoneyinfo.canexchangemoney;
                                ((TextView)findViewById(R.id.rebate_account_withdraw_cash_info)).setText("当前可提现金额：￥"+model.data.mymoneyinfo.canexchangemoney);
                            }
                            if(model.data.firstdistributorinfo != null){
                               RebateAccountInfoResponseModel.FirstDistriButorinfo info = model.data.firstdistributorinfo;
                                mDistributorid = info.id;
                                ((TextView) findViewById(R.id.person_info_name)).setText(info.personname);
                                ((TextView) findViewById(R.id.person_info_companyname)).setText(info.companyname);
                                String imagePath = MyApplication.instance.getImgPath();
                                ImageLoader.getInstance().displayImage(imagePath + info.thumbpicture, ((ImageView) findViewById(R.id.person_info_head)));
                               if(!"".equals(info.persontypeicon)){
                                   ImageLoader.getInstance().displayImage(imagePath + info.persontypeicon, ((ImageView)findViewById(R.id.person_info_personjobtitle)));
                               }

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
                    RebateAccountAction action = new RebateAccountAction();
                    RebateAccountInfoResponseModel re = action.getRebateAccountInfo();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

}
