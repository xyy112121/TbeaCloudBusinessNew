package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * Created by programmer on 2017/5/28.
 */

public class ScanCodeStateListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mScanDateLists;//扫码时间
    private List<KeyValueBean> mCodeLists;//编码
    private List<KeyValueBean> mUserLists;//用户
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
        initTopbar("已激活");
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
        addItem(expandTabView, mCodeLists, "默认排序", "编码");
        addItem(expandTabView, mScanDateLists, "默认排序", "扫码时间");
        addItem(expandTabView, mUserLists, "默认排序", "用户");
    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext,R.color.blue));
                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });
        int displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();//屏幕的宽
        if ("编码".equals(defaultShowText)){

            expandTabView.addItemToExpandTab(defaultShowText, popOneListView,displayWidth/2, Gravity.LEFT);
        }else  if("扫码时间".equals(defaultShowText)){
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView,Gravity.LEFT);
        }else {
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
                    R.layout.activity_scan_code_history_state_list_item, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext,ScanCodeViewActivity.class);
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
            mScanDateLists = new ArrayList<>();
            mScanDateLists.add(new KeyValueBean("","默认排序"));
            mScanDateLists.add(new KeyValueBean("PositiveSequence","正序"));
            mScanDateLists.add(new KeyValueBean("InvertedOrder","倒序"));
            mScanDateLists.add(new KeyValueBean("Custom","自定义"));

            mCodeLists = new ArrayList<>();
            mCodeLists.add(new KeyValueBean("","默认排序"));
            mCodeLists.add(new KeyValueBean("PositiveSequence","从大到小"));
            mCodeLists.add(new KeyValueBean("InvertedOrder","从小到大"));


            mUserLists = new ArrayList<>();
            mUserLists.add(new KeyValueBean("","默认排序"));
            mUserLists.add(new KeyValueBean("user1","用户1"));
            mUserLists.add(new KeyValueBean("user2","用户2"));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
