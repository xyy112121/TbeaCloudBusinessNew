package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity.PlumberManageLoginStatisticsActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingSignListReponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingUserTypeResonpseModel;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 会议签到列表
 */

public class PlumberMeetingSignInListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.plumber_meeting_sign_search_text)
    TextView mSearchTextView;

    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;

    @BindView(R.id.activity_plumber_meeting_main_list_user)
    ImageView codeView;
    @BindView(R.id.activity_plumber_meeting_main_list_time)
    ImageView timeView;

    private ExpandPopTabView expandTabView;
    private PopOneListView mUserTypeView;


    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private String name, electricianownertypeid, starttime, endtime, mOrderItem, mOrder, mCodeOrder, mTimeOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_sign_in_list);
        ButterKnife.bind(this);
        initView();
        getUserTypeList();
    }

    private void initView() {
        initTopbar("水电工签到列表");
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();

        mSearchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HistorySearchActivity.class);
                intent.putExtra("type", "servicemeeting");
                startActivity(intent);
            }
        });


        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        List<String> mTopList = new ArrayList<>();
        mTopList.add("用户");
        expandTabView.addTopList(mTopList);
        addUserTypeItem(expandTabView, null, "", "用户");



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
                mOrderItem = "signnumber";
                mRefreshLayout.beginRefreshing();

                mTimeOrder = "";
                timeView.setImageResource(R.drawable.icon_arraw);
                expandTabView.setViewColor();
                mUserTypeView.setSelectPostion();
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
                expandTabView.setViewColor();
                mUserTypeView.setSelectPostion();
            }
        });
    }

    private void addUserTypeItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mUserTypeView = new PopOneListView(this);
        mUserTypeView.setDefaultSelectByValue(defaultSelect);
        mUserTypeView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue),value);
                mCodeOrder = "";
                codeView.setImageResource(R.drawable.icon_arraw);
                mTimeOrder = "";
                timeView.setImageResource(R.drawable.icon_arraw);

                electricianownertypeid = key;
                mRefreshLayout.beginRefreshing();
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mUserTypeView, Gravity.LEFT);
    }

    private void getUserTypeList() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        PlumberMeetingUserTypeResonpseModel model = (PlumberMeetingUserTypeResonpseModel) msg.obj;
                        if (model.isSuccess()) {
                            mUserTypeView.setAdapterData(model.data.electricianownertypelist);

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
                    PlumberMeetingSignListReponseModel model = action.getSignList
                            (name, electricianownertypeid, starttime, endtime, mOrderItem, mOrder, mPage++, mPagesiz);
                    handler.obtainMessage(ThreadState.SUCCESS, model).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

    }




    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
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
            PlumberMeetingSignListReponseModel.MeetingSign obj = mList.get(position);

            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mNameView.setText(obj.name);
            holder.mSignItemZoneView.setText(obj.totlesignnumber);
            holder.mSignItemSignTimeView.setText(obj.signtime);
            holder.mSignItemZoneView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, PlumberManageLoginStatisticsActivity.class);
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
}
