package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.StoreManageMainResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.MyGridView;
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
 * 店铺管理首页
 */

public class StoreManageMainActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView mListView;
    private MyAdapter mAdapter;
    private GridAdapter mGridAdapter;
    private MyGridView gridView;
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manage_main);
        ButterKnife.bind(this);
        initTopbar("店铺管理");
        initView();
    }

    private void initView() {
        mRefreshLayout = getViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, false));

        mListView = (ListView) findViewById(R.id.home_list);

        gridView = (MyGridView) findViewById(R.id.gridView);
        mGridAdapter = new GridAdapter();
        gridView.setAdapter(mGridAdapter);

        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);

        mRefreshLayout.beginRefreshing();
    }

    private void getData() {
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            StoreManageMainResponseModel model = (StoreManageMainResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                initStaticsItem(model.data.staticsitemlist);
                                initFunctionModel(model.data.functionmodulelist);
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
                        StoreManageMainResponseModel model = action.getMainData();
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

    //下面的横条cell功能
    private void initStaticsItem(List<StoreManageMainResponseModel.DataBean.StaticsitemlistBean> modelList) {
        if (modelList != null) {
            mAdapter.addAll(modelList);
        }
    }

    //顶上的功能
    private void initFunctionModel(List<StoreManageMainResponseModel.DataBean.FunctionmodulelistBean> modelList) {
        if (modelList != null) {
            mGridAdapter.addAll(modelList);
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.removeAll();
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

     class GridAdapter extends BaseAdapter {
        private List<StoreManageMainResponseModel.DataBean.FunctionmodulelistBean> mList = new ArrayList<>();


        public void addAll(List<StoreManageMainResponseModel.DataBean.FunctionmodulelistBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.fragment_home_top_item, null);
            ImageView imageView = (ImageView) view1.findViewById(R.id.home_top_item_image);
            TextView textView = (TextView) view1.findViewById(R.id.home_top_item_name);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + mList.get(i).moduleicon, imageView);
            textView.setText(mList.get(i).modulename);
            LinearLayout layout = (LinearLayout) view1.findViewById(R.id.home_top_tb_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StoreManageMainResponseModel.DataBean.FunctionmodulelistBean obj = mList.get(i);
                    if ("dianpushezhi".equals(obj.moduleid)) {//分销商
                        //店铺设置
                        Intent intent = new Intent(mContext, StoreSetActtivity.class);
                        startActivity(intent);
                    }
                    if ("dingdanguanli".equals(obj.moduleid)) {
                        //订单管理
                        Intent intent = new Intent(mContext, OrderManageDeliverGoodsActivity.class);
                        startActivity(intent);
                    } else if ("shangpinguanli".equals(obj.moduleid)) {
                        //商品管理
                        Intent intent = new Intent(mContext, StoreCommodityManageListActivity.class);
                        startActivity(intent);
                    } else if ("dianpudongtai".equals(obj.moduleid)) {
                        //店铺动态
                        Intent intent = new Intent(mContext, ShopDynamicListActivity.class);
                        startActivity(intent);
                    }
                }
            });
            return view1;
        }
    }

    /**
     * 附近商家
     */
     class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        private List<StoreManageMainResponseModel.DataBean.StaticsitemlistBean> mList = new ArrayList<>();

        public void addAll(List<StoreManageMainResponseModel.DataBean.StaticsitemlistBean> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

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
        public View getView(int position, View view, ViewGroup parent) {
            final StoreManageMainResponseModel.DataBean.StaticsitemlistBean obj = mList.get(position);
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            if ("style04".equals(obj.style)) {
                view = layoutInflater.inflate(R.layout.activity_store_manage_main_list_item, null);
                ViewHolder holder = new ViewHolder(view);
                holder.topText1.setText(obj.name);
                holder.topText2.setText(obj.subitemlist.get(0).name);
                holder.topText3.setText(obj.subitemlist.get(1).name);
                if ("0".equals(obj.subitemlist.get(0).ismoney)) {
                    holder.topText4Label.setVisibility(View.VISIBLE);
                }
                holder.topText4.setText(obj.subitemlist.get(0).value);
                if ("0".equals(obj.subitemlist.get(1).ismoney)) {
                    holder.topText5Label.setVisibility(View.VISIBLE);
                }
                holder.topText5.setText(obj.subitemlist.get(1).value);

                holder.topText6.setText(obj.subitemlist.get(2).name);
                holder.topText7.setText(obj.subitemlist.get(3).name);
                if ("0".equals(obj.subitemlist.get(2).ismoney)) {
                    holder.topText8Label.setVisibility(View.VISIBLE);
                }
                holder.topText8.setText(obj.subitemlist.get(3).value);
                if ("0".equals(obj.subitemlist.get(3).ismoney)) {
                    holder.topText9Label.setVisibility(View.VISIBLE);
                }
                holder.topText9.setText(obj.subitemlist.get(1).value);
                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.icon, holder.topImgae);

            } else {
                view = layoutInflater.inflate(R.layout.activity_scan_code_main_list_top, null);
                ImageView iconView = (ImageView) view.findViewById(R.id.top_imgae);
                ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.icon, iconView);
                TextView nameView = (TextView) view.findViewById(R.id.top_text1);
                TextView nameView2 = (TextView) view.findViewById(R.id.top_text2);
                TextView valueView2 = (TextView) view.findViewById(R.id.top_text3);
                TextView nameView3 = (TextView) view.findViewById(R.id.top_text4);
                TextView valueView3 = (TextView) view.findViewById(R.id.top_text5);
                TextView labelView1 = (TextView) view.findViewById(R.id.top_text4_label);
                TextView labelView2 = (TextView) view.findViewById(R.id.top_text5_label);
                nameView.setText(obj.name);
                nameView2.setText(obj.subitemlist.get(0).name);
                valueView2.setText(obj.subitemlist.get(1).name);
                nameView3.setText(obj.subitemlist.get(0).value);
                valueView3.setText(obj.subitemlist.get(1).value);

                if ("style01".equals(obj.style)) {
                    if ("1".equals(obj.subitemlist.get(0).ismoney)) {
                        labelView1.setVisibility(View.VISIBLE);
                    }
                    if ("1".equals(obj.subitemlist.get(1).ismoney)) {
                        labelView2.setVisibility(View.VISIBLE);
                    }
                } else if ("style02".equals(obj.style)) {
                    labelView1.setVisibility(View.VISIBLE);
                    String value = "";
                    String[] values = obj.subitemlist.get(1).value.split(",");
                    for (String item : values) {
                        value = value + item;
                    }
                    SpannableString spannableString = new SpannableString(value);
                    int index1 = values[0].length();
                    int index2 = values[1].length();
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00AAEF")), 0, index1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FA8B27")), index1, index1 + index2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    valueView3.setTextColor(Color.parseColor("#B1B1B1"));
                    valueView3.setText(spannableString);

                } else if ("style03".equals(obj.style)) {
//                    if (obj.subitemlist != null && obj.subitemlist.size() > 0) {
//                        String value1 = obj.subitemlist.get(0).value + "/" + obj.subitemlist.get(0).value;
//                        String value2 = obj.subitemlist.get(1).value + "/" + obj.subitemlist.get(1).value;
//                        SpannableString spannableString1 = new SpannableString(value1);
//                        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#00AAEF")), 0, obj.subitemlist.get(0).v.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        nameView3.setText(spannableString1);
//                        SpannableString spannableString2 = new SpannableString(value2);
//                        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#00AAEF")), 0, obj.subitemlist.get(1).value1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        valueView3.setText(spannableString2);
//                    }
                }
            }


            return view;
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.top_imgae)
            ImageView topImgae;
            @BindView(R.id.top_text1)
            TextView topText1;
            @BindView(R.id.top_text2)
            TextView topText2;
            @BindView(R.id.top_text3)
            TextView topText3;
            @BindView(R.id.top_text4_label)
            TextView topText4Label;
            @BindView(R.id.top_text4)
            TextView topText4;
            @BindView(R.id.top_text5_label)
            TextView topText5Label;
            @BindView(R.id.top_text5)
            TextView topText5;
            @BindView(R.id.top_text6)
            TextView topText6;
            @BindView(R.id.top_text7)
            TextView topText7;
            @BindView(R.id.top_text8_label)
            TextView topText8Label;
            @BindView(R.id.top_text8)
            TextView topText8;
            @BindView(R.id.top_text9_label)
            TextView topText9Label;
            @BindView(R.id.top_text9)
            TextView topText9;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
