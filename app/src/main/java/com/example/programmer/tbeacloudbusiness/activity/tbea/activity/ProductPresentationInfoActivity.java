package com.example.programmer.tbeacloudbusiness.activity.tbea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MainActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MessageTypeListActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.action.PublicAction;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.JsToJava;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.NetUrlResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情
 */

public class ProductPresentationInfoActivity extends BaseActivity implements View.OnClickListener {
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private CustomPopWindow mCustomPopWindow;
    private WebView mWebView;
    private ScrollView mScrollView;
    private String mUrl;
    private String mId;
    private CustomDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_presentation_info);
        initTopbar("产品详情", this, R.drawable.icon_morepointwhite);
        mId = getIntent().getStringExtra("id");
        initView();
        initTopLayout();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.product_presentation_top_wb);
        mScrollView = (ScrollView) findViewById(R.id.product_presentation_top_sl);
        mDialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        mDialog.setText("加载中...");
        mDialog.show();
        initWebView();
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
                            String url = mUrl + "tbeaproductdescription?productid=" + mId;
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

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //启用支持javascript
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);//解决图片加载不出来的问题
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);//允许DCOM
        mWebView.addJavascriptInterface(new JsToJava(this), "Android");
    }

    private void showWebView(String url) {
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
    }


    @Override
    public void onClick(View v) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.acitivity_pop_menu, null);
        contentView.findViewById(R.id.menu3_view).setVisibility(View.VISIBLE);
        contentView.findViewById(R.id.menu3).setVisibility(View.VISIBLE);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()
                .showAsDropDown(v, -200, -30);
    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.menu2://首页
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        MyApplication.instance.exit();
                        break;
                    case R.id.menu1://消息
                        intent = new Intent(mContext, MessageTypeListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu3://分享
//                        intent = new Intent(mContext, MessageListActivity.class);
//                        startActivity(intent);
                        break;
                }

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
    }

    /**
     * 初始化顶部点击菜单
     */
    private void initTopLayout() {
        String[] topTexts = new String[]{"图片信息", "产品参数"};
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
                    mWebView.setVisibility(View.VISIBLE);
                    mScrollView.setVisibility(View.GONE);
                    String url = "";
                    if (index == 0) {
                        url = mUrl + "tbeaproductdescription?productid=" + mId;
                    } else if (index == 1) {
                        url = mUrl + "tbeaproductparameter?productid=" + mId;
                    }
                    showWebView(url);
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
