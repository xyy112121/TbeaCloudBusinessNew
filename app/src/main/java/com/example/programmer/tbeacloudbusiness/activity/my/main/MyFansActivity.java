package com.example.programmer.tbeacloudbusiness.activity.my.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by programmer on 2017/6/25.
 */

public class MyFansActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    private View mSelectAllLayout;
    private TextView rightView;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private BGARefreshLayout mRefreshLayout;
    public boolean isSelect = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans);
        initTopbar("我的关注", "编辑", this);
        initUI();
    }

    /**
     * 实例化组件
     */
    private void initUI() {
        mSelectAllLayout = findViewById(R.id.fans_select_all_layout);
        rightView = (TextView) findViewById(R.id.top_right_text);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉
        mAdapter.removeAll();
        mPage = 1;
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        return true;
    }

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

        private List<Object> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return 10;
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
            View view = layoutInflater.inflate(
                    R.layout.fragment_attention_store_list_item, null);
            CheckBox ck = (CheckBox) view.findViewById(R.id.attention_item_ck);
            if (isSelect) {
                ck.setVisibility(View.VISIBLE);
            } else {
                ck.setVisibility(View.GONE);
            }
            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                notifyDataSetChanged();
            }
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if ("编辑".equals(rightView.getText() + "")) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
            layoutParams.bottomMargin = DensityUtil.dip2px(mContext, 44);
            mListView.setLayoutParams(layoutParams);
            rightView.setText("完成");
            mSelectAllLayout.setVisibility(View.VISIBLE);
            isSelect = true;
            mAdapter.notifyDataSetChanged();
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
            layoutParams.bottomMargin = 0;
            mListView.setLayoutParams(layoutParams);
            rightView.setText("编辑");
            mSelectAllLayout.setVisibility(View.GONE);
            isSelect = false;
            mAdapter.notifyDataSetChanged();
        }
    }
}
