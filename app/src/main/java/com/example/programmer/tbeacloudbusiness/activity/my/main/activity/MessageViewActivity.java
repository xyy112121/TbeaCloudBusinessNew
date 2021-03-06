package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;


/**
 * Created by cy on 2017/2/10.
 */

public class MessageViewActivity extends BaseActivity {
    private WebView mWebView;
    private String id;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_take_view);
        mContext = this;
        initTopbar("消息详情");
        id = getIntent().getStringExtra("id");
        mWebView = (WebView) findViewById(R.id.web_view);
        final CustomDialog mDialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        mDialog.setText("加载中...");
        mDialog.show();
        WebSettings settings = mWebView.getSettings();
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //启用支持javascript
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);//解决图片加载不出来的问题
        String url = MyApplication.instance.getImgPath() + "enginterface/index.php/Apph5/messagelist?messagecategoryid" + id;
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mDialog.dismiss();
                }
            }
        });
    }
}
