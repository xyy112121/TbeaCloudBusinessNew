package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.CodeImageResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 返利二维码生成-查看-二维码
 */

public class ScanCodeImageViewActivity extends BaseActivity {

    @BindView(R.id.scan_code_image_url_iv)
    ImageView mPicturlIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_image);
        initTopbar("二维码查看");
        ButterKnife.bind(this);
        getData();
    }


    private void getData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            dialog.dismiss();
                            ResponseInfo response = (ResponseInfo) msg.obj;
                            if (response.isSuccess()) {
                                Gson gson = new GsonBuilder().serializeNulls().create();
                                String json = gson.toJson(response.data);
                                CodeImageResponseModel model = gson.fromJson(json, CodeImageResponseModel.class);
                                if (model.qrcodepictureinfo != null) {
                                    CodeImageResponseModel.QrcodepictureinfoBean info = model.qrcodepictureinfo;
                                    setViewText(R.id.scan_code_image_code_tv, info.qrcode);
                                    setViewText(R.id.scan_code_image_money_tv, info.rebatemoney);
                                    setViewText(R.id.scan_code_image_status_tv, info.activitystatus);
                                    ImageLoader.getInstance().displayImage(info.qrcodepicture, mPicturlIv);
                                }
                            } else {
                                showMessage(response.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            showMessage("操作失败！");
                            dialog.dismiss();
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ScanCodeAction action = new ScanCodeAction();
                        String code = getIntent().getStringExtra("rebateqrcode");
                        ResponseInfo model = action.getCodeImage(code);
                        if (model == null) {
                            handler.sendEmptyMessage(ThreadState.ERROR);
                        } else {
                            handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                        }

                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            showMessage("操作失败！");
            dialog.dismiss();
        }
    }

    private void setViewText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

}