package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.PlumberManageLoginStatisticsActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 会议签到列表
 */

public class PlumberMeetingSignInListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mNumberLists;//会议编码
    private List<KeyValueBean> mRegionLists;//区域
    private List<KeyValueBean> mDateLists;//时间

    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private String mFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_sign_in_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mFlag = getIntent().getStringExtra("flag");
        View topView = findViewById(R.id.plumber_meeting_sign_top1);
        View topView2 = findViewById(R.id.plumber_meeting_sign_top2);
        if ("meetingSignIn".equals(mFlag)) {
            topView.setVisibility(View.GONE);
            initTopbar("会议签到");
        } else {
            topView2.setVisibility(View.GONE);
            initTopbar("水电工签到列表");
        }

        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();
        initDate();


        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mNumberLists, "默认排序", "用户");
        if ("meetingSignIn".equals(mFlag)) {
            addItem(expandTabView, mRegionLists, "全部区域", "区域");
        } else {
            addItem(expandTabView, mRegionLists, "默认排序", "累计签到");
        }

        addItem(expandTabView, mDateLists, "默认排序", "时间");
    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if ("Custom".equals(key)) {//时间自定义
                    Intent intent = new Intent(mContext, DateSelectActivity.class);
                    startActivity(intent);
                }
                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });
        int displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();//屏幕的宽

        if ("用户".equals(defaultShowText)) {
            double wid = displayWidth / 2.8;
            int width = (int) wid;
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView, width, Gravity.LEFT);
        } else if ("时间".equals(defaultShowText)) {
            double wid = displayWidth / 2.5;
            int width = (int) wid;
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView, width, Gravity.RIGHT);
        } else {
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<Object> mList = new ArrayList<>();
//
//        public List<CheckBox> ckList = new ArrayList<>();

        /**
         * 构造函数
         *
         * @param context android上下文环境
         */
        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(
                    R.layout.activity_plumber_meeting_sign_in_list_item, null);
            if (!"meetingSignIn".equals(mFlag)) {
                ((TextView) view.findViewById(R.id.scanCode_history_state_item_text2)).setText("12");
                ((TextView) view.findViewById(R.id.scanCode_history_state_item_text2)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, PlumberManageLoginStatisticsActivity.class);
                    startActivity(intent);
                }
            });

            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<Object> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (expandTabView != null) {
            expandTabView.onExpandPopView();
        }
    }

    private void initDate() {
        try {
            if ("meetingSignIn".equals(mFlag)) {
                mRegionLists = new ArrayList<>();
                mRegionLists.add(new KeyValueBean("", "全部区域"));
                mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));
            } else {
                mRegionLists = new ArrayList<>();
                mRegionLists.add(new KeyValueBean("", "默认排序"));
                mRegionLists.add(new KeyValueBean("big", "从大到小"));
                mRegionLists.add(new KeyValueBean("small", "从小到大"));
            }


            mDateLists = new ArrayList<>();
            mDateLists.add(new KeyValueBean("", "默认排序"));
            mDateLists.add(new KeyValueBean("PositiveSequence", "正序"));
            mDateLists.add(new KeyValueBean("InvertedOrder", "倒序"));
            mDateLists.add(new KeyValueBean("Custom", "自定义"));

            mNumberLists = new ArrayList<>();
            mNumberLists.add(new KeyValueBean("", "全部用户"));
            mNumberLists.add(new KeyValueBean("user1", "隶属分销商"));
            mNumberLists.add(new KeyValueBean("user2", "旗下水电工"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
