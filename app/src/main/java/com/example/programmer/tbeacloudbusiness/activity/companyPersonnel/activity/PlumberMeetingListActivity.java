package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.activity;

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
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.PlumberMeetingViewActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 公司人员-水电工会议列表
 */

public class PlumberMeetingListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mRegionLists;//区域
    private List<KeyValueBean> mStateLists;//状态
    private List<KeyValueBean> mDateLists;//时间


    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_main_list);
        initTopbar("水电工会议列表", "准备会议", this);
        intiView();
    }

    public void intiView() {
        mListView = (ListView) findViewById(R.id.listview);

        View mHeadView1 = getLayoutInflater().inflate(R.layout.activity_cp_plumber_meeting_list_head, null);
        mListView.addHeaderView(mHeadView1);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();

        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mRegionLists, "全部区域", "区域");
        addItem(expandTabView, mStateLists, "默认排序", "状态");
        addItem(expandTabView, mDateLists, "默认排序", "时间");

    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));

                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }

    private void initDate() {
        try {
            mDateLists = new ArrayList<>();
            mDateLists.add(new KeyValueBean("", "默认排序"));
            mDateLists.add(new KeyValueBean("PositiveSequence", "正序"));
            mDateLists.add(new KeyValueBean("InvertedOrder", "倒序"));
            mDateLists.add(new KeyValueBean("Custom", "自定义"));

            mRegionLists = new ArrayList<>();
            mRegionLists.add(new KeyValueBean("", "全部区域"));
            mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));

            mStateLists = new ArrayList<>();
            mStateLists.add(new KeyValueBean("", "默认排序"));
            mStateLists.add(new KeyValueBean("yes", "已激活"));
            mStateLists.add(new KeyValueBean("no", "未激活"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据
     */
    public void getListDate() {
//        final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                mRefreshLayout.endLoadingMore();
//                mRefreshLayout.endRefreshing();
//                switch (msg.what){
//                    case ThreadState.SUCCESS:
//                        RspInfo re = (RspInfo)msg.obj;
//                        if(re.isSuccess()){
//                            List<Collect> list = (List<Collect>)re.getDateObj("mysavelist");
//                            if(list != null){
//                                mAdapter.addAll(list);
//                            }else {
//                                mListView.setSelection(mAdapter.getCount());
//                                if(mPage >1){//防止分页的时候没有加载数据，但是页数已经增加，导致下一次查询不正确
//                                    mPage--;
//                                }
//                            }
//
//                        }else {
//                            UtilAssistants.showToast(re.getMsg());
//                        }
//
//                        break;
//                    case ThreadState.ERROR:
//                        UtilAssistants.showToast("操作失败！");
//                        break;
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    UserAction userAction = new UserAction();
//                    RspInfo re = userAction.getCollectList(mPage++,mPagesiz);
//                    handler.obtainMessage(ThreadState.SUCCESS,re).sendToTarget();
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        mAdapter.removeAll();
        mPage = 1;
        getListDate();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        getListDate();
        return true;
    }

    @Override
    public void onClick(View view) {

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
            FrameLayout view = (FrameLayout) layoutInflater.inflate(
                    R.layout.activity_plumber_meeting_main_list_item, null);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PlumberMeetingViewActivity.class);
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

