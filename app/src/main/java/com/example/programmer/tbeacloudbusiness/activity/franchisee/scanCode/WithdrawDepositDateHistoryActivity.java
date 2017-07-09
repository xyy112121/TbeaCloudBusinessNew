package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 提现历史
 */

public class WithdrawDepositDateHistoryActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private  int mPage = 1;
    private int mPagesiz =10 ;
    private Context mContext;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mDateLists;//时间
    private List<KeyValueBean> mPriceLists;//金额

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_withdraw_deposit_history_list);
        initTopbar("提现历史");
        mContext = this;
        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout)findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mDateLists, "默认排序", "时间");
        addItem(expandTabView, mPriceLists, "默认排序", "金额");

    }

    public void addItem(final  ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext,R.color.blue));
                if("Custom".equals(key)){//时间自定义
                    Intent intent = new Intent(mContext,DateSelectActivity.class);
                    startActivity(intent);
                }
                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });

        int displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();//屏幕的宽
        if ("时间".equals(defaultShowText)){
            double wid = displayWidth/1.4;
            int width = (int)wid;
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView,width, Gravity.LEFT);
        }
        else {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(expandTabView != null){
            expandTabView.onExpandPopView();
        }
    }

    private void initDate() {
        try {
            mDateLists = new ArrayList<>();
            mDateLists.add(new KeyValueBean("","默认排序"));
            mDateLists.add(new KeyValueBean("PositiveSequence","正序"));
            mDateLists.add(new KeyValueBean("InvertedOrder","倒序"));
            mDateLists.add(new KeyValueBean("Custom","自定义"));

            mPriceLists = new ArrayList<>();
            mPriceLists.add(new KeyValueBean("","默认排序"));
            mPriceLists.add(new KeyValueBean("PositiveSequence","金额从大到小"));
            mPriceLists.add(new KeyValueBean("InvertedOrder","金额从小到大"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<Object> mList = new ArrayList<>();

        /**
         * 构造函数
         *
         * @param context
         *            android上下文环境
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
            FrameLayout view = (FrameLayout) layoutInflater.inflate(
                    R.layout.activity_scan_code_withdraw_deposit_history_list_item, null);
            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

//        public void addAll(List<Collect> list){
//            mList.addAll(list);
//            notifyDataSetChanged();
//        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
