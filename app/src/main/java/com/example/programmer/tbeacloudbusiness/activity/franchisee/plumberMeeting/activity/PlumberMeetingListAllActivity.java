package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListStateResonpseModel;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 水电工会议列表
 */

public class PlumberMeetingListAllActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.plumber_meeting_main_list_search_text)
    EditText mSearchTextView;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mRegionLists;//区域,状态
    private PopOneListView mRegionView, mStateView;
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private String mCode, mZoneid, mStatusid, mStartTime, mEndTime, mOrderItem, mOrder,mCodeOrder,mTimeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_main_list);
        initTopbar("水电工会议");
        intiView();
        getStatusList();
    }

    public void intiView() {
        mListView = (ListView) findViewById(R.id.listview);

        View mHeadView1 = getLayoutInflater().inflate(R.layout.activity_plumber_meeting_main_list_top1, null);
        mListView.addHeaderView(mHeadView1);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        ButterKnife.bind(this);
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addRegionItem(expandTabView, mRegionLists, "全部区域", "区域");
        addStateItem(expandTabView, null, "", "激活");

        mSearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mCode = mSearchTextView.getText()+"";
                    mRefreshLayout.beginRefreshing();
                }

                return false;
            }
        });

        final ImageView codeView = getViewById(R.id.activity_plumber_meeting_main_list_code);
        findViewById(R.id.activity_plumber_meeting_main_list_code_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOrderItem = "money";
                if ("".equals(mCodeOrder) || "asc".equals(mCodeOrder)) {//升
                    mCodeOrder = "desc";
                    codeView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mCodeOrder = "asc";
                    codeView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mCodeOrder;
                mOrderItem="meetingcode";
                mRefreshLayout.beginRefreshing();
            }
        });

        final ImageView timeView = getViewById(R.id.activity_plumber_meeting_main_list_time);
        findViewById(R.id.activity_plumber_meeting_main_list_time_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOrderItem = "money";
                if ("".equals(mTimeOrder) || "asc".equals(mTimeOrder)) {//升
                    mTimeOrder = "desc";
                    timeView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mTimeOrder = "asc";
                    timeView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mTimeOrder;
                mOrderItem="meetingtime";
                mRefreshLayout.beginRefreshing();
            }
        });

    }

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
                                ToastUtil.showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            ToastUtil.showMessage("操作失败！");
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
                                ToastUtil.showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            ToastUtil.showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PlumberMeetingAction action = new PlumberMeetingAction();
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

    public void addRegionItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mRegionView = new PopOneListView(this);
        mRegionView.setDefaultSelectByValue(defaultSelect);
        mRegionView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if ("regionSelect".equals(key)) {
//                    Intent intent = new Intent(mContext,)
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
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                mStatusid = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mStateView, Gravity.RIGHT);
    }

    private void initDate() {
        try {

            mRegionLists = new ArrayList<>();
            mRegionLists.add(new KeyValueBean("", "全部区域"));
            mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        mAdapter.removeAll();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == RESULT_OK){
            mZoneid = data.getStringExtra("ids");
            mRefreshLayout.beginRefreshing();
        }
    }

    public class MyAdapter extends BaseAdapter {

        public List<PlumberMeetingListMainResonpseModel.Meeting> mList = new ArrayList<>();


        @Override
        public int getCount() {
            return mList.size();
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
            ViewHolder holder;
            if(convertView == null){
                convertView = getLayoutInflater().inflate(
                        R.layout.activity_plumber_meeting_main_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            final PlumberMeetingListMainResonpseModel.Meeting obj = mList.get(position);
            holder.mCodeView.setText(obj.meetingcode);
            holder.mStatusView.setText(obj.meetingstatus);
            holder.mTimeView.setText(obj.meetingtime);
            holder.mZoneView.setText(obj.zone);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PlumberMeetingViewActivity.class);
                    intent.putExtra("id",obj.id);
                    startActivity(intent);
                }
            });
            return convertView;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<PlumberMeetingListMainResonpseModel.Meeting> list){
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
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

