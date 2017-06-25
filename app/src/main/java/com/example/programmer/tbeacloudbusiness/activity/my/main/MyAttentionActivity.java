package com.example.programmer.tbeacloudbusiness.activity.my.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.attention.CommodityFragment;
import com.example.programmer.tbeacloudbusiness.activity.my.main.attention.StoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的关注
 */
public class MyAttentionActivity extends TopActivity implements View.OnClickListener {
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private int mIndex = 0;
    private int mIndex2 = 0;//前一次点击的下标
    private CommodityFragment mCommodityFragment;
    private StoreFragment mStoreFragment;
    private Fragment mCustomFragment;
    public View mSelectAllLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        initTopbar("我的关注", "编辑", this);
        mSelectAllLayout = findViewById(R.id.attention_select_all_layout);
        initTopLayout();
    }

    /**
     * 初始化顶部点击菜单
     */
    private void initTopLayout() {
        String[] topTexts = new String[]{"商品", "店铺", "个人"};

        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.attention_top_layout);
        for (int i = 0; i < topTexts.length; i++) {
            final int index = i;
            final FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.fragment_company_top_layout_item, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth / topTexts.length, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(lp);
            TextView t = (TextView) layout.findViewById(R.id.fragment_company_top_tv);
            t.setText(topTexts[i]);
            if (i == 0) {
                ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                swithFragment();
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
                    swithFragment();
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

    private void swithFragment() {
        if (mIndex == 0) {
            if (mCommodityFragment == null) {
                mCommodityFragment = new CommodityFragment();
            }
            mCustomFragment = mCommodityFragment;
        } else if (mIndex == 1 || mIndex == 2) {
            if (mStoreFragment == null) {
                mStoreFragment = new StoreFragment();
            }
            mCustomFragment = mStoreFragment;
        }
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, mCustomFragment);
        t.commit();
    }

    @Override
    public void onClick(View v) {
        if (mIndex == 0) {
            mCommodityFragment.refresh();
        }else if(mIndex == 1){
            mStoreFragment.refresh();
        }

    }
}

