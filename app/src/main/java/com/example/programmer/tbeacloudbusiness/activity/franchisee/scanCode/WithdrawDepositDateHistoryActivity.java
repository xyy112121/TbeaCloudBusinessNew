package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateHistoryListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 提现历史
 */

public class    WithdrawDepositDateHistoryActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mDateLists,mPriceLists;//时间
    private PopOneListView mDateView,mPriceView;
    private String starttime, endtime, orderitem, order;
    private final int RESULT_DATA_SELECT = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_withdraw_deposit_history_list);
        initTopbar("提现历史");
        initView();
    }

    private void initView(){
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addDateItem(expandTabView, mDateLists, "默认排序", "时间");
        addPriceItem(expandTabView, mPriceLists, "默认排序", "金额");
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
                            WithdrawDepositDateHistoryListResponseModel model = (WithdrawDepositDateHistoryListResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data != null)
                                    mAdapter.addAll(model.data.takemoneyrankinglist);
                                    initPayeeInfo(model.data.payeeinfo);
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
                        String personOrCompany = getIntent().getStringExtra("personOrCompany");
                        String payeeId = getIntent().getStringExtra("payeeId");
                        WithdrawDepositDateHistoryListResponseModel model = action.getWithdrawDepositDateHistoryList(personOrCompany, payeeId, starttime, endtime, orderitem, order, mPage++, mPagesiz);
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

    public void initPayeeInfo(WithdrawDepositDateHistoryListResponseModel.PayeeInfo info){
        if (info != null){
            ImageView headView = (ImageView)findViewById(R.id.person_info_head);
            ImageView jobtitleView = (ImageView)findViewById(R.id.person_info_personjobtitle);
            String pathUrl = MyApplication.instance.getImgPath();
            ImageLoader.getInstance().displayImage(pathUrl+info.thumbpicture,headView);
            ImageLoader.getInstance().displayImage(pathUrl+info.persontypeicon,jobtitleView);
            ((TextView)findViewById(R.id.person_info_name)).setText(info.personname);
            ((TextView)findViewById(R.id.person_info_companyname)).setText(info.companyname);
            ((TextView)findViewById(R.id.scan_code_withdraw_deposit_history_list_money)).setText(info.totlemoney);
        }
    }

    public void addDateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mDateView = new PopOneListView(this);
        mDateView.setDefaultSelectByValue(defaultSelect);
        mDateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if ("Custom".equals(key)) {//时间自定义
                    Intent intent = new Intent(mContext, DateSelectActivity.class);
                    startActivityForResult(intent, RESULT_DATA_SELECT);
                }else {
                    orderitem = "time";
                    order = key;
                    starttime = "";
                    endtime = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });

        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth / 1.4;
        int width = (int) wid;
        expandTabView.addItemToExpandTab(defaultShowText, mDateView, width, Gravity.LEFT);
    }

    public void addPriceItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mPriceView = new PopOneListView(this);
        mPriceView.setDefaultSelectByValue(defaultSelect);
        mPriceView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                orderitem = "money";
                order = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mPriceView);
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
    protected void onDestroy() {
        super.onDestroy();
        if (expandTabView != null) {
            expandTabView.onExpandPopView();
        }
    }

    private void initDate() {
        try {
            mDateLists = new ArrayList<>();
            mDateLists.add(new KeyValueBean("", "默认"));
            mDateLists.add(new KeyValueBean("asc", "正序"));//小到大
            mDateLists.add(new KeyValueBean("desc", "倒序"));
            mDateLists.add(new KeyValueBean("custom", "自定义"));

            mPriceLists = new ArrayList<>();
            mPriceLists.add(new KeyValueBean("", "默认排序"));
            mPriceLists.add(new KeyValueBean("desc", "金额从大到小"));
            mPriceLists.add(new KeyValueBean("asc", "金额从小到大"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<WithdrawDepositDateHistoryListResponseModel.TakeMoneyRanking> mList = new ArrayList<>();

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
            FrameLayout view = (FrameLayout) layoutInflater.inflate(
                    R.layout.activity_scan_code_withdraw_deposit_history_list_item, null);
            WithdrawDepositDateHistoryListResponseModel.TakeMoneyRanking obj = mList.get(position);
            ((TextView)view.findViewById(R.id.scan_code_withdraw_deposit_history_list_item_time)).setText(obj.time);
            ((TextView)view.findViewById(R.id.scan_code_withdraw_deposit_history_list_item_money)).setText(obj.money);

            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<WithdrawDepositDateHistoryListResponseModel.TakeMoneyRanking> list){
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
