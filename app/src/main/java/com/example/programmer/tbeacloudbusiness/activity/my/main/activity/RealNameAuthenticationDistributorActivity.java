package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.RealNameAuthenticationDistributorResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 实名认证—商家分销商
 */

public class RealNameAuthenticationDistributorActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_authentication_distributor);
        initTopbar("实名认证");
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
                            ((TextView) findViewById(R.id.identification_state)).setText("已通过");

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
}
