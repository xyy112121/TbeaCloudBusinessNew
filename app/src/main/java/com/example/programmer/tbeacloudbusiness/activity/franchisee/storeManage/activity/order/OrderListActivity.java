package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.order;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.action.PublicAction;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.NetUrlResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单管理
 */

public class OrderListActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView mWebView;

    private String mUrl;
    private String mId;
    private CustomDialog mDialog;
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private List<FrameLayout> mListLayout = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_view_plan);
        ButterKnife.bind(this);
        initTopbar("订单管理");
        mId = MyApplication.instance.getUserId();
        initView();
        initTopLayout();
    }

    private void initView() {
        mDialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        mDialog.setText("加载中...");
        mDialog.show();
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
                            mUrl = re.data.url;
                            String url = mUrl + "orderlist_all?userid=" + mId;
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

        mWebView.setWebViewClient(new MyWebViewClient());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith("拦截字段")) {
                mWebView.evaluateJavascript("sendparamtertoapp()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        System.out.println(value);
                    }
                });
                return true;
            }
            mWebView.loadUrl(url);
            return true;
        }
    }

//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (url.contains("sendparamtertoapp")) {
////                String phone = url.substring(url.indexOf("_") + 1, url.length());
////                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
////                startActivity(intent);
//                return true;
//            }
//            return true;
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode,
//                                    String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//        }
//    }


    /**
     * 初始化顶部点击菜单
     */
    private void initTopLayout() {
        String[] topTexts = new String[]{"全部", "待付款", "待发货", "待收货", "已完成"};
        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.product_presentation_top_layout);
        for (int i = 0; i < topTexts.length; i++) {
            final int index = i;
            final FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.fragment_company_top_layout_item, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / topTexts.length, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(lp);
            TextView t = (TextView) layout.findViewById(R.id.fragment_company_top_tv);
            t.setText(topTexts[i]);
            if (i == 0) {
                ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.blue));
//
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIndex2 = mIndex;
                    mIndex = index;
                    if (mIndex2 != -1 && mIndex != mIndex2) {
                        ((TextView) view.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                        setViewColor();
                    }
                    mDialog.show();
                    String url = "";
                    switch (index) {
                        case 0://全部
                            url = mUrl + "orderlist_all?userid=" + mId;
                            break;
                        case 1://待付款
                            url = mUrl + "orderlist_needpay?userid=" + mId;
                            break;
                        case 2://待发货
                            url = mUrl + "orderlist_havepayed?userid=" + mId;
                            break;
                        case 3://待收货
                            url = mUrl + "orderlist_delivering?userid=" + mId;
                            break;
                        case 4://已完成
                            url = mUrl + "orderlist_finished?userid=" + mId;
                            break;

                    }
                    showWebView(url);
//
                }
            });
            mListLayout.add(layout);
            parentLayout.addView(layout);
        }
    }

    private void setViewColor() {
        FrameLayout layout = mListLayout.get(mIndex2);
        ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
    }

}
