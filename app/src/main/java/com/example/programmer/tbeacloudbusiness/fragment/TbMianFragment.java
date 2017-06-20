package com.example.programmer.tbeacloudbusiness.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.tbea.CompanyIntroActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.ProductPresentationActivity;
import com.example.programmer.tbeacloudbusiness.component.HeaderGridView;
import com.example.programmer.tbeacloudbusiness.utils.BannerImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 特变电工
 */

public class TbMianFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate,View.OnClickListener {

    private View mView;
    private HeaderGridView mGridView;
    private MyAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private View mHeadView;
    private int mPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tb_mian, null);
        initView(inflater);
        return mView;
    }

    private void initView(LayoutInflater inflater) {
        try {
            mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
            mRefreshLayout.setDelegate(this);
            mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
            mHeadView = inflater.inflate(R.layout.fragment_tb_mian_top, null);
            mGridView = (HeaderGridView) mView.findViewById(R.id.gridView);
            mGridView.addHeaderView(mHeadView);
//            mGridView.addView(headView);
            //设置布局管理器为2列，纵向
            mAdapter = new MyAdapter(getActivity());
            mGridView.setAdapter(mAdapter);
            initBanner();
            mHeadView.findViewById(R.id.mian_top_company_intro_layout).setOnClickListener(this);
            mHeadView.findViewById(R.id.mian_top_news_layout).setOnClickListener(this);
            mHeadView.findViewById(R.id.mian_product_presentation_layout).setOnClickListener(this);
            mHeadView.findViewById(R.id.mian_top_company_intro_layout).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void initBanner() {
        List<String> images = new ArrayList<>();
        images.add("http://www.u-shang.net//Public/Uploadstest/l_5594e301cc807.png");
        images.add("http://www.u-shang.net//Public/Uploadstest/l_5652a878b1950.png");
        images.add("http://www.u-shang.net//Public/Uploadstest/l_5652a8f1c734b.png");
        Banner banner = (Banner) mHeadView.findViewById(R.id.banner);
        banner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置图片加载器
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.start();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mian_top_company_intro_layout:
                Intent intent = new Intent(getActivity(),CompanyIntroActivity.class);
                intent.putExtra("flag","CompanyIntro");
                startActivity(intent);
                break;
            case R.id.mian_top_news_layout:
                intent = new Intent(getActivity(),CompanyIntroActivity.class);
                intent.putExtra("flag","new");
                startActivity(intent);
                break;
             case  R.id.mian_product_presentation_layout:
                 intent = new Intent(getActivity(),ProductPresentationActivity.class);
                 startActivity(intent);
            break;

        }

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
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.fragment_tb_main_item, null);
            return view;
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

