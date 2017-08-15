package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberManage.activity;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberManage.model.DranchiseeSeleteResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity.PlumberManageMainListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity.PlumberManageWithdrawalHistoryListActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PmMainListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by programmer on 2017/8/15.
 */

public class DranchiseeSeleteActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListView;
    private String mOrderItem, mOrder;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_pm_dranchisee_selete);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTopbar("总经销选择");
        mAdapter = new MyAdapter(mContext, R.layout.activity_cp_pm_dranchisee_selete_item);
        mListView.setAdapter(mAdapter);
        getListData();
    }


    /**
     * 从服务器获取数据
     */
    private void getListData() {
        try {
            final CustomDialog dialog = new CustomDialog(mContext,R.style.MyDialog,R.layout.tip_wait_dialog);
            dialog.setText("请等待");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            DranchiseeSeleteResonpseModel model = (DranchiseeSeleteResonpseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.firstleveldistributorlist != null) {
                                    mAdapter.addAll(model.data.firstleveldistributorlist);
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
                        PlumberManageAction action = new PlumberManageAction();
                        DranchiseeSeleteResonpseModel model = action.getDranchiseeSeleteList(mOrderItem, mOrder);
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

    public class MyAdapter extends ArrayAdapter<DranchiseeSeleteResonpseModel.DataBean.FirstLevelDistributorListBean> {


        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_cp_pm_dranchisee_selete_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final DranchiseeSeleteResonpseModel.DataBean.FirstLevelDistributorListBean obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.thumbpicture, holder.mHeadView);
            holder.mPersonjobtitleView.setVisibility(View.GONE);
            holder.mNameView.setText(obj.mastername);
            holder.mCompanynameView.setText(obj.companyname);
            holder.mNumberView.setText(obj.electriciannumber+"");
            holder.mRightView.setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PlumberManageMainListActivity.class);
                    intent.putExtra("type","distributor");
                    intent.putExtra("id", obj.fdistributorid);
                    startActivity(intent);
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
            TextView mCompanynameView;
            @BindView(R.id.person_info_right)
            ImageView mRightView;
            @BindView(R.id.dramchisee_selete_item_number)
            TextView mNumberView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
