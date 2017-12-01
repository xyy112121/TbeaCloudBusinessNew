package com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.model.MyTaskListAllResponseModel;
import com.example.programmer.tbeacloudbusiness.fragment.BaseFragmentV;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 获得我发布的任务列表-­‐全部
 */

public class MyTaskAllListFragment extends BaseFragmentV implements BGARefreshLayout.BGARefreshLayoutDelegate {

    Unbinder unbinder;
    @BindView(R.id.my_task_list_code_tv)
    TextView mCodeTv;
    @BindView(R.id.my_task_list_code_image)
    ImageView mCodeImage;
    @BindView(R.id.my_task_list_status_tv)
    TextView mStatusTv;
    @BindView(R.id.my_task_list_status_image)
    ImageView mStatusImage;
    @BindView(R.id.my_task_list_time_tv)
    TextView mTimeTv;
    @BindView(R.id.my_task_list_time_image)
    ImageView mTimeImage;
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.my_task_list_code_layout)
    LinearLayout mCodeLayout;
    @BindView(R.id.my_task_list_status_layout)
    LinearLayout mStatusLayout;
    @BindView(R.id.my_task_list_time_layout)
    LinearLayout mTimeLayout;

    private MyAdapter mAdapter;
    public String mOrderItem = "time";
    public String mOrder = "desc";
    private int mPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sr_my_task_list_all, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        mAdapter = new MyAdapter(getActivity(), R.layout.activity_my_free_test_list_item);
        mListView.setAdapter(mAdapter);

        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        mRefreshLayout.beginRefreshing();
    }

    /**
     * 获取数据
     */
    public void getDate() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        MyTaskListAllResponseModel re = (MyTaskListAllResponseModel) msg.obj;
                        if (re.isSuccess()) {
                            if (re.data.electricalchecklist != null) {
                                mAdapter.addAll(re.data.electricalchecklist);
                            }
                        } else {
                            showMessage(re.getMsg(), getActivity());
                        }

                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败！", getActivity());
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SubscribeAction action = new SubscribeAction();
                    MyTaskListAllResponseModel re = action.getListAll(mOrderItem, mOrder, mPage++, 10);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mPage = 1;
        mAdapter.clear();
        getDate();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getDate();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_task_list_code_layout, R.id.my_task_list_status_layout, R.id.my_task_list_time_layout})
    public void onViewClicked(View view) {
        int drawable = R.drawable.icon_arraw_grayblue;
        if ("asc".equals(view.getTag()) || view.getTag() == null) {//正
            drawable = R.drawable.icon_arraw_grayblue;
            view.setTag("desc");
        } else if ("desc".equals(view.getTag())) {//倒序\
            drawable = R.drawable.icon_arraw_bluegray;
            view.setTag("asc");
        }
        switch (view.getId()) {
            case R.id.my_task_list_code_layout:
                mCodeImage.setImageResource(drawable);
                mOrderItem = "subscribecode";

                mStatusImage.setImageResource(R.drawable.icon_arraw);
                mTimeImage.setImageResource(R.drawable.icon_arraw);
                mStatusLayout.setTag(null);
                mTimeLayout.setTag(null);
                break;
            case R.id.my_task_list_status_layout:
                mStatusImage.setImageResource(drawable);
                mOrderItem = "checkstatus";

                mCodeImage.setImageResource(R.drawable.icon_arraw);
                mTimeImage.setImageResource(R.drawable.icon_arraw);
                mCodeLayout.setTag(null);
                mTimeLayout.setTag(null);
                break;
            case R.id.my_task_list_time_layout:
                mTimeImage.setImageResource(drawable);
                mOrderItem = "time";

                mStatusImage.setImageResource(R.drawable.icon_arraw);
                mCodeImage.setImageResource(R.drawable.icon_arraw);
                mStatusLayout.setTag(null);
                mCodeLayout.setTag(null);
                break;
        }
        mOrder = view.getTag() + "";
        mRefreshLayout.beginRefreshing();
    }

    class MyAdapter extends ArrayAdapter<MyTaskListAllResponseModel.DataBean.ElectricalchecklistBean> {

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.activity_my_free_test_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mCodeView.setText(getItem(position).subscribecode);
            holder.mStatusView.setText(getItem(position).checkstatus);
            holder.mDateView.setText(getItem(position).subscribetime);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String checkstatusid = getItem(position).checkstatusid;
                    Intent intent = new Intent();
                    if ("new".equals(checkstatusid)) {//待接单
                        intent.setClass(getActivity(), PendingViewActivity.class);
                    }
                    if ("assigned".equals(checkstatusid) || "accepted".equals(checkstatusid)) {//已接单
                        intent.setClass(getActivity(), ServiceHaveAssignViewActivity.class);
                    }
                    if ("finishedbutneedappraise".equals(checkstatusid)) {//已完工
                        intent.setClass(getActivity(), ServiceHaveFinishedViewActivity.class);
                    }
                    if ("finished".equals(checkstatusid)) {//已上传
                        intent.setClass(getActivity(), ServiceHaveUploadViewActivity.class);
                    }
                    intent.putExtra("id", getItem(position).electricalcheckid);
                    startActivity(intent);
                }
            });


            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.my_free_test_list_item_code)
            TextView mCodeView;
            @BindView(R.id.my_free_test_list_item_status)
            TextView mStatusView;
            @BindView(R.id.my_free_test_list_item_date)
            TextView mDateView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
