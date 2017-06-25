package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

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
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * 历史记录
 */

public class HistoryListActivity extends TopActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mDateLists;//时间
    private List<KeyValueBean> mMoneyLists;//金额
    private List<KeyValueBean> mNumberLists;//数量
    private List<KeyValueBean> mStateLists;//激活状态
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private  int mPage = 1;
    private int mPagesiz =10 ;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_history_list);
        initTopbar("历史记录");
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
        addItem(expandTabView, mMoneyLists, "默认排序", "金额");
        addItem(expandTabView, mNumberLists, "默认排序", "数量");
        addItem(expandTabView, mStateLists, "默认排序", "激活");
    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext,R
                        .color.blue));
                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });
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
            View view =  layoutInflater.inflate(
                    R.layout.activity_scan_code_history_list_item, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext,StateListActivity.class);
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

        public void addAll(List<Object> list){
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

            mMoneyLists = new ArrayList<>();
            mMoneyLists.add(new KeyValueBean("","默认排序"));
            mMoneyLists.add(new KeyValueBean("PositiveSequence","金额从大到小"));
            mMoneyLists.add(new KeyValueBean("InvertedOrder","金额从小到大"));


            mNumberLists = new ArrayList<>();
            mNumberLists.add(new KeyValueBean("","默认排序"));
            mNumberLists.add(new KeyValueBean("PositiveSequence","数量从大到小"));
            mNumberLists.add(new KeyValueBean("InvertedOrder","数量从小到大"));

            mStateLists = new ArrayList<>();
            mStateLists.add(new KeyValueBean("","默认排序"));
            mStateLists.add(new KeyValueBean("yes","已激活"));
            mStateLists.add(new KeyValueBean("no","未激活"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}