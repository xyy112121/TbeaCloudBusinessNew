package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info.PendingInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_view_pendind);
        ButterKnife.bind(this);
        if("finised".equals(getIntent().getStringExtra("flag"))){
            initTopbar("预约详情");
            findViewById(R.id.sr_view_sendOrders).setVisibility(View.GONE);
        }else {
            initTopbar("预约详情", "取消", this);
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
                                PendingInfoResponseModel.ElectricalcheckinfoBean info = model.electricalcheckinfo;
                                setViewText(R.id.sr_view_personName, info.subscribeuser);
                                setViewText(R.id.sr_view_code, info.subscribecode);
                                setViewText(R.id.sr_view_time, info.subscribetime);
                                mCode = info.vouchercode;
                                setViewText(R.id.sr_view_VoucherCode, info.vouchercode);
                                setViewText(R.id.sr_view_customername, info.customername);
                                setViewText(R.id.sr_view_customermobile, info.customermobile);
                                setViewText(R.id.sr_view_customerAddr, info.customeraddress);
                                setViewText(R.id.sr_view_note, info.subscribenote);
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
            case R.id.sr_view_sendOrders:
                intent = new Intent(mContext, SendOrdersPersonSelectActivity.class);
                intent.putExtra("id", mId);
                startActivity(intent);
                break;
        }
    }
}
