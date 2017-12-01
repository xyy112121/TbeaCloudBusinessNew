package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListStateResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.HistorySearchActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 公司人员-水电工会议列表
 */

public class PlumberMeetingListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
      @BindView(R.id.plumber_meeting_main_list_search_text)
    TextView mSearchTextView;
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mRegionLists, mDateLists;//区域,时间
    private PopOneListView mRegionView, mStateView, mDateView;


    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private final int RESULT_DATA_SELECT = 1000;
    private String mCode, mZoneid, mStatusid, mStartTime, mEndTime, mOrderItem, mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_plumber_meeting_main_list);
        ButterKnife.bind(this);
        initTopbar("水电工会议列表", "准备会议", this);
        intiView();
        getStatusList();
    }

    public void intiView() {
        mListView = (ListView) findViewById(R.id.listview);

//        View mHeadView1 = getLayoutInflater().inflate(R.layout.activity_cp_plumber_meeting_list_head, null);
//        mListView.addHeaderView(mHeadView1);
        mAdapter = new MyAdapter(mContext, R.layout.activity_plumber_meeting_main_list_item);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));

        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        List<String> mTopList = new ArrayList<>();
        mTopList.add("区域");
        mTopList.add("状态");
        mTopList.add("时间");
        expandTabView.addTopList(mTopList);
        addRegionItem(expandTabView, mRegionLists, "全部区域", "区域");
        addStateItem(expandTabView, null, "", "状态");
        addDateItem(expandTabView, mDateLists, "默认排序", "时间");

        mSearchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistorySearchActivity.class);
                intent.putExtra("type", "servicemeeting");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }

    public void addRegionItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mRegionView = new PopOneListView(this);
        mRegionView.setDefaultSelectByValue(defaultSelect);
        mRegionView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue),value);
                mDateView.setSelectPostion();
                mStateView.setSelectPostion();
                if ("regionSelect".equals(key)) {
                    Intent intent = new Intent(mContext, RegionSelectActivity.class);
                    startActivityForResult(intent, 10001);
                } else {
                    mZoneid = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });

        expandTabView.addItemToExpandTab(defaultShowText, mRegionView);
    }

    private void addStateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mStateView = new PopOneListView(this);
        mStateView.setDefaultSelectByValue(defaultSelect);
        mStateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue),value);
                mDateView.setSelectPostion();
                mRegionView.setSelectPostion();
                mStatusid = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mStateView);
    }

    private void addDateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mDateView = new PopOneListView(this);
        mDateView.setDefaultSelectByValue(defaultSelect);
        mDateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue),value);
                mStateView.setSelectPostion();
                mRegionView.setSelectPostion();
                if ("custom".equals(key)) {
                    Intent intent = new Intent(mContext, DateSelectActivity.class);
                    startActivityForResult(intent, RESULT_DATA_SELECT);
                } else {
                    mOrderItem = "time";
                    mOrder = key;
                    mStartTime = "";
                    mEndTime = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mDateView);
    }

    /**
     * 获取状态
     */
    private void getStatusList() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            PlumberMeetingListStateResonpseModel model = (PlumberMeetingListStateResonpseModel) msg.obj;
                            if (model.isSuccess()) {
                                mStateView.setAdapterData(model.data.meetingstatuslist);

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
                        PlumberMeetingAction action = new PlumberMeetingAction();
                        PlumberMeetingListStateResonpseModel model = action.getStatus();
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

    /**
     * 从服务器获取数据
     */
    private void getListData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            PlumberMeetingListMainResonpseModel model = (PlumberMeetingListMainResonpseModel) msg.obj;
                            if (model.isSuccess()) {
                                mAdapter.addAll(model.data.meetinglist);
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
                        CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                        PlumberMeetingListMainResonpseModel model = action.getPlumberMeetingListAll(mCode, mZoneid, mStatusid, mStartTime, mEndTime, mOrderItem, mOrder, mPage++, mPagesiz);
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

    private void initDate() {
        mDateLists = new ArrayList<>();
        mDateLists.add(new KeyValueBean("", "默认"));
        mDateLists.add(new KeyValueBean("asc", "正序"));//小到大
        mDateLists.add(new KeyValueBean("desc", "倒序"));
        mDateLists.add(new KeyValueBean("custom", "自定义"));

        mRegionLists = new ArrayList<>();
        mRegionLists.add(new KeyValueBean("", "全部区域"));
        mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        mAdapter.clear();
        mPage = 1;
        getListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        getListData();
        return true;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(mContext, MeetingPrepareActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_DATA_SELECT) {
            mStartTime = data.getStringExtra("startTime");
            mEndTime = data.getStringExtra("endTime");
            if ("time".equals(mOrderItem)) {
                mOrderItem = "";
                mOrder = "";
            }
            mRefreshLayout.beginRefreshing();
        } else if (requestCode == 10001 && resultCode == RESULT_OK) {
            mZoneid = data.getStringExtra("ids");
            mRefreshLayout.beginRefreshing();
        }
    }

    class MyAdapter extends ArrayAdapter<PlumberMeetingListMainResonpseModel.Meeting> {
        int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final PlumberMeetingListMainResonpseModel.Meeting obj = getItem(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(resourceId, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mCodeView.setText(obj.meetingcode);
            holder.mStatusView.setText(obj.meetingstatus);
            holder.mTimeView.setText(obj.meetingtime);
            holder.mZoneView.setText(obj.zone);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MeetingViewActivity.class);
                    intent.putExtra("id", obj.id);
                    startActivity(intent);
                }
            });

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.pm_all_list_item_code)
            TextView mCodeView;
            @BindView(R.id.pm_all_list_item_zone)
            TextView mZoneView;
            @BindView(R.id.pm_all_list_item_status)
            TextView mStatusView;
            @BindView(R.id.pm_all_list_item_time)
            TextView mTimeView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}

