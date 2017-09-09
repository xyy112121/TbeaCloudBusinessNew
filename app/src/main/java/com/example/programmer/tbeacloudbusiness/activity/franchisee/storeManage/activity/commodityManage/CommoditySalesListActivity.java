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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommoditySalesResponseModel;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 商品销量
 */

public class CommoditySalesListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
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
    private String mOrder, mOrderItem, mUserOrder, mNumberOrder, mTimeOrder, startTime, endTime = "";
    private final int RESULT_DATA_SELECT = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_sales_list);
        ButterKnife.bind(this);
        initTopbar("商品销量");
        initView();
    }

    private void initView() {
        mAdapter = new MyAdapter(mContext, R.layout.activity_commodity_sales_list_item);
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
                            CommoditySalesResponseModel model = (CommoditySalesResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {

                                if (model.data.commoditylist != null) {
                                    mAdapter.addAll(model.data.commoditylist);
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
                        CommoditySalesResponseModel model = action.getCommoditySalesList(startTime, endTime, mOrderItem, mOrder, mPage++, 10);
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

    @OnClick({R.id.commodity_sales_list_time, R.id.marker_info_list_user_layout, R.id.marker_info_list_number_layout, R.id.marker_info_list_time_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commodity_sales_list_time:
                Intent intent = new Intent(mContext, DateSelectActivity.class);
                startActivityForResult(intent, RESULT_DATA_SELECT);
                break;
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
                mTimeOrder = "";
                setScreenView(mTimeIv, mTimeTv);
                setScreenView(mNumberIv, mNumberTv);

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
                mTimeOrder = "";
                setScreenView(mTimeIv, mTimeTv);
                setScreenView(mUserIv, mUserTv);

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
                mNumberOrder = "";
                setScreenView(mNumberIv, mNumberTv);
                setScreenView(mUserIv, mUserTv);
                break;
        }
    }

    private void setScreenView(ImageView iv, TextView tv) {
        iv.setImageResource(R.drawable.icon_arraw);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
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

    class MyAdapter extends ArrayAdapter<CommoditySalesResponseModel.DataBean.CommodityinfoBean> {
        private int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_commodity_sales_list_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            final CommoditySalesResponseModel.DataBean.CommodityinfoBean obj = getItem(i);
            ImageLoader.getInstance().displayImage(obj.thumbpicture, holder.mPictureView);
            holder.mNameView.setText(obj.name);
            holder.mPublishtimeView.setText(obj.promotion);
            holder.mSaleNumberView.setText(obj.salenumber);
            holder.mPriceView.setText(obj.price);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CommoditySalesInfoActivity.class);
                    intent.putExtra("commdityId", obj.commodityid);
                    startActivity(intent);
                }
            });
            return view;
        }

        class ViewHolder {
            @BindView(R.id.market_info_thumbpicture)
            ImageView mPictureView;
            @BindView(R.id.market_info_name)
            TextView mNameView;
            @BindView(R.id.market_info_publishtime)
            TextView mPublishtimeView;
            @BindView(R.id.commodity_sales_list_SaleNumber)
            TextView mSaleNumberView;
            @BindView(R.id.commodity_sales_list_price)
            TextView mPriceView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
