package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.RealNameAuthenticationDistributorResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.CompletionDataActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.RealNameAuthenticationActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证—商家分销商
 */

public class RealNameAuthenticationDistributorActivity extends BaseActivity {
    private String mState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_authentication_distributor);
        ButterKnife.bind(this);
        initTopbar("实名认证");
        getDate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }

    /**
     * 获取数据
     */
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
                            RealNameAuthenticationDistributorResponseModel model = gson.fromJson(json, RealNameAuthenticationDistributorResponseModel.class);
                            RealNameAuthenticationDistributorResponseModel.CompanyidentifyinfoBean info = model.companyidentifyinfo;
                            ((TextView) findViewById(R.id.identification_companyName)).setText(info.companyname);
                            ((TextView) findViewById(R.id.identification_companyCode)).setText(info.companylisencecode);
                            ((TextView) findViewById(R.id.identification_companyAddr)).setText(info.companyaddress);
                            ((TextView) findViewById(R.id.identification_personName)).setText(info.masterperson);
                            ((TextView) findViewById(R.id.identification_cardId)).setText(info.masterpersoncardid);
                            ImageView iv = (ImageView) findViewById(R.id.identification_image);
                            TextView tittleView = (TextView) findViewById(R.id.identification_title);
                            mState = info.identifystatusid;
                            ShareConfig.setConfig(mContext, Constants.whetheridentifiedid, mState);
                            if ("notidentify".equals(info.identifystatusid) || "identifyfailed".equals(info.identifystatusid)) {//没有通过认证
                                iv.setImageResource(R.drawable.icon_my_relaname_unapprove);
                                tittleView.setText("你未通过实名认证");

                            } else if ("identifying".equals(info.identifystatusid)) {//正在认证
                                iv.setImageResource(R.drawable.icon_my_relaname_audit);
                                tittleView.setText("证件审核中");
                            } else {//通过
                                iv.setImageResource(R.drawable.icon_my_relaname_verified);
                                tittleView.setText("你已通过实名认证");
                            }
                            ((TextView) findViewById(R.id.identification_state)).setText(info.identifystatus);

                        } else {
                            ToastUtil.showMessage(re.getMsg());
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
                    UserAction userAction = new UserAction();
                    ResponseInfo re = userAction.getDentifiedInfo();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }


    @OnClick(R.id.identification_state)
    public void onViewClicked() {
//        if ("notidentify".equals(mState)) {//没有通过认证
        if ("identifyfailed".equals(mState)) {//没有通过认证
            startActivity(new Intent(mContext, RealNameAuthenticationFailActivity.class));

        } else {//已通过和审核中的，就显示认证信息
            startActivity(new Intent(mContext, RealNameAuthenticationActivity.class));
        }
    }
}
