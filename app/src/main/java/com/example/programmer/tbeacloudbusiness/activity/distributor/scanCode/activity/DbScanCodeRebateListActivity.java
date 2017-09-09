package com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributor.scanCode.action.DBScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.WithdrawDepositDateHistoryActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeRebateListResponseModel;
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
 * 提现排名-分销商
 */

public class DbScanCodeRebateListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_rebate_list);
        initTopbar("提现排名");
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext, R.layout.activity_db_scan_code_rebate_list_item);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();

        mExpandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(mExpandTabView, mRegionLists, "默认排序", "区域");

        final ImageView moneyView = (ImageView) findViewById(R.id.scan_code_rebate_list_money_image);


        findViewById(R.id.scan_code_rebate_list_money_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        DBScanCodeAction action = new DBScanCodeAction();
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
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
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
        mAdapter.clear();
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

    class MyAdapter extends ArrayAdapter<ScanCodeRebateListResponseModel.TakeMoneyranking> {
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
            final ScanCodeRebateListResponseModel.TakeMoneyranking obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mMoneyView.setText(obj.totlemoney);
            holder.mNameView.setText(obj.personname);
            holder.mRightView.setVisibility(View.GONE);
            holder.mZoneView.setText(obj.zone);
            holder.mCompanynameView.setText(obj.personorcompany);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DbWithdrawDepositDateHistoryActivity.class);
                    intent.putExtra("personOrCompany", obj.personorcompany);
                    intent.putExtra("payeeId ", obj.electricianid);
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
            TextView mCompanynameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;
            @BindView(R.id.scanCode_rebate_zone)
            TextView mZoneView;
            @BindView(R.id.scanCode_rebate_money)
            TextView mMoneyView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
