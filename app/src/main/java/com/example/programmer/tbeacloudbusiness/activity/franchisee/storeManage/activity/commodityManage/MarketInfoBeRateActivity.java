package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.commodityManage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.EvaluateListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 销售详情-已评价
 */

public class MarketInfoBeRateActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.marker_info_list_user_tv)
    TextView mUserTv;
    @BindView(R.id.marker_info_list_user_iv)
    ImageView mUserIv;
    @BindView(R.id.marker_info_list_time_tv)
    TextView mTimeTv;
    @BindView(R.id.marker_info_list_time_iv)
    ImageView mTimeIv;

    private MyAdapter mAdapter;
    private int mPage = 1;
    private String mOrder, mOrderItem, mUserOrder, mTimeOrder;

    private String mCommodityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info_be_rate);
        ButterKnife.bind(this);
        mCommodityId = getIntent().getStringExtra("commodityId");
        initTopbar("已评价");
        initView();
    }

    private void initView() {
        mAdapter = new MyAdapter(mContext, R.layout.activity_market_info_be_rate_list_item);
        mListView.setAdapter(mAdapter);

        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, false));
        mRefreshLayout.beginRefreshing();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, MarketInfoBeRateInfoActivity.class);
                startActivity(intent);
            }
        });
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
                            EvaluateListResponseModel model = (EvaluateListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {

                                if (model.data.commodityinfo != null) {
                                    EvaluateListResponseModel.DataBean.CommodityinfoBean obj = model.data.commodityinfo;
                                    ImageView iv = getViewById(R.id.market_info_thumbpicture);
                                    ImageLoader.getInstance().displayImage(obj.thumbpicture, iv);

                                    ((TextView) findViewById(R.id.market_info_name)).setText(obj.commodityname);
                                    ((TextView) findViewById(R.id.market_info_price)).setText(obj.price + "");
                                    ((TextView) findViewById(R.id.market_info_publishtime)).setText("发布时间：" + obj.publishtime);
                                }

                                if (model.data.evaluatelist != null) {
                                    mAdapter.addAll(model.data.evaluatelist);
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
                        StoreManageAction action = new StoreManageAction();
                        EvaluateListResponseModel model = action.getEvaluateList(mCommodityId, mOrderItem, mOrder, mPage++, 10);
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
    public void onClick(View view) {

    }

    @OnClick({R.id.marker_info_list_user_layout, R.id.marker_info_list_time_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.marker_info_list_user_layout:
                mUserTv.setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
                if (("".equals(mUserOrder) || "asc".equals(mUserOrder) || mUserOrder == null)) {//升
                    mUserOrder = "desc";
                    mUserIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mUserOrder = "asc";
                    mUserIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mUserOrder;
                mOrderItem = "user";
                mRefreshLayout.beginRefreshing();

                mTimeOrder = "";
                mTimeIv.setImageResource(R.drawable.icon_arraw);
                break;
            case R.id.marker_info_list_time_layout:
                mTimeTv.setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
                if (("".equals(mTimeOrder) || "asc".equals(mTimeOrder) || mTimeOrder == null)) {//升
                    mTimeOrder = "desc";
                    mTimeIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mTimeOrder = "asc";
                    mTimeIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mTimeOrder;
                mOrderItem = "time";
                mRefreshLayout.beginRefreshing();

                mUserOrder = "";
                mUserIv.setImageResource(R.drawable.icon_arraw);
                break;


        }
    }

    class MyAdapter extends ArrayAdapter<EvaluateListResponseModel.DataBean.EcaluateBean> {

        private int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(resourceId, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            EvaluateListResponseModel.DataBean.EcaluateBean obj = getItem(i);
            ImageLoader.getInstance().displayImage(obj.personthumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mNameView.setText(obj.personname);
            holder.mContentView.setText(obj.evaluatecontent);
            holder.mTimeView.setText(obj.evaluatetime);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return view;
        }

        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.evaluate_item_content)
            TextView mContentView;
            @BindView(R.id.evaluate_item_time)
            TextView mTimeView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
