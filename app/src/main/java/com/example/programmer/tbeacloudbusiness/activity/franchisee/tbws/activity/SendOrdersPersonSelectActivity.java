package com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.action.SubscribeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.CommdityListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.tbws.model.info.SendOrdersPersonSelectResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.SuccessActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.model.EventCity;
import com.example.programmer.tbeacloudbusiness.model.EventFlag;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 派单人员选择
 */

public class SendOrdersPersonSelectActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView mListView;
    private MyAdapter mAdapter;
    private BGARefreshLayout mRefreshLayout;
    private int mSelectPosition = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_history_search_list);
            initTopbar("检测人员选择", "派单", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert();
                }
            });
            mListView = (ListView) findViewById(R.id.listview);
            mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
            mRefreshLayout.setDelegate(this);
            mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, false));

            mAdapter = new MyAdapter(mContext, R.layout.activity_cp_franchiser_select_list_item);
            mListView.setAdapter(mAdapter);

            mRefreshLayout.beginRefreshing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert() {
        if (mSelectPosition != -1) {
            String name = mAdapter.getItem(mSelectPosition).name;
            final String id = mAdapter.getItem(mSelectPosition).electricianid;
            View parentLayout = findViewById(R.id.parentLayout);
            final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
            popWindow1.init(parentLayout, R.layout.pop_window_header,
                    R.layout.activity_scancode_pay_confirm_tip, "确认提示", "你派单的人员为" + name + "请确认！", "", "派单",7, name.length());
            popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
                @Override
                public void onItemClick(String text) {
                    String electricalCheckId = getIntent().getStringExtra("id");
                    sendOrder(electricalCheckId,id);
                }

            });
        } else {
            showMessage("请选择派单人员！");
        }

    }


    /**
     * 派单
     */
    private void sendOrder(final String electricalCheckId, final String electricianId) {
        //确认
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        ResponseInfo re = (ResponseInfo) msg.obj;
                        if (re.data != null) {
                            if (re.isSuccess()) {
                                Intent intent = new Intent(mContext, SuccessActivity.class);
                                intent.putExtra("flag", "sendOrder");//派单
                                intent.putExtra("id",electricalCheckId);
                                startActivity(intent);
                                EventBus.getDefault().post(new EventCity(EventFlag.EVENT_FINISED_ACTIVITY1));
                                finish();
                            } else {
                                showMessage(re.getMsg());
                            }
                        } else {
                            showMessage("操作失败！");
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
                    SubscribeAction action = new SubscribeAction();
                    ResponseInfo re = action.sendOrder(electricalCheckId,electricianId);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
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
                        SendOrdersPersonSelectResponseModel re = (SendOrdersPersonSelectResponseModel) msg.obj;
                        if (re.isSuccess()) {
                            if (re.data != null) {
                                mAdapter.addAll(re.data.electricianlist);
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
                    SubscribeAction action = new SubscribeAction();
                    String code = getIntent().getStringExtra("code");
                    SendOrdersPersonSelectResponseModel re = action.getSendOrdersPerson();
                    if (re == null) {
                        handler.sendEmptyMessage(ThreadState.ERROR);
                    } else {
                        handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        mAdapter.clear();
        getListDate();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载更多
        getListDate();
        return true;
    }

    public class MyAdapter extends ArrayAdapter<SendOrdersPersonSelectResponseModel.DataBean.ElectricianList> {
        int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(resourceId, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final SendOrdersPersonSelectResponseModel.DataBean.ElectricianList obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            holder.mPersonjobtitleView.setVisibility(View.GONE);
            holder.mNameView.setText(obj.name);
            holder.mCompanyNameView.setText(obj.info);
            holder.mRightView.setVisibility(View.GONE);
            if (mSelectPosition == position) {
                holder.mCkView.setChecked(true);
            } else {
                holder.mCkView.setChecked(false);
            }
            // 根据isSelected来设置checkbox的选中状况
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectPosition = position;
                    if (holder.mCkView.isChecked() == false) {
                        holder.mCkView.setChecked(true);
                    }
                    notifyDataSetChanged();
                }
            });

            holder.mCkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectPosition = position;
                    if (holder.mCkView.isChecked() == false) {
                        holder.mCkView.setChecked(true);
                    }
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }


        class ViewHolder {
            @BindView(R.id.person_info_head)
            CircleImageView mHeadView;
            @BindView(R.id.person_info_name)
            TextView mNameView;
            @BindView(R.id.person_info_personjobtitle)
            ImageView mPersonjobtitleView;
            @BindView(R.id.person_info_companyname)
            TextView mCompanyNameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;
            @BindView(R.id.cp_franchiser_select_item_ck)
            CheckBox mCkView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }
}
