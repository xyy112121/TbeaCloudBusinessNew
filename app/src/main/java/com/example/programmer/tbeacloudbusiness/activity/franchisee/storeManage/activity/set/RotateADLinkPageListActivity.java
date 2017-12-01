package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

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
import android.widget.CheckBox;
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
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.DynamicListRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.DynamicListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 链接页面
 */

public class RotateADLinkPageListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    LinearLayout parentLayout;
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.listview1)
    ListView mListView1;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rl_recyclerview_refresh1)
    BGARefreshLayout mRefreshLayout1;
    @BindView(R.id.store_commodity_manage_list_price_tv)
    TextView mPriceTv;
    @BindView(R.id.store_commodity_manage_list_price_iv)
    ImageView mPriceIv;
    @BindView(R.id.rotate_ad_edit_link_page_release_time_iv)
    ImageView mTimeIv;
    @BindView(R.id.rotate_ad_edit_link_page_release_time_tv)
    TextView mTimeTv;
    @BindView(R.id.store_commodity_manage_list_price_layout)
    LinearLayout mPriceLayout;
    @BindView(R.id.top_layout)
    LinearLayout mTopLayout;
    @BindView(R.id.top_layout1)
    LinearLayout mTopLayout1;
    @BindView(R.id.rotate_ad_edit_link_page_type)
    PublishTextRowView mTypeView;
    private MyAdapter mAdapter;
    private MyAdapter1 mAdapter1;
    private int mIndex = 0;
    private int mIndex2 = -1;//前一次点击的下标
    private List<FrameLayout> mListLayout = new ArrayList<>();
    private int mPage = 1;
    private int mPage1 = 1;
    private String mOrder, mOrderItem;
    private CommodityManageListRequestModel mRequest = new CommodityManageListRequestModel();
    private DynamicListRequestModel mRequest1 = new DynamicListRequestModel();
    private String mFlag = "commodity";//商品列表


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_ad_linkpage_list);
        ButterKnife.bind(this);
        initTopbar("链接页面");
        initView();
    }

    private void initView() {

        parentLayout = (LinearLayout) findViewById(R.id.parent_layout);

        initTopLayout();
        mAdapter = new MyAdapter(mContext, R.layout.activity_store_commodity_manage_list_item);
        mListView.setAdapter(mAdapter);

        mAdapter1 = new MyAdapter1(mContext, R.layout.activity_shop_dynamic_list_item);
        mListView1.setAdapter(mAdapter1);

        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();

        mRefreshLayout1.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                mAdapter1.clear();
                mPage1 = 1;
                getData1();

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                getData1();
                return false;
            }
        });
        mRefreshLayout1.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout1.beginRefreshing();

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

        mTopLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeTv.setTextColor(ContextCompat.getColor(mContext, R.color.head_color));
                if ("".equals(mRequest1.order) || "asc".equals(mRequest1.order) || mRequest1.order == null) {//升
                    mRequest1.order = "desc";
                    mTimeIv.setImageResource(R.drawable.icon_arraw_grayblue);
                } else {
                    mRequest1.order = "asc";
                    mTimeIv.setImageResource(R.drawable.icon_arraw_bluegray);
                }
                mRequest1.orderitem = "time";
                mRefreshLayout1.beginRefreshing();
            }
        });

        mTypeView.setValueText("店铺商品");

        final List<String> typeList = new ArrayList<>();
        typeList.add("店铺商品");
        typeList.add("店铺动态");

        mTypeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
                popWindow1.init(parentLayout, R.layout.pop_window_header1, R.layout.pop_window_tv, typeList);
                popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
                    @Override
                    public void onItemClick(String text) {
                        if ("店铺商品".equals(text)) {
                            mFlag = "commodity";
                            mTopLayout.setVisibility(View.VISIBLE);
                            mTopLayout1.setVisibility(View.GONE);
                            mRefreshLayout.beginRefreshing();
                            mRefreshLayout.setVisibility(View.VISIBLE);
                            mRefreshLayout1.setVisibility(View.GONE);
                        } else if ("店铺动态".equals(text)) {
                            mFlag = "news";
                            mTopLayout1.setVisibility(View.VISIBLE);
                            mTopLayout.setVisibility(View.GONE);
                            mRefreshLayout1.beginRefreshing();
                            mRefreshLayout.setVisibility(View.GONE);
                            mRefreshLayout1.setVisibility(View.VISIBLE);
                        }
                        mTypeView.setValueText(text);
                    }
                });
            }
        });

    }

    private void getData1() {
        try {
            mRequest1.page = mPage1++;
            mRequest1.pageSize = 14;
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout1.endRefreshing();
                    mRefreshLayout1.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            DynamicListResponseModel model = (DynamicListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.newslist != null) {
                                    mAdapter1.addAll(model.data.newslist);
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
                        DynamicListResponseModel model = action.getDynamicList(mRequest1);
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

    class MyAdapter1 extends ArrayAdapter<DynamicListResponseModel.DataBean.NewslistBean> {
        private int resourceId;

        public MyAdapter1(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_shop_dynamic_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + getItem(position).thumbpicture, holder.mPictureView);
            holder.mTimeView.setText(getItem(position).time);
            holder.mTitleView.setText(getItem(position).title);
            holder.mCk.setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("type", mFlag);
                    intent.putExtra("obj", getItem(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.shop_dynamic_add_list_item_ck)
            CheckBox mCk;
            @BindView(R.id.shop_dynamic_item_thumbpicture)
            ImageView mPictureView;
            @BindView(R.id.shop_dynamic_item_title)
            TextView mTitleView;
            @BindView(R.id.shop_dynamic_item_time)
            TextView mTimeView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class MyAdapter extends ArrayAdapter<CommodityManageListResponseModel.DataBean.CommoditylistBean> {
        private int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(resourceId, null);
                holder = new MyAdapter.ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (MyAdapter.ViewHolder) view.getTag();
            }
            CommodityManageListResponseModel.DataBean.CommoditylistBean obj = getItem(i);
            holder.mNameView.setText(obj.commodityname);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mThumbView);
            holder.mPriceView.setText(obj.price);
            holder.mSalenumberView.setText("已售出：" + obj.salenumber);
            holder.mEvaluatenumberView.setText("已评价：" + obj.evaluatenumber);
            if ("1".equals(obj.recommended)) {
                holder.mRecommendedView.setText("推荐商品：是");
            } else {
                holder.mRecommendedView.setText("推荐商品：不是");
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("type", mFlag);
                    intent.putExtra("obj", getItem(i));
                    setResult(RESULT_OK, intent);
                    finish();
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
