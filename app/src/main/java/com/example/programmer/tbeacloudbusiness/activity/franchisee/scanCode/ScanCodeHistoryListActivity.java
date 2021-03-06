package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeActivityStatusResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeHistoryResponseModel;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;


import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * 历史记录
 */

public class ScanCodeHistoryListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mDateLists, mMoneyLists, mNumberLists;//时间,金额,数量
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private PopOneListView mStateView, mDateView, mMoneyView, mNumberView;
    private String starttime, endtime, activitystatusid, orderitem, order;
    private final int RESULT_DATA_SELECT = 1000;
    List<String> mTopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_history_list);
        String flag = getIntent().getStringExtra("flag");
        if ("my".equals(flag)) {
            initTopbar("历史记录", "生成", this);
        } else {
            initTopbar("历史记录");
        }
        initView();
        getActivityStatus();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        initDate();
        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        mTopList.add("时间");
        mTopList.add("金额");
        mTopList.add("数量");
        mTopList.add("激活");
        expandTabView.addTopList(mTopList);
        expandTabView.setOnCloseListener(new ExpandPopTabView.OnClose() {
            @Override
            public void onCloseListener() {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), "");
//                expandTabView.setViewColor();
            }
        });
        addDateItem(expandTabView, mDateLists, "默认", "时间");
        addMoneyItem(expandTabView, mMoneyLists, "默认", "金额");
        addNumberItem(expandTabView, mNumberLists, "默认", "数量");
        addStateItem(expandTabView, null, "", "激活");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }

    private void addDateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mDateView = new PopOneListView(this);
        mDateView.setDefaultSelectByValue(defaultSelect);
        mDateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mMoneyView.setSelectPostion();
                mNumberView.setSelectPostion();
                mStateView.setSelectPostion();
                if ("custom".equals(key)) {
                    Intent intent = new Intent(mContext, DateSelectActivity.class);
                    intent.putExtra("startTime", starttime);
                    intent.putExtra("endTime", endtime);
                    startActivityForResult(intent, RESULT_DATA_SELECT);
                } else {
                    orderitem = "time";
                    order = key;
                    starttime = "";
                    endtime = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mDateView);
    }

    private void addMoneyItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mMoneyView = new PopOneListView(this);
        mMoneyView.setDefaultSelectByValue(defaultSelect);
        mMoneyView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mNumberView.setSelectPostion();
                mStateView.setSelectPostion();
                mDateView.setSelectPostion();
                orderitem = "money";
                order = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mMoneyView);

    }

    private void addNumberItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mNumberView = new PopOneListView(this);
        mNumberView.setDefaultSelectByValue(defaultSelect);
        mNumberView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mMoneyView.setSelectPostion();
                mStateView.setSelectPostion();
                mDateView.setSelectPostion();
                orderitem = "count";
                order = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mNumberView);
    }

    private void addStateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mStateView = new PopOneListView(this);
        mStateView.setDefaultSelectByValue(defaultSelect);
        mStateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mMoneyView.setSelectPostion();
                mNumberView.setSelectPostion();
                mDateView.setSelectPostion();
                activitystatusid = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mStateView);
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
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_DATA_SELECT) {
            starttime = data.getStringExtra("startTime");
            endtime = data.getStringExtra("endTime");
            if ("time".equals(orderitem)) {
                orderitem = "";
                order = "";
            }
            mRefreshLayout.beginRefreshing();
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
                            ScanCodeHistoryResponseModel model = (ScanCodeHistoryResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                mAdapter.addAll(model.data.rebateqrcodelist);


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
                        ScanCodeAction action = new ScanCodeAction();
                        ScanCodeHistoryResponseModel model = action.getScanCodeHistoryList(starttime, endtime, activitystatusid, orderitem, order, mPage++, mPagesiz);
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

    private void getActivityStatus() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ScanCodeActivityStatusResponseModel model = (ScanCodeActivityStatusResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                mStateView.setAdapterData(model.data.activitystatuslist);

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
                        ScanCodeAction action = new ScanCodeAction();
                        ScanCodeActivityStatusResponseModel model = action.getActivityStatus();
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
        try {
            mDateLists = new ArrayList<>();
            mDateLists.add(new KeyValueBean("", "默认"));
            mDateLists.add(new KeyValueBean("asc", "正序"));//小到大
            mDateLists.add(new KeyValueBean("desc", "倒序"));
            mDateLists.add(new KeyValueBean("custom", "自定义"));

            mMoneyLists = new ArrayList<>();
            mMoneyLists.add(new KeyValueBean("", "默认"));
            mMoneyLists.add(new KeyValueBean("desc", "金额从大到小"));
            mMoneyLists.add(new KeyValueBean("asc", "金额从小到大"));


            mNumberLists = new ArrayList<>();
            mNumberLists.add(new KeyValueBean("", "默认排序"));
            mNumberLists.add(new KeyValueBean("desc", "数量从大到小"));
            mNumberLists.add(new KeyValueBean("asc", "数量从小到大"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        String flag = getIntent().getStringExtra("flag");
        Intent intent = new Intent(mContext,ScanCodeCreateActivity.class);
        intent.putExtra("flag",flag);
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<ScanCodeHistoryResponseModel.RebateqrCode> mList = new ArrayList<>();
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
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(
                    R.layout.activity_scan_code_history_list_item, null);
            final ScanCodeHistoryResponseModel.RebateqrCode obj = mList.get(position);
            ((TextView) view.findViewById(R.id.scanCode_history_generatetime)).setText(obj.generatetime);
            ((TextView) view.findViewById(R.id.scanCode_history_money)).setText("￥" + obj.money);
            ((TextView) view.findViewById(R.id.scanCode_history_activitynumber)).setText(obj.activitynumber);
            ((TextView) view.findViewById(R.id.scanCode_history_generatenumber)).setText(obj.generatenumber);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ScanCodeCreateInfoActivity.class);
                    intent.putExtra("id", obj.id);
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

        public void addAll(List<ScanCodeHistoryResponseModel.RebateqrCode> list) {
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
}
