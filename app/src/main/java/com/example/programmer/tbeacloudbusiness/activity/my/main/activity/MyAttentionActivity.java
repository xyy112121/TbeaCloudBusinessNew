package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.action.MyAction;
import com.example.programmer.tbeacloudbusiness.activity.my.main.attention.CommodityFragment;
import com.example.programmer.tbeacloudbusiness.activity.my.main.attention.PersonFragment;
import com.example.programmer.tbeacloudbusiness.activity.my.main.attention.StoreFragment;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AttentionCommodityResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的关注
 */
public class MyAttentionActivity extends BaseActivity implements View.OnClickListener {
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private int mIndex = 0;
    private int mIndex2 = 0;//前一次点击的下标
    private CommodityFragment mCommodityFragment;
    private StoreFragment mStoreFragment;
    private PersonFragment mPersonFragment;
    private Fragment mCustomFragment;
    public View mSelectAllLayout;
    public TextView mRightView;
    public String mId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        mId = getIntent().getStringExtra("id");
        if(mId == null){
            initTopbar("我的关注", "编辑", this);
        }else {
            initTopbar("我的关注");
        }
        mSelectAllLayout = findViewById(R.id.attention_select_all_layout);
        mRightView = (TextView) findViewById(R.id.top_right_text);
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
        } else if (mIndex == 1) {
            if (mStoreFragment == null) {
                mStoreFragment = new StoreFragment();
            }
            mCustomFragment = mStoreFragment;
        } else if (mIndex == 2) {
            if (mPersonFragment == null) {
                mPersonFragment = new PersonFragment();
            }
            mCustomFragment = mPersonFragment;
        }
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, mCustomFragment);
        t.commit();
    }

    @Override
    public void onClick(View v) {
        if (mIndex == 0) {
            mCommodityFragment.refresh();
        } else if (mIndex == 1) {
            mStoreFragment.refresh();
        } else if (mIndex == 2) {
            mPersonFragment.refresh();
        }

    }

    /**
     * 取消关注
     */
    public void cancelAttention(final String ids) {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo model = (ResponseInfo) msg.obj;
                            if (model.isSuccess()) {
                                if (mIndex == 0) {
                                    mCommodityFragment.refreshDate();
                                } else if (mIndex == 1) {
                                    mStoreFragment.refreshDate();
                                } else if (mIndex == 21) {
                                    mPersonFragment.refreshDate();
                                }
                            } else {
                                showMessage(model.getMsg());
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
                        MyAction action = new MyAction();
                        ResponseInfo model = action.cancelAttention(ids);
                        handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                    } catch (Exception e) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

