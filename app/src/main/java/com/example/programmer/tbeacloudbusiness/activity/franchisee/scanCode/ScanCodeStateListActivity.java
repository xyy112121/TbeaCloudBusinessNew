package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeHistoryResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeStateListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.ExpandPopTabView;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.KeyValueBean;
import com.example.programmer.tbeacloudbusiness.component.dropdownMenu.PopOneListView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * Created by programmer on 2017/5/28.
 */

public class ScanCodeStateListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private ExpandPopTabView expandTabView;
    private List<KeyValueBean> mScanDateLists;//扫码时间
    private BGARefreshLayout mRefreshLayout;
    private ListView mListView;
    private MyAdapter mAdapter;
    private int mPage = 1;
    private int mPagesiz = 10;
    private PopOneListView mDateView;
    private String qrcodegenerateid , orderitem, order;
    private final  int RESULT_DATA_SELECT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_history_state_list);
        qrcodegenerateid = getIntent().getStringExtra("id");
        initTopbar("已激活");
        initView();
    }

    private void initView(){
        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext);
        mListView.setAdapter(mAdapter);
        mRefreshLayout = (BGARefreshLayout)findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
        initDate();

        expandTabView = (ExpandPopTabView) findViewById(R.id.expandtab_view);
        addDateItem(expandTabView, mScanDateLists, "默认排序", "扫描时间");
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
                            ScanCodeStateListResponseModel model = (ScanCodeStateListResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                mAdapter.addAll(model.data.rebateqrcodeactivitylist);
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
                        ScanCodeStateListResponseModel model = action.getScanCodeStateList(qrcodegenerateid ,orderitem,order,mPage++,mPagesiz);
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

    public void addDateItem(final ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {
        mDateView = new PopOneListView(this);
        mDateView.setDefaultSelectByValue(defaultSelect);
        mDateView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                expandTabView.setViewColor(ContextCompat.getColor(mContext, R.color.blue));
                if("custom".equals(key)){
                    Intent intent = new Intent(mContext,DateSelectActivity.class);
                    startActivityForResult(intent,RESULT_DATA_SELECT);
                }else {
                    orderitem = "time";
                    order = key;
                    mRefreshLayout.beginRefreshing();
                }

            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, mDateView);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.removeAll();
        mPage = 1;
        getListData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getListData();
        return false;
    }

    private class MyAdapter extends BaseAdapter {
        /**
         * android 上下文环境
         */
        private Context context;

        public List<ScanCodeStateListResponseModel.RebateqrCodeActivity> mList = new ArrayList<>();
//
//        public List<CheckBox> ckList = new ArrayList<>();

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
            View view =  layoutInflater.inflate(
                    R.layout.activity_scan_code_history_state_list_item, null);
            final ScanCodeStateListResponseModel.RebateqrCodeActivity obj = mList.get(position);
            TextView codeView =(TextView)view.findViewById(R.id.scanCode_history_state_item_code);
            TextView userView =(TextView)view.findViewById(R.id.scanCode_history_state_item_user);
            TextView timeView =(TextView)view.findViewById(R.id.scanCode_history_state_item_time);
           codeView.setText(obj.rebatecode);
            userView.setText(obj.actuvityuser);
            String time = obj.activityday+"\n"+obj.activitytime;
            timeView.setText(time);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,ScanCodeViewActivity.class);
                    intent.putExtra("id",obj.qrcodeactivityid);
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

        public void addAll(List<ScanCodeStateListResponseModel.RebateqrCodeActivity> list){
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(expandTabView != null){
            expandTabView.onExpandPopView();
        }
    }

    private void initDate() {
        try {
            mScanDateLists = new ArrayList<>();
            mScanDateLists.add(new KeyValueBean("","默认排序"));
            mScanDateLists.add(new KeyValueBean("asc","正序"));
            mScanDateLists.add(new KeyValueBean("desc","倒序"));
            mScanDateLists.add(new KeyValueBean("custom","自定义"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
