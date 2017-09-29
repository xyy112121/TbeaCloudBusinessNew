package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Context;
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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.PayStatusResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.PayeeTypeResponeModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * 提现数据列表
 */

public class WithdrawDepositDateActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mDateLists;//扫码时间

    private PopOneListView mUserView, mDateView;
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private String paystatusid, payeetypeid, starttime, endtime, orderitem, order;
    private final int RESULT_DATA_SELECT = 1000;
    private TextView mState1View;
    private TextView mState1View1;
    private  int mViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_withdraw_deposit_list);
        initTopbar("支付明细");
        getPayStatus();
        getPayeeType();
        initView();

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
//        addDateItem(expandTabView, mDateLists, "默认", "时间");
        addUserItem(expandTabView, null, "", "用户");

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
                    orderitem = "time";
                    order = key;
                    starttime = "";
                    endtime = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mDateView, Gravity.LEFT);
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
        expandTabView.addItemToExpandTab(defaultShowText, mUserView, Gravity.LEFT);

    }

    /**
     * 获取用户类型
     */
    private void getPayeeType() {
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
                                    paystatusid = bean.getKey();
                                    mState1View.setText(bean.getValue());
                                    mState1View.setTag(bean.getKey());

                                    KeyValueBean bean1 = model.data.paystatuslist.get(1);

                                    mState1View1.setText(bean1.getValue());
                                    mState1View1.setTag(bean1.getKey());

                                    mRefreshLayout.beginRefreshing();
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
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            WithdrawDepositDateResponseModel model = (WithdrawDepositDateResponseModel) msg.obj;
                            if (model != null) {
                                if (model.isSuccess()) {
                                    if (model.data != null)
                                        mAdapter.addAll(model.data.takemoneylist);
                                    if (model.data.totlemoneyinfo != null) {
                                        ((TextView) findViewById(R.id.take_money_pay_money)).setText(model.data.totlemoneyinfo.totlemoney);
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
                        ScanCodeAction action = new ScanCodeAction();
                        WithdrawDepositDateResponseModel model = action.getWithdrawDepositDateList(paystatusid, payeetypeid, starttime, endtime, orderitem, order, mPage++, mPagesiz);
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

    @Override
    public void onClick(View v) {
        mViewId = v.getId();
        if (v.getId() == R.id.take_money_pay_state1) {
            mState1View.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            mState1View.setTextSize(18);

            mState1View1.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
            mState1View1.setTextSize(16);
        } else {
            mState1View1.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            mState1View1.setTextSize(18);

            mState1View.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
            mState1View.setTextSize(16);
        }
        paystatusid = v.getTag() + "";
        mRefreshLayout.beginRefreshing();
    }

     class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<WithdrawDepositDateResponseModel.TakeMoney> mList = new ArrayList<>();
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.activity_scan_code_withdraw_deposit_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final WithdrawDepositDateResponseModel.TakeMoney obj = mList.get(position);
//            ((TextView) view.findViewById(R.id.scan_code_withdraw_deposit_time)).setText(obj.time);
//            ((TextView) view.findViewById(R.id.scan_code_withdraw_deposit_name)).setText(obj.personname);
//            ((TextView) view.findViewById(R.id.scan_code_withdraw_deposit_money)).setText(obj.money);
            ImageLoader.getInstance().displayImage(obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mCompanynameView.setText(obj.companyname);
            holder.mNameView.setText(obj.personname);
            holder.mRightView.setVisibility(View.GONE);
            holder.mDepositMoneyView.setText(obj.money);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.take_money_pay_state1) {
                        Intent intent = new Intent(mContext, WithdrawDepositDateInfoActivity.class);
                        intent.putExtra("takeMoneyId", obj.takemoneyid);
                        startActivity(intent);
                    }

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

        public void addAll(List<WithdrawDepositDateResponseModel.TakeMoney> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanynameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;
            @BindView(R.id.scan_code_withdraw_deposit_money)
            TextView mDepositMoneyView;

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
