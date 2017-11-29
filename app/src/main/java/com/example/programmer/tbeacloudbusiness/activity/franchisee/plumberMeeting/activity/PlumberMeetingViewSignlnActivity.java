package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity.PlumberManageLoginStatisticsActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity.PlumberManageSignHistoryListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingSignListReponseModel;
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
 * 会议签到
 */

public class PlumberMeetingViewSignlnActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.plumber_meeting_sign_signnumber)
    TextView mSignnumberView;

    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mRegionLists;//区域,状态

    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private String mZoneid, mOrderItem, mOrder, mCodeOrder, mTimeOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_view_sign_in_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTopbar("会议签到");
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mRegionLists, "全部区域", "区域");

        final ImageView codeView = getViewById(R.id.activity_plumber_meeting_main_list_user);
        final ImageView timeView = getViewById(R.id.activity_plumber_meeting_main_list_time);

        findViewById(R.id.activity_plumber_meeting_main_list_user_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mCodeOrder) || "asc".equals(mCodeOrder) || mCodeOrder == null) {//升
                    mCodeOrder = "desc";
                    codeView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mCodeOrder = "asc";
                    codeView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mCodeOrder;
                mOrderItem = "signuser";
                mRefreshLayout.beginRefreshing();

                mTimeOrder = "";
                timeView.setImageResource(R.drawable.icon_arraw);
            }
        });


        findViewById(R.id.activity_plumber_meeting_main_list_time_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mTimeOrder) || "asc".equals(mTimeOrder) || mTimeOrder == null) {//升
                    mTimeOrder = "desc";
                    timeView.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mTimeOrder = "asc";
                    timeView.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mTimeOrder;
                mOrderItem = "signtime ";
                mRefreshLayout.beginRefreshing();

                mCodeOrder = "";
                codeView.setImageResource(R.drawable.icon_arraw);
            }
        });

    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if ("regionSelect".equals(key)) {
                    Intent intent = new Intent(mContext, RegionSelectActivity.class);
                    startActivityForResult(intent, 1000);
                } else {
                    mZoneid = "";
                    mRefreshLayout.beginRefreshing();
                }
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }

    /**
     * 从服务器获取数据
     */
    private void getListData() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mRefreshLayout.endRefreshing();
                mRefreshLayout.endLoadingMore();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        PlumberMeetingSignListReponseModel model = (PlumberMeetingSignListReponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.meetingsignlist != null) {
                                mAdapter.addAll(model.data.meetingsignlist);
                            }
                            if (model.data.meetingsigntotleinfo != null) {
                                mSignnumberView.setText("签到人数：" + model.data.meetingsigntotleinfo.totlesignnumber);
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
                    PlumberMeetingAction action = new PlumberMeetingAction();
                    String id = getIntent().getStringExtra("meetingId");
                    PlumberMeetingSignListReponseModel model = action.getViewSignList(id, mZoneid, mOrderItem, mOrder, mPage++, mPagesiz);
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        下拉刷新
        mAdapter.removeAll();
        mPage = 1;
        getListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        getListData();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            mZoneid = data.getStringExtra("ids");
            mRefreshLayout.beginRefreshing();
        }
    }

    public class MyAdapter extends BaseAdapter {
        public List<PlumberMeetingSignListReponseModel.MeetingSign> mList = new ArrayList<>();

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
                convertView = getLayoutInflater().inflate(R.layout.activity_plumber_meeting_sign_in_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final PlumberMeetingSignListReponseModel.MeetingSign obj = mList.get(position);

            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mNameView.setText(obj.name);
            holder.mSignItemZoneView.setText(obj.zone);
            holder.mSignItemSignTimeView.setText(obj.signtime);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, PlumberManageSignHistoryListActivity.class);
                    intent.putExtra("id",obj.userid);
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

        public void addAll(List<PlumberMeetingSignListReponseModel.MeetingSign> list) {
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
            @BindView(R.id.pm_sign_item_zone)
            TextView mSignItemZoneView;
            @BindView(R.id.pm_sign_item_signtime)
            TextView mSignItemSignTimeView;

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
        try {
            mRegionLists = new ArrayList<>();
            mRegionLists.add(new KeyValueBean("", "全部区域"));
            mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}