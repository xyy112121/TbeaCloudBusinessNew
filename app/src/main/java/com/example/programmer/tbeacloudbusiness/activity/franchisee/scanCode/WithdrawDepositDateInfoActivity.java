package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 提现详情
 */

public class WithdrawDepositDateInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_withdraw_deposit_view);
        initTopbar("提现详情");
        getData();
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        try {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("加载中...");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            WithdrawDepositDateInfoResponseModel model = (WithdrawDepositDateInfoResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                String imagePath = MyApplication.instance.getImgPath();
//                                WithdrawDepositDateInfoResponseModel.OwnerInfo ownerInfo = model.data.ownerinfo;
                                WithdrawDepositDateInfoResponseModel.PayeeInfo ownerInfo = model.data.payeeinfo;
                                WithdrawDepositDateInfoResponseModel.OwnerInfo payeeInfo = model.data.ownerinfo;
                                WithdrawDepositDateInfoResponseModel.PayMoneyInfo payMoneyInfo = model.data.paymoneyinfo;
                                WithdrawDepositDateInfoResponseModel.TakeMoneyCodeInfo takeMoneyCodeInfo = model.data.takemoneycodeinfo;

                                if (ownerInfo != null) {
                                    LinearLayout layout = (LinearLayout) findViewById(R.id.withdrow_deposit_view_ownerInfo);
                                    ((TextView) findViewById(R.id.person_info_name)).setText(ownerInfo.personname);
                                    layout.findViewById(R.id.person_info_personjobtitle).setVisibility(View.GONE);
                                    ImageLoader.getInstance().displayImage(imagePath + ownerInfo.thumbpicture, ((ImageView) layout.findViewById(R.id.person_info_head)));
//                                    ImageLoader.getInstance().displayImage(imagePath + ownerInfo.persontypeicon, ((ImageView) layout.findViewById(R.id.person_info_personjobtitle)));
                                }

                                if (payeeInfo != null) {
                                    LinearLayout layout = (LinearLayout) findViewById(R.id.withdrow_deposit_view_payeeInfo);
                                    ((TextView)layout. findViewById(R.id.person_info_name)).setText(payeeInfo.personname);
                                    ((TextView)layout. findViewById(R.id.person_info_companyname)).setText(payeeInfo.companyname);
                                    layout. findViewById(R.id.person_info_right).setVisibility(View.GONE);
                                    ImageLoader.getInstance().displayImage(imagePath + payeeInfo.thumbpicture, ((ImageView) layout.findViewById(R.id.person_info_head)));
                                    ImageLoader.getInstance().displayImage(imagePath + payeeInfo.persontypeicon, ((ImageView) layout.findViewById(R.id.person_info_personjobtitle)));
                                }

                                if (takeMoneyCodeInfo != null) {
                                    ((TextView) findViewById(R.id.withdrow_deposit_view_takemoneycode)).setText(takeMoneyCodeInfo.takemoneycode);
                                    ((TextView) findViewById(R.id.withdrow_deposit_view_generatecodetime)).setText(takeMoneyCodeInfo.generatecodetime);
                                    ((TextView) findViewById(R.id.withdrow_deposit_view_money)).setText(takeMoneyCodeInfo.money);
                                }

                                if (payMoneyInfo != null) {
                                    ((TextView) findViewById(R.id.withdrow_deposit_view_paytime)).setText(payMoneyInfo.paytime);
                                    ((TextView) findViewById(R.id.withdrow_deposit_view_payplace)).setText(payMoneyInfo.payplace);

                                    LinearLayout layout = (LinearLayout) findViewById(R.id.withdrow_deposit_view_paymoneyinfo);
                                    ((TextView) layout.findViewById(R.id.person_info_name)).setText(payMoneyInfo.personname);
                                    ImageLoader.getInstance().displayImage(imagePath + payMoneyInfo.thumbpicture, ((ImageView) layout.findViewById(R.id.person_info_head)));
                                    ImageLoader.getInstance().displayImage(imagePath + payMoneyInfo.persontypeicon, ((ImageView) layout.findViewById(R.id.person_info_personjobtitle)));
                                    ((TextView) layout.findViewById(R.id.person_info_companyname)).setText(payMoneyInfo.companyname);
                                    layout.findViewById(R.id.person_info_right).setVisibility(View.GONE);
                                }
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
                        String id = getIntent().getStringExtra("takeMoneyId");
                        ScanCodeAction action = new ScanCodeAction();
                        WithdrawDepositDateInfoResponseModel model = action.getWithdrawDepositDateInfo(id);
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
