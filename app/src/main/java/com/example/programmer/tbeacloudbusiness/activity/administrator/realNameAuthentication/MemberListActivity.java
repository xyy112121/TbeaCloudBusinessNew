package com.example.programmer.tbeacloudbusiness.activity.administrator.realNameAuthentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.PersonManageActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.RegionSelectActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 会员列表
 */

public class MemberListActivity extends TopActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private Context mContext;

    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mUserLists;//类型
    private List<KeyValueBean> mRegionLists;//地区

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        initTopbar("会员列表", this, R.drawable.icon_morepointwhite);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.expert_search_text)).setHint("会员查询");
        mContext = this;
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mUserLists, "全部", "用户");
        addItem(expandTabView, mRegionLists, "全部区域", "地区");

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, PersonManageActivity.class);
//                Intent intent = new Intent(mContext,ScanCodeDateListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        PopOneListView popOneListView = new PopOneListView(this);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {

                if ("regionSelect".equals(key)) {
                    Intent intent = new Intent(mContext, RegionSelectActivity.class);
                    startActivity(intent);
                }

                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });
        int displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();//屏幕的宽

        if ("用户".equals(defaultShowText)) {
            double wid = displayWidth / 2;
            int width = (int) wid;
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView, width, Gravity.LEFT);
        } else {
            double wid = displayWidth / 2.8;
            int width = (int) wid;
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView, width, Gravity.RIGHT);
        }


    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (expandTabView != null) {
            expandTabView.onExpandPopView();
        }
    }

    private void initDate() {
        try {
            mUserLists = new ArrayList<>();
            mUserLists.add(new KeyValueBean("", "全部"));
            mUserLists.add(new KeyValueBean("user1", "用户1"));
            mUserLists.add(new KeyValueBean("user2", "用户2"));

            mRegionLists = new ArrayList<>();
            mRegionLists.add(new KeyValueBean("", "全部区域"));
            mRegionLists.add(new KeyValueBean("regionSelect", "区域选择"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.acitivity_pop_menu,null);
        contentView.findViewById(R.id.menu3_view).setVisibility(View.VISIBLE);
        contentView.findViewById(R.id.menu3).setVisibility(View.VISIBLE);
        //处理popWindow 显示内容
//                handleLogic(contentView);
        //创建并显示popWindow
        new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()
                .showAsDropDown(view,-200,-30);
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<Object> mList = new ArrayList<>();

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
            FrameLayout view = (FrameLayout) layoutInflater.inflate(
                    R.layout.activity_member_list_item, null);
            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

//        public void addAll(List<Collect> list){
//            mList.addAll(list);
//            notifyDataSetChanged();
//        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
