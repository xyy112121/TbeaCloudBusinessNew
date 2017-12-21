package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.annotation.SuppressLint;
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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingUserTypeResonpseModel;
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
 * 水电工列表
 */

public class PlumberManageMainListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.pm_main_list_search_text)
    TextView mSearchTextView;
    @BindView(R.id.pm_main_list_top_layout)
    LinearLayout mTopLayout;
    @BindView(R.id.pm_main_list_top_layout1)
    LinearLayout mTopLayout1;

    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mRegionLists;//区域
    private PopOneListView mUserTypeView;
    private PopOneListView mRegionView;

    private String mName, mElectricianownertypeid, mZoneid, mOrderItem, mOrder;
    ImageView moneyView;
    LinearLayout moenyLayout;

    List<String> mTopList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_manage_main_list);
        initTopbar("水电工列表");
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


        if (!"distributor".equals(getIntent().getStringExtra("type"))) {//总经销商
            expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
            mTopList.add("用户");
            addUserTypeItem(expandTabView, null, "", "用户");
            mTopLayout.setVisibility(View.VISIBLE);
            mTopLayout1.setVisibility(View.GONE);
            moneyView = getViewById(R.id.pm_main_list_money_image);
            moenyLayout = getViewById(R.id.pm_main_list_money_image_layout);
            getUserTypeList();
        } else {
            expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view1);
            mTopLayout.setVisibility(View.GONE);
            mTopLayout1.setVisibility(View.VISIBLE);
            moneyView = getViewById(R.id.pm_main_list_money_image1);
            moenyLayout = getViewById(R.id.pm_main_list_money_image_layout1);
        }
        addRegionItem(expandTabView, mRegionLists, "全部区域", "区域");
        mTopList.add("区域");
        expandTabView.addTopList(mTopList);

        mSearchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //水电工管理
                Intent intent = new Intent(mContext, HistorySearchActivity.class);
                intent.putExtra("type", "electrician");
                startActivity(intent);
            }
        });


        moenyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandTabView.setViewColor();
                mUserTypeView.setSelectPostion();
                mRegionView.setSelectPostion();
                if ("".equals(mOrder) || "asc".equals(mOrder) || mOrder == null) {//升
                    mOrder = "desc";
                    moneyView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mOrder = "asc";
                    moneyView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrderItem = "money";
                mRefreshLayout.beginRefreshing();
            }
        });

    }

    private void getUserTypeList() {
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        PlumberMeetingUserTypeResonpseModel model = (PlumberMeetingUserTypeResonpseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.electricianownertypelist != null)
                                mUserTypeView.setAdapterData(model.data.electricianownertypelist);

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
                    PlumberMeetingAction action = new PlumberMeetingAction();
                    PlumberMeetingUserTypeResonpseModel model = action.getUserType();
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }

    /**
     * 从服务器获取数据
     */
    private void getListData() {
        try {
            @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            PmMainListResponseModel model = (PmMainListResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.electricianlist != null) {
                                    mAdapter.addAll(model.data.electricianlist);
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
                        PlumberManageAction action = new PlumberManageAction();
                        String fdistributorid = getIntent().getStringExtra("id");
                        if ("distributor".equals(getIntent().getStringExtra("type"))) {//总经销商
                            fdistributorid = null;
                        }

                        PmMainListResponseModel model = action.getPlumberManageMainList(fdistributorid, mName, mElectricianownertypeid, mZoneid, mOrderItem, mOrder, mPage++, mPagesiz);
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

    private void addUserTypeItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mUserTypeView = new PopOneListView(this);
        mUserTypeView.setDefaultSelectByValue(defaultSelect);
        mUserTypeView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mRegionView.setSelectPostion();
                mOrder = "";
                moneyView.setImageResource(R.drawable.icon_arraw);

                mElectricianownertypeid = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        float displayWidth = UtilAssistants.getWidth(mContext);
        double wid = displayWidth / 2;
        int width = (int) wid;
        expandTabView.addItemToExpandTab(defaultShowText, mUserTypeView, width, Gravity.LEFT);
    }

    private void addRegionItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mRegionView = new PopOneListView(this);
        mRegionView.setDefaultSelectByValue(defaultSelect);
        mRegionView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue), value);
                mUserTypeView.setSelectPostion();
                mOrder = "";
                moneyView.setImageResource(R.drawable.icon_arraw);
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
        expandTabView.addItemToExpandTab(defaultShowText, mRegionView, width, Gravity.CENTER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            mZoneid = data.getStringExtra("ids");
            mRefreshLayout.beginRefreshing();
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

    public class MyAdapter extends BaseAdapter {

        public List<PmMainListResponseModel.Electrician> mList = new ArrayList<>();

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
            final PmMainListResponseModel.Electrician obj = mList.get(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mJobtitleView);
            holder.mJobtitleView.setVisibility(View.GONE);
            holder.mNameView.setText(obj.personname);
            holder.mCityzoneView.setText(obj.cityzone);
            holder.mTotlemoneyView.setText(obj.totlemoney);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PlumberManageWithdrawalHistoryListActivity.class);
                    intent.putExtra("id", obj.userid);
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

        public void addAll(List<PmMainListResponseModel.Electrician> list) {
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
