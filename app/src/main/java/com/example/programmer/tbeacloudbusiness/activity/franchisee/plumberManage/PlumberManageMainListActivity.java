package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage;

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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.ScanCodeRegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 *水电工列表
 */

public class PlumberManageMainListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate  {
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private  int mPage = 1;
    private int mPagesiz =10 ;
    private Context mContext;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mTypeLists;//类型
    private List<KeyValueBean> mRegionLists;//区域
    private List<KeyValueBean> mWithdrawDepositLists;//提现累计

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_rebate_list);
        initTopbar("水电工列表");
        initView();
    }

    private void initView(){
        ((TextView)findViewById(R.id.expert_search_text)).setHint("水电工查询");
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
        addItem(expandTabView, mTypeLists, "全部", "全部");
        addItem(expandTabView, mRegionLists, "全部区域", "区域");
        addItem(expandTabView, mWithdrawDepositLists, "默认排序", "提现累计");

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext,PersonManageActivity.class);
//                Intent intent = new Intent(mContext,ScanCodeDateListActivity.class);
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

                if("regionSelect".equals(key)){
                    Intent intent = new Intent(mContext,ScanCodeRegionSelectActivity.class);
                    startActivity(intent);
                }

                expandTabView.setViewColor(ContextCompat.getColor(mContext,R.color.blue));
                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });
        if("全部".equals(defaultShowText)){
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(expandTabView != null){
            expandTabView.onExpandPopView();
        }
    }

    private void initDate() {
        try {
            mTypeLists = new ArrayList<>();
            mTypeLists.add(new KeyValueBean("","全部"));
            mTypeLists.add(new KeyValueBean("distributor","隶属分销商"));
            mTypeLists.add(new KeyValueBean("plumber","旗下水电工"));

            mRegionLists = new ArrayList<>();
            mRegionLists.add(new KeyValueBean("","全部区域"));
            mRegionLists.add(new KeyValueBean("regionSelect","区域选择"));


            mWithdrawDepositLists = new ArrayList<>();
            mWithdrawDepositLists.add(new KeyValueBean("","默认排序"));
            mWithdrawDepositLists.add(new KeyValueBean("PositiveSequence","从大到小"));
            mWithdrawDepositLists.add(new KeyValueBean("InvertedOrder","从小到大"));

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
                    R.layout.activity_plumber_manage_list_item, null);
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
