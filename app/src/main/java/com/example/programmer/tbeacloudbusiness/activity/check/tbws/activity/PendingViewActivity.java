package com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.info.PendingInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.SuccessActivity;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.model.EventCity;
import com.example.programmer.tbeacloudbusiness.model.EventFlag;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待处理详情
 */

public class PendingViewActivity extends BaseActivity implements View.OnClickListener {
    private String mCode;
    private String mId;
    PendingInfoResponseModel.ElectricalcheckinfoBean mInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ck_sr_view_pendind);
        ButterKnife.bind(this);
        initTopbar("派单详情");
        if ("finised".equals(getIntent().getStringExtra("flag"))) {
            findViewById(R.id.sr_view_sendOrders).setVisibility(View.GONE);
        }
        EventBus.getDefault().register(this);
        getDate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(EventCity event) {
        if (event == null) return;
        if (EventFlag.EVENT_FINISED_ACTIVITY1.equals(event.getEventFlag())) {
            finish();
        }
    }

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
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(re.data);
                            PendingInfoResponseModel model = gson.fromJson(json, PendingInfoResponseModel.class);
                            if (model.electricalcheckinfo != null) {
                                mInfo = model.electricalcheckinfo;
                                setViewText(R.id.sr_view_personName, mInfo.subscribeuser);
                                setViewText(R.id.sr_view_code, mInfo.subscribecode);
                                setViewText(R.id.sr_view_time, mInfo.subscribetime);
                                mCode = mInfo.vouchercode;
                                setViewText(R.id.sr_view_VoucherCode, mInfo.vouchercode);
                                setViewText(R.id.sr_view_customername, mInfo.customername);
                                setViewText(R.id.sr_view_customermobile, mInfo.customermobile);
                                setViewText(R.id.sr_view_customerAddr, mInfo.customeraddress);
                                setViewText(R.id.sr_view_note, mInfo.subscribenote);
                                setViewText(R.id.sr_view_assignTime, mInfo.assigntime);
                            }

                            if (model.orderinfo != null) {
                                PendingInfoResponseModel.OrderinfoBean info = model.orderinfo;
                                setViewText(R.id.sr_view_orderCompany, info.ordercompany);
                                setViewText(R.id.sr_view_orderTime, info.ordertime);
                            }
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
                    SubscribeAction action = new SubscribeAction();
                    mId = getIntent().getStringExtra("id");
                    ResponseInfo re = action.getPendingInfo(mId);
                    if (re == null) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    } else {
                        handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    public void setViewText(int id, String text) {
        ((PublishTextRowView) findViewById(id)).setValueText(text);
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.sr_view_orderCommdity, R.id.sr_view_sendOrders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sr_view_orderCommdity:
                Intent intent = new Intent(mContext, CommdityListActivity.class);
                intent.putExtra("code", mCode);
                startActivity(intent);
                break;
            case R.id.sr_view_sendOrders://接单
                showAlert();
                break;
        }
    }

    private void showAlert() {
        View parentLayout = findViewById(R.id.parentLayout);
        final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, "确认提示", "预约编号：" + mInfo.subscribecode, "预约用户：" + mInfo.subscribeuser, "预约时间：" + mInfo.subscribetime, "接单");
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                OrderReceiving();
            }

        });
    }

    /**
     * 接单
     */
    private void OrderReceiving() {
        //确认
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
                        if (re.data != null) {
                            if (re.isSuccess()) {
                                Intent intent = new Intent(mContext, SuccessActivity.class);
                                intent.putExtra("flag", "sendOrder");//派单
                                intent.putExtra("id", mId);
                                startActivity(intent);
                                finish();
                            } else {
                                showMessage(re.getMsg());
                            }
                        } else {
                            showMessage("操作失败！");
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
                    SubscribeAction action = new SubscribeAction();
                    ResponseInfo re = action.OrderReceiving(mId);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }
}
