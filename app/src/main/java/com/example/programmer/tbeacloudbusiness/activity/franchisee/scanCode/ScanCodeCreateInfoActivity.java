package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeHistoryInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 历史记录详细界面
 */

public class ScanCodeCreateInfoActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    @BindView(R.id.create_code_info_money)
    TextView mMoneyView;
    @BindView(R.id.create_code_info_number)
    TextView mNumberView;
    @BindView(R.id.create_code_info_time)
    TextView mTimeView;
    @BindView(R.id.create_code_info_state_tv)
    TextView mStateTv;
    @BindView(R.id.create_code_info_state_image)
    ImageView mStateImage;
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;

    private String mId, mState;
    private int mPage = 1;
    MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code_info);
        ButterKnife.bind(this);
        initTopbar("生成查看", "下载", this);

        mId = getIntent().getStringExtra("id");
        mState = "";//全部

        mAdapter = new MyAdapter(mContext, R.layout.activity_my_free_test_list_item);
        mListView.setAdapter(mAdapter);

        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();
    }

    @OnClick(R.id.create_code_info_state_layout)
    public void onViewClicked(View v) {
        if ("".equals(mState)) {
            mStateTv.setText("已经激活");
            mStateImage.setImageResource(R.drawable.icon_arraw_grayblue);
            mState = "activityed";
        } else if ("activityed".equals(mState)) {
            mStateTv.setText("没有激活");
            mState = "notactivity";
            mStateImage.setImageResource(R.drawable.icon_arraw_bluegray);
        } else if ("notactivity".equals(mState)) {
            mStateTv.setText("状态");
            mState = "";
            mStateImage.setImageResource(R.drawable.icon_arraw);
        }
        mPage = 1;
        mRefreshLayout.beginRefreshing();
    }

    /**
     * 获取数据
     */
    public void getDate() {
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mRefreshLayout.endLoadingMore();
                mRefreshLayout.endRefreshing();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.isSuccess() && re.data != null) {
                            Gson gson = new GsonBuilder().serializeNulls().create();
                            String json = gson.toJson(re.data);
                            ScanCodeHistoryInfoResponseModel model = gson.fromJson(json, ScanCodeHistoryInfoResponseModel.class);
                            if (model.qrcodelist != null) {
                                mAdapter.addAll(model.qrcodelist);
                            }

                            if (model.rebateqrcodegenerateinfo != null) {
                                ScanCodeHistoryInfoResponseModel.RebateqrcodegenerateinfoBean info = model.rebateqrcodegenerateinfo;
                                mMoneyView.setText(info.money + "");
                                mNumberView.setText(info.number + "");
                                mTimeView.setText(info.time);
                            }
                        } else {
                            showMessage(re.getMsg());
                        }

                        break;
                    case ThreadState.ERROR:
                        showMessage("操作失败！");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ScanCodeAction action = new ScanCodeAction();
                    ResponseInfo re = action.getScanCodeInfo(mId, mState, mPage++, 10);
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
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ScanCodeUrlDownloadActivity.class);
        intent.putExtra("id", mId);
        startActivity(intent);

    }

    class MyAdapter extends ArrayAdapter<ScanCodeHistoryInfoResponseModel.QrcodelistBean> {

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_create_code_info_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mCodeView.setText(getItem(position).rebatecode);//已经激活
            if ("activityed".equals(getItem(position).activitystatusid)) {
                holder.mStateView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_text_normal));
                holder.mStateImageView.setVisibility(View.VISIBLE);
            } else {
                holder.mStateImageView.setVisibility(View.GONE);
                holder.mStateView.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
            }
            holder.mStateView.setText(getItem(position).activitystatus);
            holder.mStateView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ("activityed".equals(getItem(position).activitystatusid)) {
                        Intent intent = new Intent(mContext, ScanCodeViewActivity.class);
                        intent.putExtra("id", getItem(position).rebatecode);
                        startActivity(intent);
                    }
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ScanCodeImageViewActivity.class);
                    intent.putExtra("rebateqrcode", getItem(position).rebatecode);
                    startActivity(intent);
                }
            });


            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.code_info_item_code)
            TextView mCodeView;
            @BindView(R.id.code_info_item_state)
            TextView mStateView;
            @BindView(R.id.code_info_item_state_iv)
            ImageView mStateImageView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
