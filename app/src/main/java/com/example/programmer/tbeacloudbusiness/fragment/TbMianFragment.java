package com.example.programmer.tbeacloudbusiness.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.tbea.activity.CompanyIntroActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.activity.NewsIntroActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.activity.ProductPresentationContactInfoActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.activity.ProductPresentationInfoActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.activity.ProductPresentationListActivity;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.TbMainResponseModel;
import com.example.programmer.tbeacloudbusiness.component.HeaderGridView;
import com.example.programmer.tbeacloudbusiness.utils.BannerImageLoader;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
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

public class TbMianFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {

    private View mView;
    private HeaderGridView mGridView;
    private MyAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private View mHeadView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tb_mian, null);
        initView(inflater);
        return mView;
    }

    /**
     * 从服务器获取数据
     */
    private void getData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            TbMainResponseModel model = (TbMainResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                initBanner(model.data.advpicturelist);
                                initMessage(model.data.messagelist);
                                if(model.data.productlist != null){
                                    mAdapter.addAll(model.data.productlist);
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
                        UserAction userAction = new UserAction();
                        TbMainResponseModel model = userAction.getTbMainData();
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

    private void initView(LayoutInflater inflater) {
        try {
            mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
            mRefreshLayout.setDelegate(this);
            mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), false));
            mHeadView = inflater.inflate(R.layout.fragment_tb_mian_top, null);
            mGridView = (HeaderGridView) mView.findViewById(R.id.gridView);
            mGridView.addHeaderView(mHeadView);
            //设置布局管理器为2列，纵向
            mAdapter = new MyAdapter(getActivity());
            mGridView.setAdapter(mAdapter);

            mRefreshLayout.beginRefreshing();

            mHeadView.findViewById(R.id.mian_top_company_intro_layout).setOnClickListener(this);
            mHeadView.findViewById(R.id.mian_top_news_layout).setOnClickListener(this);
            mHeadView.findViewById(R.id.mian_product_presentation_layout).setOnClickListener(this);
            mHeadView.findViewById(R.id.mian_top_company_intro_layout).setOnClickListener(this);
            mHeadView.findViewById(R.id.mian_product_presentation_phone_layout).setOnClickListener(this);
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

    private void initBanner(List<TbMainResponseModel.Advpicture> modelList) {
        if (modelList != null && modelList.size() > 0) {
            List<String> images = new ArrayList<>();
            for (TbMainResponseModel.Advpicture model : modelList) {
                images.add(MyApplication.instance.getImgPath() + model.picture);
            }
            Banner banner = (Banner) mHeadView.findViewById(R.id.banner);
            banner.setImageLoader(new BannerImageLoader());
            //设置图片集合
            banner.setImages(images);
            //设置图片加载器
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            banner.start();
        }

    }

    private void initMessage(List<TbMainResponseModel.Message> modelList) {
        if (modelList != null)
            ((TextView) mHeadView.findViewById(R.id.tb_mian_top_message)).setText(modelList.get(0).title);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mian_top_company_intro_layout:
                Intent intent = new Intent(getActivity(), CompanyIntroActivity.class);
                startActivity(intent);
                break;
            case R.id.mian_top_news_layout:
                intent = new Intent(getActivity(), NewsIntroActivity.class);
                startActivity(intent);
                break;
            case R.id.mian_product_presentation_layout:
                intent = new Intent(getActivity(), ProductPresentationListActivity.class);
                startActivity(intent);
                break;
            case R.id.mian_product_presentation_phone_layout:
                intent = new Intent(getActivity(), ProductPresentationContactInfoActivity.class);
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

        private List<TbMainResponseModel.Product> mList = new ArrayList<>();


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
            return mList.size();
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
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.fragment_tb_main_item, null);
            }
            final  TbMainResponseModel.Product obj = mList.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.tb_mian_image);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, imageView);
            ((TextView) convertView.findViewById(R.id.tb_mian_title)).setText(obj.name);
            ((TextView) convertView.findViewById(R.id.tb_mian_type)).setText(obj.specification);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ProductPresentationInfoActivity.class);
                    intent.putExtra("id", obj.id);
                    startActivity(intent);
                }
            });
            return convertView;
        }


        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        public void addAll(List<TbMainResponseModel.Product> list){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }
}

