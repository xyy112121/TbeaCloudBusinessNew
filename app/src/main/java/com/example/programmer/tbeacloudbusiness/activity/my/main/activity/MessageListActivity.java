package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.MessageListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 我的消息列表
 */

public class MessageListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView mListView;
    private MyAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private int mPage = 1;
    private int mPagesize = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_search_list);
        initTopbar("我的消息");
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(MessageListActivity.this);
        mListView.setAdapter(mAdapter);

        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(MessageListActivity.this, true));
        mRefreshLayout.beginRefreshing();
    }

    /**
     * 获取数据
     */
    public void getListDate() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        MessageListResponseModel re = (MessageListResponseModel) msg.obj;
                        if (re.isSuccess()) {
//                            List<MessageCategory> list = (List<MessageCategory>)re.getDateObj("messagecategorylist");
//                            if(list != null){
//                                mAdapter.addAll(list);
//                            }else {
//                                mListView.setSelection(mAdapter.getCount());
//                                if(mPage >1){//防止分页的时候没有加载数据，但是页数已经增加，导致下一次查询不正确
//                                    mPage--;
//                                }
//                            }

                        } else {
                            ToastUtil.showMessage(re.getMsg());
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
                    MessageListResponseModel re = userAction.getMessageList(mPage++, mPagesize);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
//        mAdapter.removeAll();
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
        private Context mContext;
//        private List<MessageCategory> mList = new ArrayList<>();

        public MyAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
//            return mList.size();
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            View view = (View) inflater.inflate(R.layout.activity_message_list_item, null);
//            final MessageCategory obj = mList.get(i);
//            final ImageView imageView = (ImageView) view.findViewById(R.id.message_item_picture);
//            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.getPicture(), imageView);
//            ((TextView) view.findViewById(R.id.message_item_title)).setText(obj.getName());
//            ((TextView) view.findViewById(R.id.message_item_time)).setText(obj.getLasttime());
//            ((TextView) view.findViewById(R.id.message_item_content)).setText(obj.getLastmessagetitle());
//
//            if (!"0".equals(obj.getNewcount()) && !"".equals(obj.getNewcount())) {
//                BadgeView badgeView = new BadgeView(mContext, imageView);
//                badgeView.setText(obj.getNewcount());
//                badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//                badgeView.setBadgeMargin(0, 0); // 水平和竖直方向的间距
//                badgeView.show();
//            }
//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, MessageViewActivity.class);
//                    intent.putExtra("id", obj.getId());
//                    startActivity(intent);
//                }
//            });
            return view;
        }

//        public void addAll(List<MessageCategory> list) {
//            mList.addAll(list);
//            notifyDataSetChanged();
//        }
//
//        public void removeAll() {
//            mList.clear();
//            notifyDataSetChanged();
//        }
    }
}
