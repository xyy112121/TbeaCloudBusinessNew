package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmWithdrawalHistoryListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.WithdrawDepositDateInfoActivity;
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
 * 提现历史
 */

public class PlumberManageWithdrawalHistoryListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.person_info_head)
    CircleImageView mHeadView;
    @BindView(R.id.person_info_name)
    TextView mNameView;
    @BindView(R.id.person_info_personjobtitle)
    ImageView mPersonjobtitleView;
    @BindView(R.id.person_info_companyname)
    TextView mCompanynameView;
    @BindView(R.id.expandtab_view)
    ExpandPopTabView expandTabView;
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.activity_pm_withdrawal_history_list_money)
    ImageView moneyView;
    private List<KeyValueBean> mDateLists;//时间
    MyAdapter mAdapter;
    private final int RESULT_DATA_SELECT = 1000;
    private String electricianid, startdate, enddate, orderitem, order, mMoneyOrder;
    private int mPage = 1;
    private int mPagesiz = 10;
    PmWithdrawalHistoryListResponseModel model;
    PopOneListView mDateView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_withdrawal_history_list);
        ButterKnife.bind(this);
        electricianid = getIntent().getStringExtra("id");
        initTopbar("提现历史");
        initView();
    }

    private void initView() {
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();

        List<String> mTopList = new ArrayList<>();
        mTopList.add("时间");
        expandTabView.addTopList(mTopList);
        addDateItem(expandTabView, mDateLists, "默认", "时间");

        findViewById(R.id.activity_pm_withdrawal_history_list_money_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandTabView.setViewColor();
                mDateView.setSelectPostion();
                orderitem = "money";
                if ("".equals(mMoneyOrder) || "asc".equals(mMoneyOrder) || mMoneyOrder == null) {//升
                    mMoneyOrder = "desc";
                    moneyView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mMoneyOrder = "asc";
                    moneyView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                order = mMoneyOrder;
                mRefreshLayout.beginRefreshing();
            }
        });

        findViewById(R.id.pm_withdrawal_history_list_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlumberManageWithdrawalHistoryViewActivity.class);
                intent.putExtra("id", model.getData().getElectricianinfo().getUserid());
                startActivity(intent);
            }
        });
    }

    private void addDateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mDateView = new PopOneListView(this);
        mDateView.setDefaultSelectByValue(defaultSelect);
        mDateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mMoneyOrder = "";
                moneyView.setImageResource(R.drawable.icon_arraw);
                if ("Custom".equals(key)) {
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
        expandTabView.addItemToExpandTab(defaultShowText, mDateView, Gravity.LEFT);
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
                            PmWithdrawalHistoryListResponseModel model = (PmWithdrawalHistoryListResponseModel) msg.obj;
                            if (model != null) {
                                if (model.isSuccess() && model.getData() != null) {
                                    mAdapter.addAll(model.getData().getTakemoneylist());
                                    if (model.getData().getElectricianinfo() != null) {
                                        PmWithdrawalHistoryListResponseModel.DataBean.ElectricianinfoBean info = model.getData().getElectricianinfo();
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + info.getThumbpicture(), mHeadView);
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + info.getPersontypeicon(), mPersonjobtitleView);
                                        mNameView.setText(info.getPersonname());
                                        mCompanynameView.setText(info.getZone());
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
                        PlumberManageAction action = new PlumberManageAction();
                        model = action.getPmWithdrawalHistoryList(electricianid, startdate, enddate, orderitem, order, mPage++, mPagesiz);
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
        mDateLists.add(new KeyValueBean("", "默认排序"));
        mDateLists.add(new KeyValueBean("asc", "正序"));
        mDateLists.add(new KeyValueBean("desc", "倒序"));
        mDateLists.add(new KeyValueBean("Custom", "自定义"));
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
            startdate = data.getStringExtra("startTime");
            enddate = data.getStringExtra("endTime");
            if ("time".equals(orderitem)) {
                orderitem = "";
                order = "";
            }
            mRefreshLayout.beginRefreshing();
        }
    }

    public class MyAdapter extends BaseAdapter {

        public List<PmWithdrawalHistoryListResponseModel.DataBean.TakemoneylistBean> mList = new ArrayList<>();


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

                        R.layout.activity_pm_withdrawal_history_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final PmWithdrawalHistoryListResponseModel.DataBean.TakemoneylistBean obj = mList.get(position);
            holder.mMoneyView.setText(obj.getMoney());
            holder.mTimeView.setText(obj.getTime());
            holder.mZoneView.setText(obj.getZone());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WithdrawDepositDateInfoActivity.class);
                    intent.putExtra("takeMoneyId", obj.getTakemoneyid());
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

        public void addAll(List<PmWithdrawalHistoryListResponseModel.DataBean.TakemoneylistBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.pm_withdrawal_history_list_item_time)
            TextView mTimeView;
            @BindView(R.id.pm_withdrawal_history_list_item_zone)
            TextView mZoneView;
            @BindView(R.id.pm_withdrawal_history_list_item_money)
            TextView mMoneyView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
