package com.example.programmer.tbeacloudbusiness.activity.tbea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品详情
 */

public class ProductPresentationInfoActivity extends BaseActivity implements View.OnClickListener{
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private  CustomPopWindow mCustomPopWindow;
    private WebView mWebView;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_presentation_info);
        initTopbar("产品详情",this,R.drawable.icon_morepointwhite);
        initView();
        initTopLayout();
        listener();
    }

    private void initView(){
        mWebView = (WebView)findViewById(R.id.product_presentation_top_wb);
        mScrollView = (ScrollView)findViewById(R.id.product_presentation_top_sl);
    }


    private void listener(){

    }

//    private void handleLogic(View contentView){
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mCustomPopWindow!=null){
//                    mCustomPopWindow.dissmiss();
//                }
//                String showContent = "";
//                switch (v.getId()){
//                    case R.id.menu1:
//                        showContent = "点击 Item菜单1";
//                        break;
//                    case R.id.menu2:
//                        showContent = "点击 Item菜单2";
//                        break;
//                }
//                ToastUtil.showMessage(showContent);
//            }
//        };
//        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
//        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
//    }

    @Override
    public void onClick(View v) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.acitivity_pop_menu,null);
        contentView.findViewById(R.id.menu3_view).setVisibility(View.VISIBLE);
        contentView.findViewById(R.id.menu3).setVisibility(View.VISIBLE);
        //处理popWindow 显示内容
//                handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()
                .showAsDropDown(v,-200,-30);
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

        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.product_presentation_top_layout);
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
                    if(index == 0){
                        mWebView.setVisibility(View.VISIBLE);
                        mScrollView.setVisibility(View.GONE);

                    }else if(index == 1){
                        mWebView.setVisibility(View.GONE);
                        mScrollView.setVisibility(View.VISIBLE);
                    }
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
