package com.example.programmer.tbeacloudbusiness.activity.tbea;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.component.picker.CustomOptionPicker;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 产品介绍
 */

public class ProductPresentationListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private  CustomPopWindow mCustomPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_presentation);
        initTopbar("产品介绍");
        mContext = this;
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        listener();
    }


    private void listener(){
        findViewById(R.id.product_presentation_standard_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] types = new String[]{"全部","1米","1.5米","15","20米"};
                CustomOptionPicker optionPicker = new CustomOptionPicker((Activity) mContext,"规格选择",types);
                optionPicker.setTextSize(14);
                optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        ToastUtil.showMessage(option);
                    }
                });
                optionPicker.show();
                optionPicker.setGravity(Gravity.CENTER);
            }
        });

        findViewById(R.id.product_presentation_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(mContext).inflate(R.layout.acitivity_pop_menu,null);
                //处理popWindow 显示内容
//                handleLogic(contentView);
                //创建并显示popWindow
                 mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(mContext)
                        .setView(contentView)
                         .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                         .setBgDarkAlpha(0.5f) // 控制亮度
                        .create()
                        .showAsDropDown(v,-270,20);
            }
        });
    }

//    private void handleLogic(View contentView){
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mCustomPopWindow!=null){
//                    mCustomPopWindow.dissmiss();
//                }
//                String showContent = "";
//                switch (v.getId()){
//                    case R.id.menu1:
//                        showContent = "点击 Item菜单1";
//                        break;
//                    case R.id.menu2:
//                        showContent = "点击 Item菜单2";
//                        break;
//                }
//                ToastUtil.showMessage(showContent);
//            }
//        };
//        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
//        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
//    }



    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<Object> mList = new ArrayList<>();
//
//        public List<CheckBox> ckList = new ArrayList<>();

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
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(
                    R.layout.activity_product_presentation_list_item, null);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ProductPresentationInfoActivity.class);
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

        public void addAll(List<Object> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }

}
