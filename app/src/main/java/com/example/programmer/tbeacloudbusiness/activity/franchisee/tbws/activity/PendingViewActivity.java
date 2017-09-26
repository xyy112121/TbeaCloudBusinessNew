package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.activity;

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
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 待处理详情
 */

public class PendingViewActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_view_pendind);
        initTopbar("预约详情", "取消", this);
        getDate();
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
                        if (re.isSuccess()){
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(re.data);
                            PendingInfoResponseModel model = gson.fromJson(json, PendingInfoResponseModel.class);
                            if(model.electricalcheckinfo != null){
                                PendingInfoResponseModel.ElectricalcheckinfoBean info = model.electricalcheckinfo;
                                setViewText(R.id.sr_view_personName,info.subscribeuser);
                                setViewText(R.id.sr_view_code,info.subscribecode);
                                setViewText(R.id.sr_view_time,info.subscribetime);
                                setViewText(R.id.sr_view_VoucherCode,info.vouchercode);
                                setViewText(R.id.sr_view_customername,info.customername);
                                setViewText(R.id.sr_view_customermobile,info.customermobile);
                                setViewText(R.id.sr_view_customerAddr,info.customeraddress);
                                setViewText(R.id.sr_view_note,info.subscribenote);
                            }

                            if(model.orderinfo != null){
                                PendingInfoResponseModel.OrderinfoBean info = model.orderinfo;
                                setViewText(R.id.sr_view_orderCompany,info.ordercompany);
                                setViewText(R.id.sr_view_orderTime,info.ordertime);
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
                    SubscribeAction action = new SubscribeAction();
                    String id = getIntent().getStringExtra("id");
                    ResponseInfo re = action.getPendingInfo(id);
                    if(re == null){
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }else {
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

}
