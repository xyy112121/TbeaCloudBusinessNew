package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmSignHistoryResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 签到历史
 */

public class PlumberManageSignHistoryListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.person_info_head)
    CircleImageView mHeadView;
    @BindView(R.id.person_info_name)
    TextView mNameView;
    @BindView(R.id.person_info_personjobtitle)
    ImageView mPersonjobtitleView;
    @BindView(R.id.pm_sign_list_total)
    TextView mTotalView;
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mRegionLists;//区域
    private List<KeyValueBean> mDateLists;//时间

    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private final int RESULT_DATA_SELECT = 1000;

    private String electricianid, meetingcode, zoneid, startdate, enddate, orderitem, order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_sign_list);
        ButterKnife.bind(this);
        electricianid = getIntent().getStringExtra("id");
        initTopbar("签到历史");
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addRegionItem(expandTabView, mRegionLists, "全部区域", "区域");
        addDateItem(expandTabView, mDateLists, "默认排序", "时间");

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
                            PmSignHistoryResponseModel model = (PmSignHistoryResponseModel) msg.obj;
                            if (model != null) {
                                if (model.isSuccess() && model.data != null) {
                                    if (model.data.attendmeetinglist != null) {
                                        mAdapter.addAll(model.data.attendmeetinglist);
                                    }

                                    if (model.data.electricianinfo != null) {
                                        PmSignHistoryResponseModel.DataBean.ElectricianinfoBean info = model.data.electricianinfo;
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + info.thumbpicture, mHeadView);
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + info.persontypeicon, mPersonjobtitleView);
                                        mNameView.setText(info.personname);
                                    }
//
//
                                } else {
                                    ToastUtil.showMessage(model.getMsg());
                                }
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
                        PlumberManageAction action = new PlumberManageAction();
                        PmSignHistoryResponseModel model = action.getSignHistoryList(electricianid, meetingcode, zoneid, startdate, enddate, orderitem, order, mPage++, mPagesiz);
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

    private void addDateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView mDateView = new PopOneListView(this);
        mDateView.setDefaultSelectByValue(defaultSelect);
        mDateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if ("custom".equals(key)) {
                    Intent intent = new Intent(mContext, DateSelectActivity.class);
                    startActivityForResult(intent, RESULT_DATA_SELECT);
                } else {
                    orderitem = "time";
                    order = key;
                    startdate = "";
                    enddate = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });
        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth * 0.4;
        int width = (int) wid - DensityUtil.dip2px(mContext, 15);
        expandTabView.addItemToExpandTab(defaultShowText, mDateView, width, Gravity.RIGHT);
    }

    private void addRegionItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView regionView = new PopOneListView(this);
        regionView.setDefaultSelectByValue(defaultSelect);
        regionView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if ("regionSelect".equals(key)) {
                    Intent intent = new Intent(mContext, RegionSelectActivity.class);
                    startActivityForResult(intent, 1000);
                } else {
                    zoneid = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });

        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth * 0.2;
        int width = (int) wid - DensityUtil.dip2px(mContext, 15);
        expandTabView.addItemToExpandTab(defaultShowText, regionView, width, Gravity.CENTER);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.removeAll();
        mPage = 1;
        getListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getListData();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_DATA_SELECT) {
            startdate = data.getStringExtra("startTime");
            enddate = data.getStringExtra("endTime");
            if ("time".equals(orderitem)) {
                orderitem = "";
                order = "";
            }
            mRefreshLayout.beginRefreshing();
        } else  if (requestCode == 1000 && resultCode == RESULT_OK) {
            zoneid = data.getStringExtra("ids");
            mRefreshLayout.beginRefreshing();
        }
    }

    public class MyAdapter extends BaseAdapter {

        public List<PmSignHistoryResponseModel.DataBean.AttendmeetinglistBean> mList = new ArrayList<>();

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
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.activity_plumber_manage_sign_history_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PmSignHistoryResponseModel.DataBean.AttendmeetinglistBean obj = mList.get(position);
            holder.mCodeView.setText(obj.meetingcode);
            holder.mTimeView.setText(obj.attendtime);
            holder.mZoneView.setText(obj.zone);
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, PlumberManageLoginStatisticsActivity.class);
//                    startActivity(intent);
//                }
//            });

            return convertView;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<PmSignHistoryResponseModel.DataBean.AttendmeetinglistBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.pm_sign_item_code)
            TextView mCodeView;
            @BindView(R.id.pm_sign_item_zone)
            TextView mZoneView;
            @BindView(R.id.pm_sign_item_time)
            TextView mTimeView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
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
        mRegionLists = new ArrayList<>();
        mRegionLists.add(new KeyValueBean("", "全部区域"));
        mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));

        mDateLists = new ArrayList<>();
        mDateLists.add(new KeyValueBean("", "默认排序"));
        mDateLists.add(new KeyValueBean("PositiveSequence", "正序"));
        mDateLists.add(new KeyValueBean("InvertedOrder", "倒序"));
        mDateLists.add(new KeyValueBean("Custom", "自定义"));
    }
}
