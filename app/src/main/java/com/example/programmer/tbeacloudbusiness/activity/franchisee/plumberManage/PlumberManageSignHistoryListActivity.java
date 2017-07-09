package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 签到历史
 */

public class PlumberManageSignHistoryListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mUserLists;//用户
    private List<KeyValueBean> mRegionLists;//区域
    private List<KeyValueBean> mDateLists;//时间

    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_sign_in_list);
        initTopbar("会议签到");
        mContext = this;
        initView();
    }

    private  void  initView(){
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mUserLists, "默认排序", "用户");
        addItem(expandTabView, mRegionLists, "全部区域", "区域");
        addItem(expandTabView, mDateLists, "默认排序", "时间");

        findViewById(R.id.sign_history_person_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlumberManagePersonViewActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext,R.color.blue));
                if ("Custom".equals(key)) {//时间自定义
                    Intent intent = new Intent(mContext, DateSelectActivity.class);
                    startActivity(intent);
                }
                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });
//        int displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();//屏幕的宽

            expandTabView.addItemToExpandTab(defaultShowText, popOneListView);

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
                    R.layout.activity_plumber_manage_sign_history_list_item, null);


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
            mRegionLists = new ArrayList<>();
            mRegionLists.add(new KeyValueBean("", "全部区域"));
            mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));

            mDateLists = new ArrayList<>();
            mDateLists.add(new KeyValueBean("", "默认排序"));
            mDateLists.add(new KeyValueBean("PositiveSequence", "正序"));
            mDateLists.add(new KeyValueBean("InvertedOrder", "倒序"));
            mDateLists.add(new KeyValueBean("Custom", "自定义"));

            mUserLists = new ArrayList<>();
            mUserLists.add(new KeyValueBean("", "默认排序"));
            mUserLists.add(new KeyValueBean("user1", "用户1"));
            mUserLists.add(new KeyValueBean("user2", "用户2"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
