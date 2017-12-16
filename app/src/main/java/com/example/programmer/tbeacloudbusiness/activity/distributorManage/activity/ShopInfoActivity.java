package com.example.programmer.tbeacloudbusiness.activity.distributorManage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributorManage.action.DistributorManageAction;
import com.example.programmer.tbeacloudbusiness.activity.distributorManage.model.DBShopInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

/**
 * 商家资料
 */

public class ShopInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_shop_info);
        initTopbar("");
        getData();
    }

    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo response = (ResponseInfo) msg.obj;
                            if (response.isSuccess()) {
                                if (response.data != null) {
                                    String json = mGson.toJson(response.data);
                                    DBShopInfoResponseModel model = mGson.fromJson(json, DBShopInfoResponseModel.class);

                                    DBShopInfoResponseModel.DistributorinfoBean info = model.distributorinfo;
                                    setTextView(R.id.distributor_shop_companyname_tv, info.companyname);
                                    setTextView(R.id.distributor_shop_mastername_tv, info.mastername);
                                    setTextView(R.id.distributor_shop_mastersex_tv, info.mastersex);
                                    setTextView(R.id.distributor_shop_masterage_tv, info.masterage);
                                    setTextView(R.id.distributor_shop_mobilenumber_tv, info.mobilenumber);
                                    setTextView(R.id.distributor_shop_identifystatus_tv, info.identifystatus);
                                    setTextView(R.id.distributor_shop_uplevelcompanyname_tv, info.uplevelcompanyname);
                                    setTextView(R.id.distributor_shop_electriciannumber_tv, info.electriciannumber);
                                    setTextView(R.id.distributor_shop_totletakemoney_tv, "￥" + info.totletakemoney);
                                    setTextView(R.id.distributor_shop_servicemeetingnumber_tv, info.servicemeetingnumber);
                                    setTextView(R.id.distributor_shop_onlineshop_tv, info.onlineshop);
                                    setTextView(R.id.distributor_shop_introduce_tv, info.introduce);
                                }

                            } else {
                                showMessage(response.getMsg());
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
                        DistributorManageAction action = new DistributorManageAction();
                        String distributorid = getIntent().getStringExtra("id");
                        ResponseInfo model = action.getShopInfo(distributorid);
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

    private void setTextView(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }
}
