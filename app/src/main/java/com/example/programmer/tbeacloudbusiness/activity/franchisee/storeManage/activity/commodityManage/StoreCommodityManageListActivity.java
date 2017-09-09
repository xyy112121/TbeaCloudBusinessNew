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
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityManageListRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityManageListResponseModel;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 商品管理
 */

public class StoreCommodityManageListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.store_commodity_manage_list_total)
    TextView mTotalView;
    @BindView(R.id.store_commodity_manage_list_price_tv)
    TextView mPriceTv;
    @BindView(R.id.store_commodity_manage_list_price_iv)
    ImageView mPriceIv;
    @BindView(R.id.store_commodity_manage_list_price_layout)
    LinearLayout mPriceLayout;
    private MyAdapter mAdapter;
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private int mPage = 1;
    private String mOrder, mOrderItem;
    private CommodityManageListRequestModel mRequest = new CommodityManageListRequestModel();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_store_commodity_manage_list);
            ButterKnife.bind(this);
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        initTopLayout();
        mAdapter = new MyAdapter(mContext, R.layout.activity_store_commodity_manage_list_item);
        mListView.setAdapter(mAdapter);

        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));


        mPriceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPriceTv.setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
                if ("".equals(mOrder) || "asc".equals(mOrder) || mOrder == null) {//升
                    mOrder = "desc";
                    mPriceIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mOrder = "asc";
                    mPriceIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mOrderItem = "price";
                mRefreshLayout.beginRefreshing();
                if (mListLayout.size() > 0) {
                    for (int i = 0; i < mListLayout.size(); i++) {
                        setViewColor(i);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }

    private void getData() {
        try {
            mRequest.page = mPage++;
            mRequest.pageSize = 14;
            mRequest.order = mOrder;
            mRequest.orderitem = mOrderItem;
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            CommodityManageListResponseModel model = (CommodityManageListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.commoditylist != null) {
                                    mAdapter.addAll(model.data.commoditylist);
                                }
                                if (model.data.totleinfo != null && model.data.totleinfo.totlecommoditynumber != null) {
                                    mTotalView.setText("数量：" + model.data.totleinfo.totlecommoditynumber);
                                } else {
                                    mTotalView.setText("数量：0");
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
                        CommodityManageListResponseModel model = action.getCommodityList(mRequest);
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

    /**
     * 初始化顶部点击菜单
     */
    private void initTopLayout() {
        String[] topTexts = new String[]{"推荐", "销量", "新品"};
        //获取屏幕的宽度
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        int width = (new Double(screenWidth * 0.75)).intValue();

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.store_commodity_intro_top_layout);
        for (int i = 0; i < topTexts.length; i++) {
            final int index = i;
            final FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.fragment_company_top_layout_item, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width / topTexts.length, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(lp);
            TextView t = (TextView) layout.findViewById(R.id.fragment_company_top_tv);
            t.setText(topTexts[i]);
            if (i == 0) {
                ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.blue));
//
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPriceTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    mPriceIv.setImageResource(R.drawable.icon_arraw);
                    mIndex2 = mIndex;
                    mIndex = index;
                    if (mIndex2 != -1 && mIndex != mIndex2) {
                        ((TextView) view.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                        setViewColor();
                    }
                    switch (mIndex) {
                        case 0:
                            mOrderItem = "";
                            break;
                        case 1:
                            mOrderItem = "salenumber";
                            break;
                        case 2:
                            mOrderItem = "time";
                            break;
                    }
                    mRefreshLayout.beginRefreshing();
                }
            });
            mListLayout.add(layout);
            parentLayout.addView(layout);
        }
    }

    private void setViewColor() {
        FrameLayout layout = mListLayout.get(mIndex2);
        ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
    }

    private void setViewColor(int index) {
        FrameLayout layout = mListLayout.get(index);
        ((TextView) layout.findViewById(R.id.fragment_company_top_tv)).setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.clear();
        mPage = 1;
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getData();
        return true;
    }


    @OnClick({R.id.top_left, R.id.top_right, R.id.top_right_text_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                finish();
                break;
            case R.id.top_right:
                //搜索
                break;
            case R.id.top_right_text_layout:
                Intent intent = new Intent(mContext, StoreCommodityManageAddActivity.class);
                intent.putExtra("flag", "add");
                startActivity(intent);
                break;
        }
    }

    class MyAdapter extends ArrayAdapter<CommodityManageListResponseModel.DataBean.CommoditylistBean> {
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
            final CommodityManageListResponseModel.DataBean.CommoditylistBean obj = getItem(i);
            holder.mNameView.setText(obj.commodityname);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mThumbView);
            holder.mPriceView.setText(obj.price);
            holder.mSalenumberView.setText("已售出：" + obj.salenumber);
            holder.mEvaluatenumberView.setText("已评价：" + obj.evaluatenumber);
            if (obj.recommended == 1) {
                holder.mRecommendedView.setText("推荐商品：是");
            } else {
                holder.mRecommendedView.setText("推荐商品：否");
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MarketInfoActivity.class);
                    intent.putExtra("commodityId", obj.commodityid);
                    startActivity(intent);
                }
            });
            return view;
        }

        class ViewHolder {
            @BindView(R.id.store_commodity_thumb)
            ImageView mThumbView;
            @BindView(R.id.store_commodity_name)
            TextView mNameView;
            @BindView(R.id.store_commodity_price)
            TextView mPriceView;
            @BindView(R.id.store_commodity_salenumber)
            TextView mSalenumberView;
            @BindView(R.id.store_commodity_evaluatenumber)
            TextView mEvaluatenumberView;
            @BindView(R.id.store_commodity_recommended)
            TextView mRecommendedView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
