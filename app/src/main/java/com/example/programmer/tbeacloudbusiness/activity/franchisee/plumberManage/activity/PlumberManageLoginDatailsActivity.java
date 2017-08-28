package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PlumberManageLoginDataResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
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
 * 登录详情
 */

public class PlumberManageLoginDatailsActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mDateLists;//时间

    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private String startdate, enddate, orderitem, order;
    private final int RESULT_DATA_SELECT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_manage_login_dateils_list);
        initTopbar("登录详情");
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mDateLists, "默认排序", "时间");

    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        final PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));

                if ("Custom".equals(key)) {//时间自定义
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
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);

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
                            PlumberManageLoginDataResponseModel model = (PlumberManageLoginDataResponseModel) msg.obj;
                            if (model != null) {
                                if (model.isSuccess() && model.data != null) {
                                    mAdapter.addAll(model.data.loginlist);
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
                        PlumberManageLoginDataResponseModel model = action.getLoginDataList(startdate, enddate, orderitem, order, mPage++, mPagesiz);
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

     class MyAdapter extends BaseAdapter {

        public List<PlumberManageLoginDataResponseModel.DataBean.LoginlistBean> mList = new ArrayList<>();

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
                        R.layout.activity_plumber_manage_login_dateils_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTimeView.setText(mList.get(position).logintime);
            holder.mLocationView.setText(mList.get(position).loginplace);
            holder.mTerminalView.setText(mList.get(position).loginterminal);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, PlumberManageLoginStatisticsActivity.class);
//                    startActivity(intent);
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

        public void addAll(List<PlumberManageLoginDataResponseModel.DataBean.LoginlistBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.login_data_time)
            TextView mTimeView;
            @BindView(R.id.login_data_location)
            TextView mLocationView;
            @BindView(R.id.login_data_terminal)
            TextView mTerminalView;

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
        mDateLists = new ArrayList<>();
        mDateLists.add(new KeyValueBean("", "默认排序"));
        mDateLists.add(new KeyValueBean("asc", "正序"));
        mDateLists.add(new KeyValueBean("desc", "倒序"));
        mDateLists.add(new KeyValueBean("Custom", "自定义"));

    }

}
