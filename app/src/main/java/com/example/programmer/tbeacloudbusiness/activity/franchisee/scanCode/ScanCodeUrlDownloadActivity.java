package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.UrlDownloadResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 下载链接
 */

public class ScanCodeUrlDownloadActivity extends BaseActivity {
    private String mId;
    private String mExcelFileUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_url_download);
        initTopbar("Excel文件链接");
        ButterKnife.bind(this);
        mId = getIntent().getStringExtra("id");
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
                                UrlDownloadResponseModel model = gson.fromJson(json, UrlDownloadResponseModel.class);
                                if (model.generateinfo != null) {
                                    UrlDownloadResponseModel.Generateinfo info = model.generateinfo;
                                    setViewText(R.id.url_dowmload_companyname_tv, info.companyname);
                                    setViewText(R.id.url_dowmload_commodityname_tv, info.commodityname);
                                    setViewText(R.id.url_dowmload_commodityspecificationandmodel_tv, info.commodityspecificationandmodel);
                                    setViewText(R.id.url_dowmload_count_tv, info.count + "");
                                    setViewText(R.id.url_dowmload_money_tv, info.money);
                                    setViewText(R.id.url_dowmload_totlemoney_tv, info.totlemoney);
                                    setViewText(R.id.url_dowmload_createtime_tv, info.createtime);
                                    mExcelFileUrl = info.excelfileurl;
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
                        ResponseInfo model = action.getUrlDownload(mId);
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

    @OnClick(R.id.url_dowmload_copy_btn)
    public void onViewClicked() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("copy", mExcelFileUrl);
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
        showMessage("复制成功！");

    }
}
