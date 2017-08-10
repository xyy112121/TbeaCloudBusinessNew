package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmScanCodeResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmScanCodeStateResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmWithdrawListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.ScanCodeViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.WithdrawDepositDateInfoActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 扫码数据
 */

public class PlumberManageScanCodeDateListActivity extends BaseActivity {
    @BindView(R.id.pm_scan_code)
    TextView mScanCodeView;
    @BindView(R.id.pm_withdraw_deposit)
    TextView mWithdrawDepositView;
    @BindView(R.id.pm_scan_code_top_money_iv)
    ImageView mScanCodeTopMoneyIv;
    @BindView(R.id.pm_scan_code_top_layout)
    LinearLayout mScanCodeTopLayout;
    @BindView(R.id.pm_withdraw_deposit_top_moeny_iv)
    ImageView mWithdrawDepositTopMoenyIv;
    @BindView(R.id.pm_withdraw_deposit_top_layout)
    LinearLayout mWithdrawDepositTopLayout;
    @BindView(R.id.pm_withdraw_deposit_moeny)
    TextView mMoenyView;
    @BindView(R.id.pm_withdraw_deposit_moeny_layout)
    RelativeLayout mMoenyLayout;
    private ExpandPopTabView expandTabView, expandTabView1;
    private List<KeyValueBean> mDateLists;//时间
    private BGARefreshLayout mRefreshLayout;
    private BGARefreshLayout mRefreshLayout1;
    private ListView mListView;
    private ListView mListView1;
    private MyAdapter mAdapter;
    private WdAdapter mWAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;

