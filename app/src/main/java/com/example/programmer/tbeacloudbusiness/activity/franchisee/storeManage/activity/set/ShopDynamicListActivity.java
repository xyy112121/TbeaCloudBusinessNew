package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.DynamicListRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.DynamicListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 店铺动态
 */

public class ShopDynamicListActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.rl_recyclerview_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.shop_dynamic_add_list_item_all_ck)
    CheckBox mAllView;
    @BindView(R.id.shop_dynamic_add_list_item_all_tv)
    TextView mAllTextView;
    @BindView(R.id.shop_dynamic_add_list_item_all_layout)
    RelativeLayout mParentAllView;

    private MyAdapter1 mAdapter;
    private int mPage = 1;
    private DynamicListRequestModel mRequest = new DynamicListRequestModel();
    List<DynamicListResponseModel.DataBean.NewslistBean> mSelectList = new ArrayList<>();
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    private boolean mFlag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_shop_dynamic_list);
            ButterKnife.bind(this);
            initTopbar("店铺动态");
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mAdapter = new MyAdapter1(mContext, R.layout.activity_shop_dynamic_list_item);
        mListView.setAdapter(mAdapter);

        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        mRefreshLayout.beginRefreshing();

        mAllView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mAdapter.selectAll();
                    mAllTextView.setText("全选");
                } else {
                    mAdapter.selectNotAll();
                    mAllTextView.setText("全不选");
                }
            }
        });
    }


    private void getData() {
        try {
            mRequest.page = mPage++;
            mRequest.pageSize = 14;
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mRefreshLayout.endRefreshing();
                    mRefreshLayout.endLoadingMore();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            DynamicListResponseModel model = (DynamicListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.newslist != null) {
                                    mAdapter.initDate(model.data.newslist.size(), model.data.newslist);
                                    mAdapter.addAll(model.data.newslist);
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
                        DynamicListResponseModel model = action.getDynamicList(mRequest);
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
        mAdapter.clear();
        mPage = 1;
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        getData();
        return false;
    }

    @OnClick({R.id.shop_dynamic_list_delete, R.id.top_right_text, R.id.shop_dynamic_add_list_item_all_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop_dynamic_list_delete:
                TextView deleteView = (TextView) view;
                if ("删除".equals((deleteView.getText() + ""))) {
                    try {
                        mParentAllView.setVisibility(View.VISIBLE);
                        mFlag = true;
                        deleteView.setText("完成");
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if ("完成".equals((deleteView.getText() + ""))) {
                    mParentAllView.setVisibility(View.GONE);
                    mFlag = false;
                    deleteView.setText("删除");
                    if (mSelectList.size() > 0) {
                        String ids = "";
                        for (DynamicListResponseModel.DataBean.NewslistBean item : mSelectList) {
                            if (ids.length() > 0) {
                                ids += ",";
                            }
                            ids += item.newsid;
                        }
                        delete(ids);
                    }
                }

                break;
            case R.id.top_right_text:
                Intent intent = new Intent(mContext, ShopDynamicAddActivity.class);
                startActivity(intent);
                break;
            case R.id.shop_dynamic_add_list_item_all_layout:
            case R.id.shop_dynamic_add_list_item_all_ck:
            case R.id.shop_dynamic_add_list_item_all_tv:
                if (mAllView.isChecked()) {
                    mAllView.setChecked(false);
                } else {
                    mAllView.setChecked(true);
                }
                break;
        }
    }

    private void delete(final String ids) {

//        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_delete_dialog);
//        dialog.show();
//        dialog.setText("删除后不可恢复，确定删除么？");
//        dialog.setConfirmBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//
//            }
//        }, "否");
//        dialog.setCancelBtnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
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
                        mAdapter.selectNotAll();
                        mSelectList.clear();
                        ToastUtil.showMessage(re.getMsg());

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
                    ResponseInfo re = action.deleteDynamic(ids);
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();

//    }
//        }, "是");
    }

    class MyAdapter1 extends ArrayAdapter<DynamicListResponseModel.DataBean.NewslistBean> {
        private int resourceId;

        public MyAdapter1(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
            isSelected = new HashMap<>();
            // 初始化数据
            initDate(getCount(), null);
        }

        //全选
        private void selectAll() {
            for (int i = 0; i < isSelected.size(); i++) {
                isSelected.put(i, true);
                notifyDataSetChanged();
            }
        }

        //全选
        private void selectNotAll() {
            for (int i = 0; i < isSelected.size(); i++) {
                isSelected.put(i, false);
                notifyDataSetChanged();
            }
        }

        // 初始化isSelected的数据
        public void initDate(int count, List<DynamicListResponseModel.DataBean.NewslistBean> list) {
            for (int i = 0; i < count; i++) {
                isSelected.put(i, false);
            }
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_shop_dynamic_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + getItem(position).thumbpicture, holder.mPictureView);
            holder.mTimeView.setText(getItem(position).time);
            holder.mTitleView.setText(getItem(position).title);
            if (mFlag) {
                holder.mCk.setVisibility(View.VISIBLE);
                holder.mCk.setChecked(isSelected.get(position));
                if (isSelected.get(position) == true) {
                    mSelectList.add(getItem(position));
                    removeDuplicate(mSelectList);
                }


                holder.mCk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isSelected.get(position)) {
                            isSelected.put(position, false);
                            mSelectList.remove(getItem(position));
                        } else {
                            isSelected.put(position, true);
                            mSelectList.add(getItem(position));
                            removeDuplicate(mSelectList);
                        }
                        notifyDataSetChanged();
                    }
                });
            } else {
                holder.mCk.setVisibility(View.GONE);
            }


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent();
//                    intent.putExtra("type", mFlag);
//                    intent.putExtra("obj", getItem(position));
//                    setResult(RESULT_OK, intent);
//                    finish();
                }
            });
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.shop_dynamic_add_list_item_ck)
            CheckBox mCk;
            @BindView(R.id.shop_dynamic_item_thumbpicture)
            ImageView mPictureView;
            @BindView(R.id.shop_dynamic_item_title)
            TextView mTitleView;
            @BindView(R.id.shop_dynamic_item_time)
            TextView mTimeView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        public void removeDuplicate(List list) {
            HashSet h = new HashSet(list);
            list.clear();
            mSelectList.addAll(h);
        }
    }
}
