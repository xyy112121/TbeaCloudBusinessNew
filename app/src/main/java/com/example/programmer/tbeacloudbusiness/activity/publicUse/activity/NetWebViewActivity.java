package com.example.programmer.tbeacloudbusiness.activity.publicUse.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.action.PublicAction;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.NetUrlResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.SearchResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * webView公用界面（需要先去服务器获取url）
 */

public class NetWebViewActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView mWebView;

    CustomDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_view_plan);
        ButterKnife.bind(this);
        mDialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        mDialog.setText("加载中...");
        mDialog.show();
        initTopbar(getIntent().getStringExtra("title"));
        getDate();
    }

    /**
     * 获取url
     */
    public void getDate() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        NetUrlResponseModel re = (NetUrlResponseModel) msg.obj;
                        if (re.isSuccess() && re.data != null) {
                            String parameter = getIntent().getStringExtra("parameter");
                            String url = re.data.url+parameter;
                            showWebView(url);
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
                    PublicAction userAction = new PublicAction();
                    NetUrlResponseModel re = userAction.getUrl();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    private void showWebView(String url) {
        WebSettings settings = mWebView.getSettings();
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //启用支持javascript
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);//解决图片加载不出来的问题
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);//允许DCOM

        mWebView.loadUrl(url);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mDialog.dismiss();
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient());
    }
}
