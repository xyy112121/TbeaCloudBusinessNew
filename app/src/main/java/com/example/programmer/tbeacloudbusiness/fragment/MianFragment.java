package com.example.programmer.tbeacloudbusiness.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.scanCode.MainListActivity;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 首页
 */

public class MianFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView mListView;
    private MyAdapter mAdapter;
    private LayoutInflater mInflater;
    private BGARefreshLayout mRefreshLayout;
    private View mView;
    private View headView;
    private int mPage = 1;
    private int mPagesiz = 40;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (View) inflater.inflate(R.layout.fragment_home, null);
        mInflater = inflater;
        initView(mView);
//        mRefreshLayout.beginRefreshing();
        return mView;
    }

    private void initView(View view) {
        mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));

        mListView = (ListView) view.findViewById(R.id.home_list);
        headView = mInflater.inflate(R.layout.fragment_home_top, null);
        mListView.addHeaderView(headView);

        mAdapter = new MyAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        headView.findViewById(R.id.home_scan_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫码返利
                startActivity(new Intent(getActivity(), MainListActivity.class));
            }
        });

        headView.findViewById(R.id.home_plumber_manage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //水电工管理
                startActivity(new Intent(getActivity(), com.example.programmer.tbeacloudbusiness.activity.plumberManage.MainListActivity.class));
            }
        });

        headView.findViewById(R.id.home_plumber_meeting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //水电工管理
                startActivity(new Intent(getActivity(), com.example.programmer.tbeacloudbusiness.activity.plumberMeeting.MainListActivity.class));
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mView.findViewById(id);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        mPage = 1;
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        return true;
    }


    /**
     * 附近商家
     */
    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;


        /**
         * 构造函数
         *
         * @param context android上下文环境
         */
        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 0;
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
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            return convertView;
        }


        public void remove(int index) {
            if (index > 0) {

                notifyDataSetChanged();
            }
        }

        public void removeAll() {

            notifyDataSetChanged();
        }
    }

}
