package com.example.programmer.tbeacloudbusiness.activity.my.main.attention;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.my.main.MyAttentionActivity;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class StoreFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private View mView;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private BGARefreshLayout mRefreshLayout;
    public boolean isSelect = false;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_attention_list, container, false);
        initUI();//实例化控件
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mRefreshLayout.beginRefreshing();
    }

    public void refresh(){
        try {
            if (mAdapter != null){
                if (isSelect){
                    isSelect = false;
                    mAdapter.notifyDataSetChanged();
                    ((MyAttentionActivity)getActivity()).mSelectAllLayout.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
                    layoutParams.bottomMargin=0;
                    mListView.setLayoutParams(layoutParams);
                }else {
                    isSelect = true;
                    mAdapter.notifyDataSetChanged();
                    View ff = ((MyAttentionActivity)getActivity()).mSelectAllLayout;
                    ff.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mListView.getLayoutParams();
                    layoutParams.bottomMargin= DensityUtil.dip2px(getActivity(),44);
                    mListView.setLayoutParams(layoutParams);
                }
            }
        }catch (Exception e){
            Log.e("","");
        }

    }

    /**
     * 实例化组件
     */
    private void initUI() {
        mListView = (ListView) mView.findViewById(R.id.listview);
        mAdapter = new MyAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) mView.findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
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
            CheckBox ck  = (CheckBox) view.findViewById(R.id.attention_item_ck);
            if(isSelect){
                ck.setVisibility(View.VISIBLE);
            }else {
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
}

