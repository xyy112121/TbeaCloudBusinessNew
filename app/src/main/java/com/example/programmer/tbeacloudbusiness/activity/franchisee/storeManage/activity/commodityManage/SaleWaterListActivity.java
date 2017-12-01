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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.DateSelectActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.SaleWaterResponseModel;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 销售流水
 */

public class SaleWaterListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
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

    @BindView(R.id.masale_water_list_price)
    TextView mPriceView;


    private MyAdapter mAdapter;
    private int mPage = 1;
    private String mOrder, mOrderItem, mUserOrder, mNumberOrder, startTime, endTime;
    private final int RESULT_DATA_SELECT = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_water_list);
        ButterKnife.bind(this);
        initTopbar("销售流水");
        initView();
    }

    private void initView() {
        mAdapter = new MyAdapter(mContext, R.layout.activity_sale_water_list_item);
        mListView.setAdapter(mAdapter);

        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));

        mNumberOrder = "desc";
        mOrderItem = "number";
        mOrder = mNumberOrder;
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
                            SaleWaterResponseModel model = (SaleWaterResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.commoditylist != null) {
                                    mAdapter.addAll(model.data.commoditylist);
                                }

                                if (model.data.totleinfo != null) {
                                    mPriceView.setText(model.data.totleinfo.saletotlemoney);
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
                        SaleWaterResponseModel model = action.getSaleWater(startTime, endTime, mOrderItem, mOrder, mPage++, 10);
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

    @OnClick({R.id.commodity_sales_list_time, R.id.marker_info_list_user_layout, R.id.marker_info_list_number_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commodity_sales_list_time:
                Intent intent = new Intent(mContext, DateSelectActivity.class);
                startActivityForResult(intent, RESULT_DATA_SELECT);
                break;
            case R.id.marker_info_list_user_layout:
                mUserTv.setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
                if (("".equals(mUserOrder) || "asc".equals(mUserOrder)) || mUserOrder == null) {//升
                    mUserOrder = "desc";
                    mUserIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mUserOrder = "asc";
                    mUserIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrder = mUserOrder;
                mOrderItem = "user";
                mNumberOrder = "";
                mNumberIv.setImageResource(R.drawable.icon_arraw);
                mNumberTv.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_normal));
                mRefreshLayout.beginRefreshing();
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
                mOrderItem = "number";
                mOrder = mNumberOrder;
                mUserOrder = "";
                mUserIv.setImageResource(R.drawable.icon_arraw);
                mUserTv.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_normal));
                mRefreshLayout.beginRefreshing();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESULT_DATA_SELECT) {
            startTime = data.getStringExtra("startTime");
            endTime = data.getStringExtra("endTime");
            mOrderItem = "time";
            ((PublishTextRowView) findViewById(R.id.commodity_sales_list_time)).setValueText(startTime + "至" + endTime);
//            if ("time".equals(mOrderItem)) {
//                mOrderItem = "";
//                mOrder = "";
//            }
            mRefreshLayout.beginRefreshing();
        }
    }

    class MyAdapter extends ArrayAdapter<SaleWaterResponseModel.DataBean.CommoditylistBean> {
        private int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_sale_water_list_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            SaleWaterResponseModel.DataBean.CommoditylistBean obj = getItem(i);
            ImageLoader.getInstance().displayImage(obj.thumbpicture, holder.mThumbpictureView);
            holder.mNameView.setText(obj.name);
            holder.mPromotionView.setText(obj.promotion);
            holder.mSaleNumberView.setText(obj.salenumber+"");
            holder.mPriceView.setText(obj.price);
            holder.mSaleNumberView.setText(obj.salenumber+"");
            holder.mFinishTimeView.setText(obj.finishtime);
            return view;
        }


        class ViewHolder {
            @BindView(R.id.sales_water_item_thumbpicture)
            ImageView mThumbpictureView;
            @BindView(R.id.sales_water_item_name)
            TextView mNameView;
            @BindView(R.id.sales_water_item_promotion)
            TextView mPromotionView;
            @BindView(R.id.sales_water_item_price)
            TextView mPriceView;
            @BindView(R.id.sales_water_item_saleNumber)
            TextView mSaleNumberView;
            @BindView(R.id.sales_water_item_finishTime)
            TextView mFinishTimeView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}
