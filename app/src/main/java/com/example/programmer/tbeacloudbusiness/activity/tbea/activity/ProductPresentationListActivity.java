package com.example.programmer.tbeacloudbusiness.activity.tbea.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MainActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MessageTypeListActivity;
import com.example.programmer.tbeacloudbusiness.activity.tbea.action.TbeaAction;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CommodityCategoryResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CommodityModelResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CommoditySpecificationResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ProductPresentationListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.TbMainResponseModel;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomOptionObjPicker;
import com.example.programmer.tbeacloudbusiness.model.Condition;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.zhouwei.library.CustomPopWindow;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 产品介绍
 */

public class ProductPresentationListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private List<Condition> mCategoryList, mSpecificationList, mModelList;//商品列表，规格
    private int mPage = 1;
    private int mPagesiz = 10;
    private String name, commodityspecificationid, commoditymodelid, orderitem, order;
    private CustomPopWindow mCustomPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_presentation);
        initTopbar("产品介绍");
        initView();
        getTypeData();//获取下拉列表的数据
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();

        findViewById(R.id.product_presentation_specification_layout).setOnClickListener(this);
        findViewById(R.id.product_presentation_time_layout).setOnClickListener(this);
        findViewById(R.id.product_presentation_model_layout).setOnClickListener(this);
        findViewById(R.id.product_presentation_more).setOnClickListener(this);

        final EditText searchEditText = (EditText) findViewById(R.id.product_presentation_list_search_text);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    name = searchEditText.getText() + "";
                    mRefreshLayout.beginRefreshing();
                }
                return false;
            }
        });
    }

    /**
     * 从服务器获取下拉列表的数据
     */
    public void getTypeData() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        TbeaAction action = new TbeaAction();
                        CommodityCategoryResponseModel model = action.getCommodityCategory();
                        if (model.isSuccess()) {
                            mCategoryList = model.data.commoditycategorylist;
                            if (mCategoryList != null && mCategoryList.size() > 0) {
                                CommoditySpecificationResponseModel model1 = action.getCommoditySpecification(mCategoryList.get(0).getId());
                                mSpecificationList = model1.data.commodityspecificationlist;
                                CommodityModelResponseModel model2 = action.getCommodityModelList(mCategoryList.get(0).getId());
                                mModelList = model2.data.commoditymodellist;
                            }
                        }
                    } catch (Exception e) {
                        Log.d(mContext.getPackageName(), e.getMessage());
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                            ProductPresentationListResponseModel model = (ProductPresentationListResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data != null && model.data.commoditylist != null)
                                    mAdapter.addAll(model.data.commoditylist);
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
                        TbeaAction action = new TbeaAction();
                        ProductPresentationListResponseModel model = action.getCommodityList(name, commodityspecificationid, commoditymodelid, orderitem, order, mPage++, mPagesiz);
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

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mPage = 1;
        mAdapter.removeAll();
        getListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getListData();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.product_presentation_time_layout:
                TextView timeTv = (TextView) findViewById(R.id.product_presentation_time_tv);
                Drawable drawable = null;
                /// 这一步必须要做,否则不会显示.
                if ("asc".equals(timeTv.getTag())) {//正
                    drawable = getResources().getDrawable(R.drawable.icon_arraw_bluegray);
                    timeTv.setTag("desc");
                } else if ("desc".equals(timeTv.getTag())) {//倒序\
                    drawable = getResources().getDrawable(R.drawable.icon_arraw_grayblue);
                    timeTv.setTag("asc");
                }
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                timeTv.setCompoundDrawables(null, null, drawable, null);
                orderitem = "time";
                order = timeTv.getTag() + "";
                mRefreshLayout.beginRefreshing();

                break;
            case R.id.product_presentation_specification_layout:
                if (mSpecificationList != null && mSpecificationList.size() > 0)
                    showOptionPicker(R.id.product_presentation_specification_tv, "规格选择", mSpecificationList);
                break;
            case R.id.product_presentation_model_layout:
                if (mModelList != null && mModelList.size() > 0)
                    showOptionPicker(R.id.product_presentation_model_tv, "型号选择", mModelList);
                break;

            case R.id.product_presentation_more:
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.acitivity_pop_menu, null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                        .setView(contentView)
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.5f) // 控制亮度
                        .create()
                        .showAsDropDown(view, DensityUtil.px2dip(mContext, -270), DensityUtil.px2dip(mContext, 20));
                break;
        }
    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.menu2://首页
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        MyApplication.instance.exit();
                        break;
                    case R.id.menu1://消息
                        intent = new Intent(mContext, MessageTypeListActivity.class);
                        startActivity(intent);
                        break;
                }

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
    }


    public void showOptionPicker(final int id, String text, List<Condition> list) {
        CustomOptionObjPicker optionPicker = new CustomOptionObjPicker(mContext, text, list);
        optionPicker.setTextSize(14);
        if (list.size() > 1) {
            optionPicker.setSelectedIndex(1);
        }
        optionPicker.setOnOptionPickListener(new CustomOptionObjPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(Condition option) {
                if (id == R.id.product_presentation_specification_tv) {
                    commodityspecificationid = option.getId();
                } else if (id == R.id.product_presentation_model_tv) {
                    commoditymodelid = option.getId();
                }
                //选择规格
                ((TextView) findViewById(id)).setText(option.getName());
                mRefreshLayout.beginRefreshing();
            }
        });
        optionPicker.show();
        optionPicker.setGravity(Gravity.CENTER);
    }

    private class MyAdapter extends BaseAdapter {

        public List<TbMainResponseModel.Product> mList = new ArrayList<>();

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
            if (view == null) {
                view = getLayoutInflater().inflate(
                        R.layout.activity_product_presentation_list_item, null);
            }
            final TbMainResponseModel.Product obj = mList.get(position);

            ImageView imageView = (ImageView) view.findViewById(R.id.product_presentation_list_item_thumbpicture);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, imageView);
            if (obj.specification != null) {
                ((TextView) view.findViewById(R.id.product_presentation_list_item_specification)).setText("规格型号：" + obj.specification);
            }

            ((TextView) view.findViewById(R.id.product_presentation_list_item_name)).setText(obj.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ProductPresentationInfoActivity.class);
                    intent.putExtra("id", obj.id);
                    startActivity(intent);
                }
            });

            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<TbMainResponseModel.Product> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
