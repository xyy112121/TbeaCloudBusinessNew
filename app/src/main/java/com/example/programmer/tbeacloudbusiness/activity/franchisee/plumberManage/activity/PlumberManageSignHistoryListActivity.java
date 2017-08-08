package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmWithdrawalHistoryListResonseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 签到历史
 */

public class PlumberManageSignHistoryListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

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
        electricianid = getIntent().getStringExtra("id");
        initTopbar("签到历史");
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
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
                            PmWithdrawalHistoryListResonseModel model = (PmWithdrawalHistoryListResonseModel) msg.obj;
                            if (model != null) {
                                if (model.isSuccess() && model.getData() != null) {
//                                    mAdapter.addAll(model.getData().getTakemoneylist());
//                                    if(model.getData().getElectricianinfo() != null){
//                                        PlumberManageWithdrawalHistoryListResonseModel.DataBean.ElectricianinfoBean info = model.getData().getElectricianinfo();
//                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath()+info.getThumbpicture(),mHeadView);
//                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath()+info.getPersontypeicon(),mPersonjobtitleView);
//                                        mNameView.setText(info.getPersonname());
//                                        mCompanynameView.setText(info.getZone());
//                                    }
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
                        PmWithdrawalHistoryListResonseModel model = action.getSignHistoryList(electricianid, meetingcode, zoneid, startdate, enddate, orderitem, order, mPage++, mPagesiz);
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
//                    mZoneid = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });

        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth * 0.2;
        int width = (int) wid- DensityUtil.dip2px(mContext, 15);
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
        }
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
