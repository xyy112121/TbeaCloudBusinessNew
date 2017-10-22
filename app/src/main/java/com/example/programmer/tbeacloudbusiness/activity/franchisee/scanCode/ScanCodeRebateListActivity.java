package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeRebateListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.HistorySearchActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 提现排名
 */

public class ScanCodeRebateListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private ExpandPopTabView mExpandTabView;
    private List<KeyValueBean> mRegionLists;//区域
    private PopOneListView mRegionView;
    private String mRegionId;//区域id
    private String mOrderitem, mOrder;

    ImageView moneyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_rebate_list);
        initTopbar("提现排名");
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

        mExpandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        List<String> mTopList = new ArrayList<>();
        mTopList.add("区域");
        mExpandTabView.addTopList(mTopList);
        addItem(mExpandTabView, mRegionLists, "默认排序", "区域");

        moneyView = (ImageView) findViewById(R.id.scan_code_rebate_list_money_image);


        findViewById(R.id.scan_code_rebate_list_money_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpandTabView.setViewColor();
                mRegionView.setSelectPostion();
                mOrderitem = "money";
                if ("".equals(mOrder) || "asc".equals(mOrder) || mOrder == null) {//升
                    mOrder = "desc";
                    moneyView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mOrder = "asc";
                    moneyView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mRefreshLayout.beginRefreshing();
            }
        });

        findViewById(R.id.top_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistorySearchActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
            }
        });
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
                            ScanCodeRebateListResponseModel model = (ScanCodeRebateListResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                mAdapter.addAll(model.data.takemoneyrankinglist);
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
                        ScanCodeRebateListResponseModel model = action.getWithdrawDepositDateList(mRegionId, mOrderitem, mOrder, mPage++, mPagesiz);
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

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mRegionView = new PopOneListView(this);
        mRegionView.setDefaultSelectByValue(defaultSelect);
        mRegionView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mOrder = "";
                moneyView.setImageResource(R.drawable.icon_arraw);

                if ("regionSelect".equals(key)) {
                    Intent intent = new Intent(mContext, RegionSelectActivity.class);
                    startActivityForResult(intent, 1000);
                } else {
                    mRegionId = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mRegionView);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            mRegionId = data.getStringExtra("ids");
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExpandTabView != null) {
            mExpandTabView.onExpandPopView();
        }
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

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<ScanCodeRebateListResponseModel.TakeMoneyranking> mList = new ArrayList<>();

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
                    R.layout.activity_scan_code_rebate_list_item, null);
            final ScanCodeRebateListResponseModel.TakeMoneyranking obj = mList.get(position);
            CircleImageView headView = (CircleImageView) view.findViewById(R.id.person_info_head);
            ImageView jobImageView = (ImageView) view.findViewById(R.id.person_info_personjobtitle);
            ((TextView) view.findViewById(R.id.person_info_name)).setText(obj.personname);
            ((TextView) view.findViewById(R.id.scanCode_rebate_money)).setText(obj.totlemoney);
            ((TextView) view.findViewById(R.id.scanCode_rebate_zone)).setText(obj.zone);
            String url = MyApplication.instance.getImgPath();
            ImageLoader.getInstance().displayImage(url + obj.thumbpicture, headView);
            ImageLoader.getInstance().displayImage(url + obj.persontypeicon, jobImageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WithdrawDepositDateHistoryActivity.class);
                    intent.putExtra("personOrCompany", obj.personorcompany);
                    intent.putExtra("payeeId", obj.electricianid);
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

        public void addAll(List<ScanCodeRebateListResponseModel.TakeMoneyranking> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
