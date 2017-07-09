package com.example.programmer.tbeacloudbusiness.activity.tbea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司简介/新闻资讯
 */

public class CompanyIntroActivity extends BaseActivity {
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private List<FrameLayout> mListLayout = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbea_company_intro);

        initTopLayout();
        listener();
    }

    /**
     * 初始化顶部点击菜单
     */
    private void initTopLayout() {
        String flag = getIntent().getStringExtra("flag");
         String[] topTexts = new String[]{"公司介绍", "企业文化", "企业责任"};
        initTopbar("公司简介");
        if("new".equals(flag)){
            initTopbar("新闻资讯");
            topTexts = new String[]{"公司动态", "行业新闻", "家装资讯"};
        }

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;

        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.company_intro_top_layout);
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
//                    swithFragment();
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

    private void listener(){

    }
}
