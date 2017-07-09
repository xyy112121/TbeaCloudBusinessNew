package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 扫码返利
 */

public class ScanCodeMainListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private View mHeadView;
    private View mHeadView1;
    private MyAdapter mAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_main_list);
        mContext = this;
        initTopbar("扫码返利", "生成", this);
        intiView();
    }

    public void intiView() {
        mListView = (ListView) findViewById(R.id.listview);
        mHeadView = getLayoutInflater().inflate(R.layout.activity_scan_code_main_list_top, null);
        mHeadView1 = getLayoutInflater().inflate(R.layout.activity_scan_code_main_list_top1, null);
        mListView.addHeaderView(mHeadView);
        mListView.addHeaderView(mHeadView1);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, false));
        mRefreshLayout.beginRefreshing();
//        mHeadView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(mContext,WithdrawDepositDateActivity.class);
//                startActivity(intent);
//            }
//        });

//        mHeadView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(mContext,ScanCodeRebateListActivity.class);
//                startActivity(intent);
//            }
//        });

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.setClass(mContext,WithdrawDepositDateHistoryActivity.class);
//                startActivity(intent);
//            }
//        });
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
                            ScanCodeMainResponseModel model = (ScanCodeMainResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                if (model.data.takemoneyrankinglist != null && model.data.takemoneyrankinglist.size() > 0) {
                                    mAdapter.addAll(model.data.takemoneyrankinglist);
                                }

                                ScanCodeMainResponseModel.TakeMoneyTotleInfo info = model.data.takemoneytotleinfo;
                                if (info != null) {
                                    mHeadView.findViewById(R.id.top_text4_label).setVisibility(View.VISIBLE);
                                    mHeadView.findViewById(R.id.top_text5_label).setVisibility(View.VISIBLE);
                                    ((TextView) mHeadView.findViewById(R.id.top_text4)).setText(info.havepayed);
                                    ((TextView) mHeadView.findViewById(R.id.top_text5)).setText(info.needpay);
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
                        ScanCodeAction action = new ScanCodeAction();
                        ScanCodeMainResponseModel model = action.getMainData();
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
        //下拉刷新
        mAdapter.removeAll();
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        getData();
        return true;
    }

    @Override
    public void onClick(View view) {
        //生成
        Intent intent = new Intent();
        intent.setClass(mContext, ScanCodeCreateActivity.class);
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<ScanCodeMainResponseModel.TakeMoneyRanking> mList = new ArrayList<>();

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
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            FrameLayout view = (FrameLayout) layoutInflater.inflate(
                    R.layout.activity_scan_code_main_list_item, null);
            ScanCodeMainResponseModel.TakeMoneyRanking obj = mList.get(position);
            CircleImageView headView = (CircleImageView) view.findViewById(R.id.scan_code_main_item_thumbpicture);
            ImageView personJobTitleView = (ImageView) view.findViewById(R.id.scan_code_main_item_personjobtitle);
            TextView nameView = (TextView) view.findViewById(R.id.scan_code_main_item_name);
            TextView moneyView = (TextView) view.findViewById(R.id.scan_code_main_item_totlemoney);
            String path = MyApplication.instance.getImgPath();
            ImageLoader.getInstance().displayImage(path + obj.thumbpicture, headView);
            ImageLoader.getInstance().displayImage(path + obj.personjobtitle, personJobTitleView);
            nameView.setText(obj.personname);
            moneyView.setText(obj.totlemoney);

            return view;
        }


        public void remove(int index) {
            if (index > 0) {
                mList.remove(index);
                notifyDataSetChanged();
            }
        }

        public void addAll(List<ScanCodeMainResponseModel.TakeMoneyRanking> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }
}
