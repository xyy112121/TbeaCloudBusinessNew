package com.example.programmer.tbeacloudbusiness.activity.user;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.action.UserAction;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 隶属人员(单选)
 */

public class AffiliationSelectListActivity extends BaseActivity {
    @BindView(R.id.cp_franchiser_list_number)
    TextView mNumberView;
    private ListView mListView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_franchiser_select_list);
        ButterKnife.bind(this);
        initTopbar("总经销商列表");
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.cp_franchiser_list_number_tv)).setText("总经销商");
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new MyAdapter(mContext, R.layout.activity_cp_franchiser_select_list_item);
        mListView.setAdapter(mAdapter);
        getListData();
    }

    /**
     * 从服务器获取数据
     */
    private void getListData() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            FranchiserSelectListResponseModel model = (FranchiserSelectListResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.distributorlist != null) {
                                    mAdapter.addAll(model.data.distributorlist);
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
                        UserAction action = new UserAction();
                        String city = getIntent().getStringExtra("city");
                        String province = getIntent().getStringExtra("province");
                        FranchiserSelectListResponseModel model = action.getAffiliationList(province,city);
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


    public class MyAdapter extends ArrayAdapter<FranchiserSelectListResponseModel.DataBean.DistributorlistBean> {
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
            final FranchiserSelectListResponseModel.DataBean.DistributorlistBean obj = getItem(position);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.masterthumbpicture, holder.mHeadView);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.companytypeicon, holder.mPersonjobtitleView);
            holder.mNameView.setText(obj.name);
            holder.mCompanyNameView.setText(obj.mastername);
            holder.mRightView.setVisibility(View.GONE);
            holder.mCkView.setVisibility(View.GONE);
            // 根据isSelected来设置checkbox的选中状况
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FranchiserSelectListResponseModel.DataBean.DistributorlistBean obj = getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra("selectObj", obj);
                    setResult(RESULT_OK, intent);
                    finish();
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