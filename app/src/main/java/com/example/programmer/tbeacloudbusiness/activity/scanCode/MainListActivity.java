package com.example.programmer.tbeacloudbusiness.activity.scanCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 扫码返利
 */

public class MainListActivity extends TopActivity implements BGARefreshLayout.BGARefreshLayoutDelegate,View.OnClickListener{
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private View mHeadView;
    private View mHeadView1;
    private MyAdapter mAdapter;
    private  int mPage = 1;
    private int mPagesiz =10 ;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_main_list);
        mContext = this;
        initTopbar("扫码返利","生成",this);
        intiView();
    }

    public  void intiView(){
        mListView = (ListView)findViewById(R.id.listview);
        mHeadView = getLayoutInflater().inflate(R.layout.activity_scan_code_main_list_top,null);
        mHeadView1 = getLayoutInflater().inflate(R.layout.activity_scan_code_main_list_top1,null);
        mListView.addHeaderView(mHeadView);
        mListView.addHeaderView(mHeadView1);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout)findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
//        mRefreshLayout.beginRefreshing();
        mHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,WithdrawDepositDateActivity.class);
                startActivity(intent);
            }
        });

        mHeadView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,ScanCodeRebateListActivity.class);
                startActivity(intent);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mContext,WithdrawDepositDateHistoryActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onClick(View view) {
        //生成
        Intent intent = new Intent();
        intent.setClass(mContext,CreateActivity.class);
        startActivity(intent);
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
                    R.layout.activity_scan_code_main_list_item, null);
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

