package com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.CommdityListResponseModel;
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
 * 服务凭证对应的购买产品列表
 */

public class CommdityListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView mListView;
    private MyAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private int mPage = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_history_search_list);
            initTopbar("购买的产品");
            mListView = (ListView) findViewById(R.id.listview);
            mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
            mRefreshLayout.setDelegate(this);
            mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));

            mAdapter = new MyAdapter();
            mListView.setAdapter(mAdapter);

            mRefreshLayout.beginRefreshing();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        CommdityListResponseModel re = (CommdityListResponseModel) msg.obj;
                        if (re.isSuccess()) {
                            if (re.data != null) {
                                mAdapter.addAll(re.data.commoditylist);
                            } else {
                                mListView.setSelection(mAdapter.getCount());
                                if (mPage > 1) {//防止分页的时候没有加载数据，但是页数已经增加，导致下一次查询不正确
                                    mPage--;
                                }
                            }
                        } else {
                            ToastUtil.showMessage(re.getMsg());
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
                    SubscribeAction action = new SubscribeAction();
                    String code = getIntent().getStringExtra("code");
                    CommdityListResponseModel re = action.getCommdityList(code, mPage++, 10);
                    if (re == null) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    } else {
                        handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        mAdapter.removeAll();
        mPage = 1;
        getListDate();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        getListDate();
        return true;
    }

    class MyAdapter extends BaseAdapter {
        private List<CommdityListResponseModel.DataBean.CommoditylistBean> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            ViewHolder holder;
            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.activity_sr_commodity_list_item, null);
                holder = new ViewHolder(v);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            CommdityListResponseModel.DataBean.CommoditylistBean model = mList.get(i);
            holder.mNameView.setText(model.name);
            holder.mTypeView.setText("规格型号：" + model.modelandspecification);
            holder.mNumberView.setText("数量" + model.number);
            ImageLoader.getInstance().displayImage(model.thumbpicture, holder.mHeadView);
            return v;
        }

        public void addAll(List<CommdityListResponseModel.DataBean.CommoditylistBean> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.sr_commdity_item_head)
            ImageView mHeadView;
            @BindView(R.id.sr_commdity_item_name)
            TextView mNameView;
            @BindView(R.id.sr_commdity_item_type)
            TextView mTypeView;
            @BindView(R.id.sr_commdity_item_number)
            TextView mNumberView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}