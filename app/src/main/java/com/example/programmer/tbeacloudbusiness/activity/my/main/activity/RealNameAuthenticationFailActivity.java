package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.RealNameAuthenticationDistributorResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.RealNameAuthenticationFailResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.CompletionDataActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.RealNameAuthenticationActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.Constants;
import com.example.programmer.tbeacloudbusiness.utils.ShareConfig;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.utils.L;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 实名认证失败
 */

public class RealNameAuthenticationFailActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_authentication_fail);
        ButterKnife.bind(this);
        initTopbar("审核拒绝");
        getDate();
    }

    /**
     * 获取数据
     */
    public void getDate() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess()) {
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(re.data);
                            RealNameAuthenticationFailResponseModel model = gson.fromJson(json, RealNameAuthenticationFailResponseModel.class);
                            LinearLayout parentLayout = (LinearLayout) findViewById(R.id.realname_authentication_fail_layout);
                            if (model.failedreasonlist != null && model.failedreasonlist.size() > 0) {
                                for (int i = 0; i < model.failedreasonlist.size(); i++) {
                                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_realname_authentication_fail_item, null);
                                    ((TextView) layout.findViewById(R.id.fail_item_index)).setText(i+1 + "");
                                    ((TextView) layout.findViewById(R.id.fail_item_info)).setText(model.failedreasonlist.get(i).reason);
                                    parentLayout.addView(layout);
                                }
                            }


                        } else {
                            showMessage(re.getMsg());
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
                    UserAction userAction = new UserAction();
                    ResponseInfo re = userAction.getDentifiedFailInfo();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @OnClick(R.id.identification_finish)
    public void onViewClicked() {
        startActivity(new Intent(mContext, RealNameAuthenticationActivity.class));
        finish();
    }
}