    private int mPage1 = 1;
    private int mPagesize1 = 10;
    PopOneListView mDateView, mStateView;
    private final int RESULT_DATA_SELECT = 1000;
    public String electricianid, startdate, enddate, confirmstatusid, mOrderItem, order;//查询参数
    public String electricianid1, startdate1, enddate1, mOrderItem1, order1;//查询参数
    private String mMoneyOrder1, mMoneyOrder;
    private int mFlag = 1;//1是扫码，2是提现


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_scancode_date_list);
        ButterKnife.bind(this);
        initTopbar("扫码数据");
        initView();
        getStatusList();
    }

    private void initView() {

        mListView = (ListView) findViewById(R.id.listview);///扫码的ListView
        mAdapter = new MyAdapter(mContext, R.layout.activity_pm_scan_code_list_item);
        mListView.setAdapter(mAdapter);

        mListView1 = (ListView) findViewById(R.id.listview1);///提现的ListView
        mWAdapter = new WdAdapter(mContext, R.layout.activity_pm_scan_code_list_item);
        mListView1.setAdapter(mWAdapter);

        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mPage = 1;
                mPagesiz = 14;
                mAdapter.clear();
                getScanCodeListData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                getScanCodeListData();
                return false;
            }
        });
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();

        mRefreshLayout1 = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh1);
        mRefreshLayout1.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mPage1 = 1;
                mPagesize1 = 14;
                mWAdapter.clear();
                getWithdrawListData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                getWithdrawListData();
                return false;
            }
        });
        mRefreshLayout1.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout1.beginRefreshing();

        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        expandTabView1 = (ExpandPopTabView) findViewById(R.id.expandtab_view1);
        addDateItem(expandTabView, mDateLists, "默认", "时间");
        addStateItem(expandTabView, null, "", "状态");

        addDateItem(expandTabView1, mDateLists, "默认", "时间");

    }

    /**
     * 从服务器获取扫码数据
     */
    private void getScanCodeListData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            PmScanCodeResponseModel model = (PmScanCodeResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.rebatescanlist != null) {
                                    mAdapter.addAll(model.data.rebatescanlist);
                                }

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
                        PlumberManageAction action = new PlumberManageAction();
                        PmScanCodeResponseModel model = action.getScanCodeList(electricianid, startdate, enddate, confirmstatusid, mOrderItem, order, mPage++, mPagesiz);
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
     * 从服务器获取提现数据
     */
    private void getWithdrawListData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout1.endRefreshing();
                    mRefreshLayout1.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            PmWithdrawListResponseModel model = (PmWithdrawListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.takemoneylist != null) {
                                    mWAdapter.addAll(model.data.takemoneylist);
                                }
                                if(model.data.takemoneytotleinfo != null){
                                    mMoenyView.setText(model.data.takemoneytotleinfo.totlemoney);
                                }

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
                        PlumberManageAction action = new PlumberManageAction();
                        PmWithdrawListResponseModel model = action.getWithdrawList(electricianid1, startdate1, enddate1, mOrderItem1, order1, mPage1++, mPagesize1);
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

    private void getStatusList() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        PmScanCodeStateResponseModel model = (PmScanCodeStateResponseModel) msg.obj;
                        if (model.isSuccess()) {
                            mStateView.setAdapterData(model.data.confirmstatuslist);

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
                    PlumberManageAction action = new PlumberManageAction();
                    PmScanCodeStateResponseModel model = action.getStatus();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }

    private void addStateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mStateView = new PopOneListView(this);
        mStateView.setDefaultSelectByValue(defaultSelect);
        mStateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                confirmstatusid = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth * 0.2;
        int width = (int) wid - DensityUtil.dip2px(mContext, 15);
        expandTabView.addItemToExpandTab(defaultShowText, mStateView, width, Gravity.CENTER);
    }

    private void addDateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mDateView = new PopOneListView(this);
        mDateView.setDefaultSelectByValue(defaultSelect);
        mDateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if ("custom".equals(key)) {
                    Intent intent = new Intent(mContext, DateSelectActivity.class);
                    startActivityForResult(intent, RESULT_DATA_SELECT);
                } else {
                    if(mFlag == 1){//扫码
                        mOrderItem = "time";
                        order = key;
                        startdate = "";
                        enddate = "";
                        mRefreshLayout.beginRefreshing();
                    }else {
                        mOrderItem1 = "time";
                        order1 = key;
                        startdate1 = "";
                        enddate1 = "";
                        mRefreshLayout1.beginRefreshing();
                    }

                }
            }
        });
        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth * 0.5;
        int width = (int) wid - DensityUtil.dip2px(mContext, 15);
        expandTabView.addItemToExpandTab(defaultShowText, mDateView, width, Gravity.LEFT);
    }


    @OnClick({R.id.pm_scan_code, R.id.pm_withdraw_deposit, R.id.pm_withdraw_deposit_top_moeny_layout, R.id.pm_scan_code_top_money_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pm_scan_code:
                mFlag = 1;
                mScanCodeView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                mWithdrawDepositView.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                mWithdrawDepositTopLayout.setVisibility(View.GONE);
                mScanCodeTopLayout.setVisibility(View.VISIBLE);
                mMoenyLayout.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout1.setVisibility(View.GONE);
                mRefreshLayout.beginRefreshing();
                break;
            case R.id.pm_withdraw_deposit:
                mFlag = 2;
                mScanCodeView.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                mWithdrawDepositView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                mScanCodeTopLayout.setVisibility(View.GONE);
                mWithdrawDepositTopLayout.setVisibility(View.VISIBLE);
                mMoenyLayout.setVisibility(View.VISIBLE);
                mRefreshLayout1.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.GONE);
                mRefreshLayout1.beginRefreshing();
                break;
            case R.id.pm_scan_code_top_money_layout://扫码的金额
                mOrderItem = "money";
                if ("".equals(mMoneyOrder1) || "asc".equals(mMoneyOrder1)) {//升
                    mMoneyOrder1 = "desc";
                    mWithdrawDepositTopMoenyIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mMoneyOrder1 = "asc";
                    mWithdrawDepositTopMoenyIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                order = mMoneyOrder1;
                mOrderItem = "money";
                mRefreshLayout.beginRefreshing();
                break;
            case R.id.pm_withdraw_deposit_top_moeny_layout://提现的金额
                mOrderItem1 = "money";
                if ("".equals(mMoneyOrder1) || "asc".equals(mMoneyOrder1)) {//升
                    mMoneyOrder1 = "desc";
                    mScanCodeTopMoneyIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mMoneyOrder1 = "asc";
                    mScanCodeTopMoneyIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                order1 = mMoneyOrder1;
                mOrderItem1 = "money";
                mRefreshLayout1.beginRefreshing();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_DATA_SELECT) {
            if(mFlag == 1){//扫码
                startdate = data.getStringExtra("startTime");
                enddate = data.getStringExtra("endTime");
                if ("time".equals(mOrderItem)) {
                    mOrderItem = "";
                    order = "";
                }
                mRefreshLayout.beginRefreshing();
            }else {
                startdate1 = data.getStringExtra("startTime");
                enddate1 = data.getStringExtra("endTime");
                if ("time".equals(mOrderItem1)) {
                    mOrderItem1 = "";
                    order1 = "";
                }
                mRefreshLayout1.beginRefreshing();
            }
        }
    }

    //扫码的
    class MyAdapter extends ArrayAdapter<PmScanCodeResponseModel.DataBean.RebatescanlistBean> {
        private int mResourceId;

        public MyAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.mResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final PmScanCodeResponseModel.DataBean.RebatescanlistBean obj = getItem(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(mResourceId, null);
//                convertView = getLayoutInflater().inflate(R.layout.activity_pm_scan_code_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mMoneyView.setText(obj.money);
            holder.mScantimeView.setText(obj.scantime);
            if("已确认".equals(obj.confirmstatus)){
                holder.mStateView.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
            }
            holder.mStateView.setText(obj.confirmstatus);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ScanCodeViewActivity.class);
                    intent.putExtra("id",obj.rebatescanid);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.pm_scan_code_item_scantime)
            TextView mScantimeView;
            @BindView(R.id.pm_scan_code_item_state)
            TextView mStateView;
            @BindView(R.id.pm_scan_code_item_money)
            TextView mMoneyView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    //提现
    class WdAdapter extends ArrayAdapter<PmWithdrawListResponseModel.DataBean.TakeMoneyBean> {
        private int mResourceId;

        public WdAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.mResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final PmWithdrawListResponseModel.DataBean.TakeMoneyBean obj = getItem(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(mResourceId, null);
//                convertView = getLayoutInflater().inflate(R.layout.activity_pm_scan_code_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mMoneyView.setText(obj.money);
            holder.mScantimeView.setText(obj.time);
            holder.mStateView.setText(obj.zone);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WithdrawDepositDateInfoActivity.class);
                    intent.putExtra("id",obj.takemoneyid);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.pm_scan_code_item_scantime)
            TextView mScantimeView;
            @BindView(R.id.pm_scan_code_item_state)
            TextView mStateView;
            @BindView(R.id.pm_scan_code_item_money)
            TextView mMoneyView;

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
        mDateLists.add(new KeyValueBean("PositiveSequence", "正序"));
        mDateLists.add(new KeyValueBean("InvertedOrder", "倒序"));
        mDateLists.add(new KeyValueBean("Custom", "自定义"));
    }

}
