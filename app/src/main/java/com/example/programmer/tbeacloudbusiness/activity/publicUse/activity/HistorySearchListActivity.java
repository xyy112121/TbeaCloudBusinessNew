package com.example.programmer.tbeacloudbusiness.activity.publicUse.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.MeetingViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.distributorManage.activity.DustributorWithdrawalHistoryListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity.PersonManageViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.PlumberMeetingViewActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.WithdrawDepositDateHistoryActivity;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.action.PublicAction;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.SearchResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.activity.ProductPresentationInfoActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 商品搜索结果
 */

public class HistorySearchListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private String mKeyword = "";
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search_list);
        initTopbar("搜索结果");
        mKeyword = getIntent().getStringExtra("keyword");
        mType = getIntent().getStringExtra("type");
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext, R.layout.activity_history_search_list_item);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(HistorySearchListActivity.this, true));
        mRefreshLayout.beginRefreshing();
    }

    /**
     * 获取数据
     */
    public void getListDate() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        SearchResponseModel re = (SearchResponseModel) msg.obj;
                        if (re.isSuccess() && re.data != null) {
                            mAdapter.addAll(re.data.searchresultlist);
                        } else {
                            showMessage(re.getMsg());
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
                    PublicAction userAction = new PublicAction();
                    SearchResponseModel re = userAction.getSearchList(mType, mKeyword);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
//        mAdapter.removeAll();
        getListDate();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        return false;
    }

    class MyAdapter extends ArrayAdapter<SearchResponseModel.DataBean.SearchresultlistBean> {
        int resourceId;


        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(resourceId, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTypeView.setText(getItem(position).objecttypelable);
            holder.mCompanynameView.setText(getItem(position).description);
            holder.mNameViw.setText(getItem(position).name);
            ImageLoader.getInstance().displayImage(getItem(position).thumbpicture, holder.mHeadView);
            holder.mPersonjobtitleView.setVisibility(View.GONE);
            holder.mRightView.setVisibility(View.GONE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String type = getItem(position).objecttype;
                    String id = getItem(position).objectprimarykey;
                    //全部(all)，水电工 (electrician)，分销商(distributor)，水电工会议 (servicemeeting)，
                    // 扫码返利(scanrebate)，特变卫士服务单(tbeaservice)，订单(tbeaorder)，
                    // 特变电工内的产品搜索(tbeaproduct)
                    Intent intent = new Intent();
                    if ("electrician".equals(type)) {//水电工
                        intent.setClass(mContext, PersonManageViewActivity.class);
                        intent.putExtra("id", id);

                    } else if ("servicemeeting".equals(type)) {//水电工会议
                        intent.setClass(mContext, MeetingViewActivity.class);
                        intent.putExtra("id", id);

                    } else if ("distributor".equals(type)) {//分销商
                        intent.setClass(mContext, DustributorWithdrawalHistoryListActivity.class);
                        intent.putExtra("id", id);
                        
                    } else if ("scanrebate".equals(type)) {//扫码返利
//                        intent.setClass(mContext, WithdrawDepositDateHistoryActivity.class);
//                        intent.putExtra("personOrCompany", getItem(position).personorcompany);
//                        intent.putExtra("payeeId", obj.electricianid);

                    } else if ("tbeaproduct".equals(type)) {//特变电工内的产品搜索
                        intent.setClass(mContext, ProductPresentationInfoActivity.class);
                        intent.putExtra("id", id);
                    }
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.search_item_type)
            TextView mTypeView;
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameViw;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanynameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


//        public void addAll(List<Commodith> list){
//            mList.addAll(list);
//            notifyDataSetChanged();
//        }
//
//        public void removeAll() {
//            mList.clear();
//            notifyDataSetChanged();
//        }


    }
}
