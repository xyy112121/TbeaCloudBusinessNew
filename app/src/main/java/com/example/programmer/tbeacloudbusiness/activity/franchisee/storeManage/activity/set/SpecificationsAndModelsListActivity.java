package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.action.ScanCodeAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeTypeSelectReponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.ModelSpecListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.Condition;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 规格型号
 */

public class SpecificationsAndModelsListActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    private MyAdapter mAdapter;
    private String method;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specifications_and_models_list);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        initTopbar(title, "添加", this);
        method = getIntent().getStringExtra("method");
        initView();
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
                            ScanCodeTypeSelectReponseModel model = (ScanCodeTypeSelectReponseModel) msg.obj;
                            if (model.isSuccess()) {
                                //获取选择型号 TBEAYUN001002001000
                                if ("TBEAYUN001002001000".equals(method)) {
                                    if (model.data.commoditymodellist != null && model.data.commoditymodellist.size() > 0) {
                                        mAdapter.addAll(model.data.commoditymodellist);
                                    }
                                } else {
                                    if (model.data.commodityspecificationlist != null && model.data.commodityspecificationlist.size() > 0) {
                                        mAdapter.addAll(model.data.commodityspecificationlist);
                                    }
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
                        StoreManageAction action = new StoreManageAction();
                        ScanCodeTypeSelectReponseModel model = action.getTypeSelect(method);
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

    private void initView() {
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);

        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_recyclerview_refresh);
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, false));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, SpecificationsAndModelsEditActivity.class);
        intent.putExtra("title", getIntent().getStringExtra("title"));
        intent.putExtra("methodName", getIntent().getStringExtra("methodName"));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshLayout.beginRefreshing();
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.removeAll();
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getData();
        return true;
    }

    class MyAdapter extends BaseAdapter {
        private List<Condition> mList = new ArrayList<>();

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
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_specifications_and_models_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.nameView.setText(mList.get(position).getName());
            holder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(mList.get(position).getId(), position);
                }
            });
            return convertView;
        }

        public void addAll(List<Condition> list) {
            mList = list;
            notifyDataSetChanged();
        }

        public void removeAll() {
            mList.clear();
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.model_spec_list_item_name)
            TextView nameView;
            @BindView(R.id.model_spec_list_item_delete)
            ImageView deleteView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        /**
         * 删除
         *
         * @param id
         */
        private void delete(final String id, final int postion) {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
            dialog.show();
            dialog.setText("删除后不可恢复，确定删除么？");
            dialog.setConfirmBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            }, "否");
            dialog.setCancelBtnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
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
                                    if (re.isSuccess()) {
                                        mList.remove(postion);
                                        notifyDataSetChanged();
                                    } else {
                                        ToastUtil.showMessage(re.getMsg());
                                    }
                                    break;
                                case ThreadState.ERROR:
                                    ToastUtil.showMessage("操作失败!");
                                    break;
                            }
                        }
                    };

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                StoreManageAction action = new StoreManageAction();
                                ResponseInfo re = action.deleteModelSpec(id);
                                handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                            } catch (Exception e) {
                                handler.sendEmptyMessage(ThreadState.ERROR);
                            }
                        }
                    }).start();

                }
            }, "是");
        }
    }
}
