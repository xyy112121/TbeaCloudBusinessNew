package com.example.programmer.tbeacloudbusiness.activity.distributorManage.activity;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributorManage.action.DistributorManageAction;
import com.example.programmer.tbeacloudbusiness.activity.distributorManage.model.DMMainListReponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.HistorySearchActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.example.programmer.tbeacloudbusiness.utils.UtilAssistants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 分销商管理-分销商列表
 */

public class DistributorListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {

    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;

    private List<KeyValueBean> mRegionLists;//区域


    private String mZoneid, mOrderItem, mOrder, mMoneyOrder, mUserOrder, starttime, endtime;

    @BindView(R.id.pm_main_list_money_layout)
    LinearLayout mMoenyLayout;
    @BindView(R.id.pm_main_list_money_image)
    ImageView mMoneyView;

    @BindView(R.id.pm_main_list_user_layout)
    LinearLayout mUserLayout;
    @BindView(R.id.pm_main_list_user_image)
    ImageView mUserView;
    @BindView(R.id.expandtab_view)
    ExpandPopTabView expandTabView;
    private final int RESULT_DATA_SELECT = 1000;

    PopOneListView regionView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsitributor_list);
        initTopbar("分销商列表", "时间选择", this, R.drawable.icon_search_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HistorySearchActivity.class);
                intent.putExtra("type", "all");
                startActivity(intent);
            }
        });
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {

        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();
        List<String> mTopList = new ArrayList<>();
        mTopList.add("地区");
        expandTabView.addTopList(mTopList);
        addRegionItem(expandTabView, mRegionLists, "全部区域", "地区");

        mMoenyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mMoneyOrder) || "asc".equals(mMoneyOrder) || mMoneyOrder == null) {//升
                    mMoneyOrder = "desc";
                    mMoneyView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mMoneyOrder = "asc";
                    mMoneyView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mMoneyOrder;
                mOrderItem = "money";
                mRefreshLayout.beginRefreshing();

                mUserOrder = "";
                mUserView.setImageResource(R.drawable.icon_arraw);
                expandTabView.setViewColor();
                regionView.setSelectPostion();
            }
        });

        mUserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mUserOrder) || "asc".equals(mUserOrder) || mUserOrder == null) {//升
                    mUserOrder = "desc";
                    mUserView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mUserOrder = "asc";
                    mUserView.setImageResource(R.drawable.icon_arraw_bluegray);
                }

                mOrder = mUserOrder;
                mOrderItem = "user";
                mRefreshLayout.beginRefreshing();

                mMoneyOrder = "";
                mMoneyView.setImageResource(R.drawable.icon_arraw);
                expandTabView.setViewColor();
                regionView.setSelectPostion();
            }
        });
    }

    private void addRegionItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        regionView = new PopOneListView(this);
        regionView.setDefaultSelectByValue(defaultSelect);
        regionView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mMoneyOrder = "";
                mMoneyView.setImageResource(R.drawable.icon_arraw);
                mUserOrder = "";
                mUserView.setImageResource(R.drawable.icon_arraw);

                if ("regionSelect".equals(key)) {
                    Intent intent = new Intent(mContext, RegionSelectActivity.class);
                    startActivityForResult(intent, 1000);
                } else {
                    mZoneid = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });

        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth / 4;
        int width = (int) wid;
        expandTabView.addItemToExpandTab(defaultShowText, regionView, width, Gravity.CENTER);
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
                            DMMainListReponseModel model = (DMMainListReponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.distributelist != null) {
                                    mAdapter.addAll(model.data.distributelist);
                                }

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
                        DistributorManageAction action = new DistributorManageAction();
                        DMMainListReponseModel model = action.getMainList(mZoneid, starttime, endtime, mOrderItem, mOrder, mPage++, mPagesiz);
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
        //下拉刷新
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
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, DateSelectActivity.class);
        startActivityForResult(intent, RESULT_DATA_SELECT);
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
        } else if (requestCode == 1000 && resultCode == RESULT_OK) {
            mZoneid = data.getStringExtra("ids");
            mRefreshLayout.beginRefreshing();
        }
    }

    public class MyAdapter extends BaseAdapter {

        public List<DMMainListReponseModel.DataBean.DistributelistBean> mList = new ArrayList<>();

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
                convertView = getLayoutInflater().inflate(R.layout.activity_plumber_manage_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final DMMainListReponseModel.DataBean.DistributelistBean obj = mList.get(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mJobtitleView);
            holder.mNameView.setText(obj.mastername);
            holder.mCityzoneView.setText(obj.zone);
            holder.mTotlemoneyView.setText(obj.totletakemoney);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DustributorWithdrawalHistoryListActivity.class);
                    intent.putExtra("id", obj.distributorid);
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

        public void addAll(List<DMMainListReponseModel.DataBean.DistributelistBean> list) {
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
            ImageView mJobtitleView;
            @BindView(R.id.pm_main_list_item_cityzone)
            TextView mCityzoneView;
            @BindView(R.id.pm_main_list_item_totlemoney)
            TextView mTotlemoneyView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }
}


