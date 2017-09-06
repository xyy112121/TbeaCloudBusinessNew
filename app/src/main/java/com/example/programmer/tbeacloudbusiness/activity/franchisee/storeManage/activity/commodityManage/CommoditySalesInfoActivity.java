package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.commodityManage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommoditySalesInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 商品销售详情
 */

public class CommoditySalesInfoActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.marker_info_list_user_tv)
    TextView mUserTv;
    @BindView(R.id.marker_info_list_user_iv)
    ImageView mUserIv;
    @BindView(R.id.marker_info_list_number_tv)
    TextView mNumberTv;
    @BindView(R.id.marker_info_list_number_iv)
    ImageView mNumberIv;
    @BindView(R.id.marker_info_list_time_tv)
    TextView mTimeTv;
    @BindView(R.id.marker_info_list_time_iv)
    ImageView mTimeIv;

    private MyAdapter mAdapter;
    private int mPage = 1;
    private String mOrder, mOrderItem, mUserOrder, mNumberOrder, mTimeOrder;

    private String mCommdityId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_sales_info);
        ButterKnife.bind(this);
        mCommdityId = getIntent().getStringExtra("commdityId");
        initTopbar("销售详情");
        initView();
    }

    private void initView() {
        mAdapter = new MyAdapter(mContext, R.layout.activity_market_info_be_sold_list_item);
        mListView.setAdapter(mAdapter);

        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, false));
        mRefreshLayout.beginRefreshing();
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
        return false;
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
                            CommoditySalesInfoResponseModel model = (CommoditySalesInfoResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {

                                if (model.data.commodityinfo != null) {
                                    CommoditySalesInfoResponseModel.DataBean.CommodityinfoBean obj = model.data.commodityinfo;
                                    ImageView iv = getViewById(R.id.market_info_thumbpicture);
                                    ImageLoader.getInstance().displayImage(obj.thumbpicture, iv);

                                    ((TextView) findViewById(R.id.market_info_name)).setText(obj.name);
                                    ((TextView) findViewById(R.id.market_info_promotion)).setText(obj.promotion);
                                    ((TextView) findViewById(R.id.market_info_price)).setText(obj.price + "");
                                    ((TextView) findViewById(R.id.market_info_salenumber)).setText("商品销量：" + obj.salenumber);
                                }

                                if (model.data.salelist != null) {
                                    mAdapter.addAll(model.data.salelist);
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
                        StoreManageAction action = new StoreManageAction();
                        CommoditySalesInfoResponseModel model = action.getSaleInfo(mCommdityId, mOrderItem, mOrder, mPage++, 10);
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

    @OnClick({R.id.marker_info_list_user_layout, R.id.marker_info_list_number_layout, R.id.marker_info_list_time_layout})
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

                mNumberOrder = "";
                mNumberIv.setImageResource(R.drawable.icon_arraw);
                mTimeOrder = "";
                mTimeIv.setImageResource(R.drawable.icon_arraw);
                break;
            case R.id.marker_info_list_number_layout:
                mNumberTv.setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
                if (("".equals(mNumberOrder) || "asc".equals(mNumberOrder) || mNumberOrder == null)) {//升
                    mNumberOrder = "desc";
                    mNumberIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mNumberOrder = "asc";
                    mNumberIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mNumberOrder;
                mOrderItem = "number";
                mRefreshLayout.beginRefreshing();

                mUserOrder = "";
                mUserIv.setImageResource(R.drawable.icon_arraw);
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

                mNumberOrder = "";
                mNumberIv.setImageResource(R.drawable.icon_arraw);
                mUserOrder = "";
                mUserIv.setImageResource(R.drawable.icon_arraw);
                break;


        }
    }

    class MyAdapter extends ArrayAdapter<CommoditySalesInfoResponseModel.DataBean.SalelistBean> {
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

            CommoditySalesInfoResponseModel.DataBean.SalelistBean obj = getItem(i);
            ImageLoader.getInstance().displayImage(obj.personthumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(obj.persontypeicon, holder.mPersonjobtitleView);
            holder.mNameView.setText(obj.personname);
            holder.mBuyamountView.setText("X" + obj.buyamount);
            holder.mTimeView.setText(obj.paytime);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CommoditySalesListActivity.class);
                    startActivity(intent);
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
            @BindView(R.id.market_info_item_buyamount)
            TextView mBuyamountView;
            @BindView(R.id.market_info_item_time)
            TextView mTimeView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}