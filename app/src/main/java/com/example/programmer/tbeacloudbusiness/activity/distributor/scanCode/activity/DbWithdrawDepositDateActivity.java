package com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.action.DBScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.WithdrawDepositDateInfoActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.PayStatusResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.PayeeTypeResponeModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateResponseModel;
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
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * 提现数据列表-分销商
 */

public class DbWithdrawDepositDateActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.take_money_pay_money)
    TextView mPayMoneyView;
    @BindView(R.id.scan_code_top_layout)
    LinearLayout mScanCodeTopLayout;
    @BindView(R.id.scan_code_top_layout1)
    LinearLayout mScanCodeTopLayout1;
    @BindView(R.id.scan_code_top_money_iv)
    ImageView mScanCodeTopMoneyIv;
    @BindView(R.id.scan_code_top_money_iv1)
    ImageView mScanCodeTopMoneyIv1;
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mDateLists;//扫码时间

    private PopOneListView mUserView, mDateView;
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;

    private BGARefreshLayout mRefreshLayout1;
    private ListView mListView1;
    private WithdrawAdapter mAdapter1;


    private int mPage = 1;
    private int mPagesize = 10;
    private String paystatusid, payeetypeid, starttime, endtime, mOrderItem, mOrder, mMoneyOrder, mTimeOrder;
    private String starttime1, endtime1, mOrderItem1, mOrder1, mMoneyOrder1;
    private int mPage1 = 1;
    private int mPagesize1 = 10;

    private final int RESULT_DATA_SELECT = 1000;
    private TextView mState1View;
    private TextView mState1View1;
    private int mFlag = 1;//1是已支付 2是已提现

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_scan_code_withdraw_deposit_list);
        ButterKnife.bind(this);
        initTopbar("提现数据");
        initView();
        getPayStatus();
        getUserType();
    }

    private void initView() {

        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext, R.layout.activity_db_scan_code_withdraw_list_item);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mAdapter.clear();
                mPage = 1;
                getListData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                getListData();
                return false;
            }
        });
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();


        mListView1 = (ListView) findViewById(R.id.listview1);
        mAdapter1 = new WithdrawAdapter(mContext, R.layout.activity_db_withdraw_list_item);
        mListView1.setAdapter(mAdapter1);
        mRefreshLayout1 = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh1);
        mRefreshLayout1.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mAdapter.clear();
                mPage = 1;
                getListData();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                getListData();
                return false;
            }
        });
        mRefreshLayout1.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);

        addUserItem(expandTabView, null, "", "用户");
        addDateItem(expandTabView, mDateLists, "默认", "支付时间");

        mState1View = (TextView) findViewById(R.id.take_money_pay_state1);
        mState1View1 = (TextView) findViewById(R.id.take_money_pay_state2);

        mState1View.setOnClickListener(this);
        mState1View1.setOnClickListener(this);
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
                    mOrderItem = "time";
                    mOrder = key;
                    starttime = "";
                    endtime = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });
        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth * 0.25;
        int width = (int) wid - DensityUtil.dip2px(mContext, 15);
        expandTabView.addItemToExpandTab(defaultShowText, mDateView, width, Gravity.CENTER);
    }

    private void addUserItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mUserView = new PopOneListView(this);
        mUserView.setDefaultSelectByValue(defaultSelect);
        mUserView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                payeetypeid = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth * 0.5;
        int width = (int) wid - DensityUtil.dip2px(mContext, 15);
        expandTabView.addItemToExpandTab(defaultShowText, mUserView, width, Gravity.LEFT);
    }

    /**
     * 获取用户类型
     */
    private void getUserType() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            PayeeTypeResponeModel model = (PayeeTypeResponeModel) msg.obj;
                            if (model != null) {
                                if (model.isSuccess()) {
                                    mUserView.setAdapterData(model.data.getpayeetypelist);
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
                        ScanCodeAction action = new ScanCodeAction();
                        PayeeTypeResponeModel model = action.getPayeeType();
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
     * 获取状态类型
     */
    private void getPayStatus() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            PayStatusResponseModel model = (PayStatusResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data != null) {
                                    KeyValueBean bean = model.data.paystatuslist.get(0);

                                    mState1View.setText(bean.getValue());
                                    mState1View.setTag(bean.getKey());

                                    KeyValueBean bean1 = model.data.paystatuslist.get(1);

                                    mState1View1.setText(bean1.getValue());
                                    mState1View1.setTag(bean1.getKey());
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
                        ScanCodeAction action = new ScanCodeAction();
                        PayStatusResponseModel model = action.getPayStatus();
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
                    mRefreshLayout1.endRefreshing();
                    mRefreshLayout1.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            WithdrawDepositDateResponseModel model = (WithdrawDepositDateResponseModel) msg.obj;
                            if (model != null) {
                                if (model.isSuccess()) {
                                    if (model.data != null)
                                        if (mFlag == 1) {//已支付
                                            mAdapter.addAll(model.data.takemoneylist);
                                            if (model.data.takemoneytotleinfo != null) {
                                                ((TextView) findViewById(R.id.take_money_pay_money)).setText(model.data.takemoneytotleinfo.totlemoney);
                                            }
                                        } else {
                                            mAdapter1.addAll(model.data.takemoneylist);
                                            (findViewById(R.id.take_money_pay_money)).setVisibility(View.GONE);
                                        }
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
                        DBScanCodeAction action = new DBScanCodeAction();
                        WithdrawDepositDateResponseModel model;
                        if (mFlag == 1) {//已支付
                            model = action.getWithdrawDepositDateList(paystatusid, payeetypeid, starttime, endtime, mOrderItem, mOrder, mPage++, mPagesize);
                        } else {
                            model = action.getWithdrawDepositDateList1(starttime1, endtime1, mOrderItem1, mOrder1, mPage1++, mPagesize1);
                        }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_DATA_SELECT) {
            starttime = data.getStringExtra("startTime");
            endtime = data.getStringExtra("endTime");
            if ("time".equals(mOrderItem)) {
                mOrderItem = "";
                mOrder = "";
            }
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.take_money_pay_state1) {//已支付
            mState1View.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            mState1View.setTextSize(18);

            mState1View1.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
            mState1View1.setTextSize(16);
            mFlag = 1;
            mAdapter.clear();
            mPage = 1;
            paystatusid = v.getTag() + "";
            mRefreshLayout.beginRefreshing();
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRefreshLayout1.setVisibility(View.GONE);
            mScanCodeTopLayout.setVisibility(View.VISIBLE);
            mScanCodeTopLayout1.setVisibility(View.GONE);

        } else {
            mState1View1.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            mState1View1.setTextSize(18);

            mState1View.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
            mState1View.setTextSize(16);
            mFlag = 2;
            mAdapter1.clear();
            mPage1 = 1;
            paystatusid = v.getTag() + "";
            mRefreshLayout1.beginRefreshing();
            mRefreshLayout1.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
            mScanCodeTopLayout1.setVisibility(View.VISIBLE);
            mScanCodeTopLayout.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.scan_code_top_money_layout, R.id.scan_code_top_money_layout1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scan_code_top_money_layout:
                mOrderItem = "money";
                if ("".equals(mMoneyOrder) || "asc".equals(mMoneyOrder)) {//升
                    mMoneyOrder = "desc";
                    mScanCodeTopMoneyIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mMoneyOrder = "asc";
                    mScanCodeTopMoneyIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mMoneyOrder;
                mOrderItem = "money";
                mRefreshLayout.beginRefreshing();
                break;
            case R.id.scan_code_top_money_layout1:
                mOrderItem1 = "money";
                if ("".equals(mMoneyOrder1) || "asc".equals(mMoneyOrder1)) {//升
                    mMoneyOrder1 = "desc";
                    mScanCodeTopMoneyIv1.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mMoneyOrder1 = "asc";
                    mScanCodeTopMoneyIv1.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder1 = mMoneyOrder1;
                mOrderItem1 = "money";
                mRefreshLayout1.beginRefreshing();
                break;
        }
    }

    class MyAdapter extends ArrayAdapter<WithdrawDepositDateResponseModel.TakeMoney> {
        int resourceId;


        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(resourceId, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final WithdrawDepositDateResponseModel.TakeMoney obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mCompanyNameView.setText(obj.companyname);
            holder.mMoneyView.setText(obj.money);
            holder.mTimeView.setText(obj.time);
            holder.mNameView.setText(obj.personname);
            holder.mRightView.setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WithdrawDepositDateInfoActivity.class);
                    intent.putExtra("takeMoneyId", obj.takemoneyid);
                    startActivity(intent);
                }
            });

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanyNameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;
            @BindView(R.id.scan_code_withdraw_deposit_time)
            TextView mTimeView;
            @BindView(R.id.scan_code_withdraw_deposit_money)
            TextView mMoneyView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class WithdrawAdapter extends ArrayAdapter<WithdrawDepositDateResponseModel.TakeMoney> {
        int resourceId;


        public WithdrawAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_db_withdraw_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final WithdrawDepositDateResponseModel.TakeMoney obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mCompanyNameView.setText(obj.companyname);
            holder.mMoneyView.setText(obj.money);
            holder.mNameView.setText(obj.personname);
            holder.mRightView.setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WithdrawDepositDateInfoActivity.class);
                    intent.putExtra("takeMoneyId", obj.takemoneyid);
                    startActivity(intent);
                }
            });

            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanyNameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;
            @BindView(R.id.scan_code_withdraw_deposit_money)
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
        mDateLists.add(new KeyValueBean("", "默认"));
        mDateLists.add(new KeyValueBean("asc", "正序"));//小到大
        mDateLists.add(new KeyValueBean("desc", "倒序"));
        mDateLists.add(new KeyValueBean("custom", "自定义"));
    }

}
