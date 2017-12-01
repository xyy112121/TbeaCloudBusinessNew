package com.example.programmer.tbeacloudbusiness.activity.tbea.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.WebViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.action.TbeaAction;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ContactInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 联系方式
 */

public class ProductPresentationContactInfoActivity extends BaseActivity {


    @BindView(R.id.product_presentation_contact_info_name)
    TextView mNameView;
    @BindView(R.id.product_presentation_contact_info_addr)
    TextView mAddrView;
    @BindView(R.id.product_presentation_contact_info_zipcode)
    TextView mZipcodeView;
    @BindView(R.id.product_presentation_contact_info_platformservicetel)
    TextView mPlatformservicetelView;
    @BindView(R.id.product_presentation_contact_info_fax)
    TextView mFaxView;
    @BindView(R.id.product_presentation_contact_info_delantel)
    TextView mDelantelView;
    @BindView(R.id.product_presentation_contact_info_tbeatel)
    TextView mTbeatelView;

    ContactInfoResponseModel.DataBean.ContactinfoBean mObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_presentation_contact_info);
        ButterKnife.bind(this);
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
                            ContactInfoResponseModel model = (ContactInfoResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                mObj = model.data.contactinfo;
                                mNameView.setText(mObj.name);
                                mAddrView.setText("地址：" + mObj.address);
                                mZipcodeView.setText("邮编：" + mObj.zipcode);
                                mPlatformservicetelView.setText("平台服务电话：" + mObj.platformservicetel);
                                mFaxView.setText("传真：" + mObj.fax);
                                mDelantelView.setText("德缆电话：" + mObj.delantel);
                                mTbeatelView.setText("集团电话：" + mObj.tbeatel);

                            } else {
                                showMessage(model.getMsg());
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
                        TbeaAction action = new TbeaAction();
                        ContactInfoResponseModel model = action.getContactInfo();
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

    private void callPhone(final String number) {
        //单个权限获取
        checkPermission(new CheckPermListener() {
            @Override
            public void Granted() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            }

            @Override
            public void Denied() {
                Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                //检查是否选择了不再提醒
            }
        }, "拨打电话权限", Manifest.permission.CALL_PHONE);

    }

    @OnClick(R.id.product_presentation_contact_info_addr)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, WebViewActivity.class);
        String url = "http://121.42.193.154:6696/index.php/h5forapp/Index/tbeageographicaddress";
        intent.putExtra("url", url);
        intent.putExtra("title", "地图");

        startActivity(intent);
    }


    @OnClick({R.id.product_presentation_contact_info_platformservicetel_layout, R.id.product_presentation_contact_info_delantel_layout, R.id.product_presentation_contact_info_tbeatel_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.product_presentation_contact_info_platformservicetel_layout:
                if("".equals(mObj.platformservicetel)){
                    showMessage("号码为空");
                }else {
                    callPhone(mObj.platformservicetel);
                }

                break;
            case R.id.product_presentation_contact_info_delantel_layout:
                if("".equals(mObj.delantel)){
                    showMessage("号码为空");
                }else {
                    callPhone(mObj.delantel);
                }
                break;
            case R.id.product_presentation_contact_info_tbeatel_layout:
                if("".equals(mObj.tbeatel)){
                    showMessage("号码为空");
                }else {
                    callPhone(mObj.delantel);
                }
                break;
        }
    }
}
