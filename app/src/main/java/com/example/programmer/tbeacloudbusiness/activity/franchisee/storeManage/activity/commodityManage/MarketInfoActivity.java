package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.commodityManage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.MarketInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 销售详情
 */

public class MarketInfoActivity extends BaseActivity implements View.OnClickListener {
    private String mCommodityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info);
        ButterKnife.bind(this);
        initTopbar("销售详情", "商品编辑", this);
        mCommodityId = getIntent().getStringExtra("commodityId");
        getData();
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        try {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("请等待...");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            MarketInfoResponseModel model = (MarketInfoResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.commodityinfo != null) {
                                    MarketInfoResponseModel.DataBean.CommodityinfoBean obj = model.data.commodityinfo;
                                    ImageView iv = getViewById(R.id.market_info_thumbpicture);
                                    ImageLoader.getInstance().displayImage(obj.thumbpicture, iv);

                                    ((TextView) findViewById(R.id.market_info_name)).setText(obj.commodityname);
                                    ((TextView) findViewById(R.id.market_info_price)).setText(obj.price + "");
                                    ((TextView) findViewById(R.id.market_info_publishtime)).setText("发布时间：" + obj.publishtime);
                                }

                                if (model.data.saleinfo != null) {
                                    ((PublishTextRowView) findViewById(R.id.market_info_be_sold)).setValueText(model.data.saleinfo.salenumber + "");
                                    ((PublishTextRowView) findViewById(R.id.market_info_be_rate)).setValueText(model.data.saleinfo.evaluatenumber + "");
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
                        StoreManageAction action = new StoreManageAction();
                        MarketInfoResponseModel model = action.getMarketInfo(mCommodityId);
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, StoreCommodityManageAddActivity.class);
        intent.putExtra("commodityId", mCommodityId);
        intent.putExtra("flag", "edit");
        startActivity(intent);

    }

    @OnClick({R.id.market_info_be_sold, R.id.market_info_be_rate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.market_info_be_sold:
                Intent intent = new Intent(mContext, MarketInfoBeSoldActivity.class);
                intent.putExtra("commodityId", mCommodityId);
                startActivity(intent);
                break;
            case R.id.market_info_be_rate:
                intent = new Intent(mContext, MarketInfoBeRateActivity.class);
                intent.putExtra("commodityId",mCommodityId);
                startActivity(intent);
                break;
        }
    }
}
