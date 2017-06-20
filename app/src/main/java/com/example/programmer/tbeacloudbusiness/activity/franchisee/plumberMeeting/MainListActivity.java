package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 水电工会议
 */

public class MainListActivity extends TopActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mNumberLists;//会议编码
    private List<KeyValueBean> mRegionLists;//区域
    private List<KeyValueBean> mStateLists;//状态
    private List<KeyValueBean> mDateLists;//时间


    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private View mHeadView;
    private MyAdapter mAdapter;
    private  int mPage = 1;
    private int mPagesiz =10 ;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_main_list);
        mContext = this;
        initTopbar("水电工会议");
        intiView();
    }

    public  void intiView(){
        mListView = (ListView)findViewById(R.id.listview);
        mHeadView = getLayoutInflater().inflate(R.layout.activity_plumber_meeting_main_list_top,null);

        View mHeadView1 = getLayoutInflater().inflate(R.layout.activity_plumber_meeting_main_list_top1,null);

        mListView.addHeaderView(mHeadView);
        mListView.addHeaderView(mHeadView1);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout)findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addItem(expandTabView, mNumberLists, "默认排序", "会议编码");
        addItem(expandTabView, mRegionLists, "全部区域", "区域");
        addItem(expandTabView, mStateLists, "默认排序", "状态");
        addItem(expandTabView, mDateLists, "默认排序", "时间");

        findViewById(R.id.plumber_meeting_all_list_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,ListAllActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.plumber_meeting_sign_in_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //水电工签到列表
                Intent intent = new Intent();
                intent.setClass(mContext,SignInListActivity.class);
                intent.putExtra("mFlag","plumberSignIn");
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
                expandTabView.setViewColor(ContextCompat.getColor(mContext,R.color.blue));

                Log.e("tag", "key :" + key + " ,value :" + value);
            }
        });

        int displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();//屏幕的宽
        if ("会议编码".equals(defaultShowText)){
            double wid = displayWidth/3;
            int width = (int)wid;
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView,width, Gravity.LEFT);
        }else if ("时间".equals(defaultShowText)){

            expandTabView.addItemToExpandTab(defaultShowText, popOneListView, Gravity.RIGHT);
        }
        else {
            expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
        }
    }

    private void initDate() {
        try {
            mDateLists = new ArrayList<>();
            mDateLists.add(new KeyValueBean("","默认排序"));
            mDateLists.add(new KeyValueBean("PositiveSequence","正序"));
            mDateLists.add(new KeyValueBean("InvertedOrder","倒序"));
            mDateLists.add(new KeyValueBean("Custom","自定义"));

            mRegionLists = new ArrayList<>();
            mRegionLists.add(new KeyValueBean("","全部区域"));
            mRegionLists.add(new KeyValueBean("regionSelect","区域选择"));


            mNumberLists = new ArrayList<>();
            mNumberLists.add(new KeyValueBean("", "默认排序"));
            mNumberLists.add(new KeyValueBean("user1", "编码1"));
            mNumberLists.add(new KeyValueBean("user2", "编码2"));

            mStateLists = new ArrayList<>();
            mStateLists.add(new KeyValueBean("","默认排序"));
            mStateLists.add(new KeyValueBean("yes","已激活"));
            mStateLists.add(new KeyValueBean("no","未激活"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据
     */
    public void getListDate(){
//        final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                mRefreshLayout.endLoadingMore();
//                mRefreshLayout.endRefreshing();
//                switch (msg.what){
//                    case ThreadState.SUCCESS:
//                        RspInfo re = (RspInfo)msg.obj;
//                        if(re.isSuccess()){
//                            List<Collect> list = (List<Collect>)re.getDateObj("mysavelist");
//                            if(list != null){
//                                mAdapter.addAll(list);
//                            }else {
//                                mListView.setSelection(mAdapter.getCount());
//                                if(mPage >1){//防止分页的时候没有加载数据，但是页数已经增加，导致下一次查询不正确
//                                    mPage--;
//                                }
//                            }
//
//                        }else {
//                            UtilAssistants.showToast(re.getMsg());
//                        }
//
//                        break;
//                    case ThreadState.ERROR:
//                        UtilAssistants.showToast("操作失败！");
//                        break;
//                }
//            }
//        };
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    UserAction userAction = new UserAction();
//                    RspInfo re = userAction.getCollectList(mPage++,mPagesiz);
//                    handler.obtainMessage(ThreadState.SUCCESS,re).sendToTarget();
//                } catch (Exception e) {
//                    handler.sendEmptyMessage(ThreadState.ERROR);
//                }
//            }
//        }).start();
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

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<Object> mList = new ArrayList<>();

        /**
         * 构造函数
         *
         * @param context
         *            android上下文环境
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
                    R.layout.activity_plumber_meeting_main_list_item, null);
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

