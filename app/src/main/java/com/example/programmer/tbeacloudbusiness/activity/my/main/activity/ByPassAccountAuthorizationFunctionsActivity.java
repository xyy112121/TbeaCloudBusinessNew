package com.example.programmer.tbeacloudbusiness.activity.my.main.activity;

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
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.main.action.MyAction;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AuthorizationModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountEditRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountFunctionListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.activity.AddressEditActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddressModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 授权功能
 */

public class ByPassAccountAuthorizationFunctionsActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView mListView;
    MyAdapter mAdapter;
    List<AuthorizationModel> mSelectList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bypass_account_authorization_functions);
        ButterKnife.bind(this);
        initTopbar("授权功能");
        getListDate();
        mAdapter = new MyAdapter(mContext, R.layout.activity_bypass_account_authorization_functions_item);
        mListView.setAdapter(mAdapter);
    }


    /**
     * 获取数据
     */
    public void getListDate() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("加载中...");
        dialog.show();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                switch (msg.what) {
                    case ThreadState.SUCCESS:
                        BypassAccountFunctionListResponseModel model = (BypassAccountFunctionListResponseModel) msg.obj;
                        if (model.isSuccess() && model.data != null) {
                            if (model.data.modulelist != null) {
                                mAdapter.addAll(model.data.modulelist);
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
                    MyAction userAction = new MyAction();
                    BypassAccountFunctionListResponseModel re = userAction.getFunctionList();
                    handler.obtainMessage(ThreadState.SUCCESS, re).sendToTarget();
                } catch (Exception e) {
                    handler.sendEmptyMessage(ThreadState.ERROR);
                }
            }
        }).start();
    }

    @OnClick(R.id.my_bypass_account_functions_save)
    public void onViewClicked() {
        List<AuthorizationModel> list = new ArrayList<>();
        for (AuthorizationModel model : mSelectList) {
            if ("yes".equals(model.canview) || "yes".equals(model.canoperation)) {
                list.add(model);
            }
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(list);
        Intent intent = new Intent();
        intent.putExtra("authorizationList", json);
        setResult(RESULT_OK, intent);
        finish();
    }

    class MyAdapter extends ArrayAdapter<BypassAccountFunctionListResponseModel.DataBean.ModulelistBean> {
        int resourceId;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            resourceId = resource;
        }

        @Override
        public View getView(final int i, View v, ViewGroup viewGroup) {
            final ViewHolder holder;
            if (v == null) {
                v = getLayoutInflater().inflate(R.layout.activity_bypass_account_authorization_functions_item, null);
                holder = new ViewHolder(v);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            final BypassAccountFunctionListResponseModel.DataBean.ModulelistBean obj = getItem(i);
            holder.mNameView.setText(obj.modulename);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.moduleicon, holder.mIconView);
            if ("yes".equals(obj.canview)) {
                holder.mCanviewView.setChecked(true);
            } else {
                holder.mCanviewView.setChecked(false);
            }

            if ("yes".equals(obj.canoperation)) {
                holder.mCanoperationView.setChecked(true);
            } else {
                holder.mCanoperationView.setChecked(false);
            }

            AuthorizationModel item = new AuthorizationModel();
            mSelectList.add(item);

            holder.mCanoperationView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mSelectList.get(i).moduleid = obj.moduleid;
                        mSelectList.get(i).canview = "yes";
                    } else {
                        mSelectList.get(i).canview = "no";
                    }
                }
            });

            holder.mCanviewView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        mSelectList.get(i).moduleid = obj.moduleid;
                        mSelectList.get(i).canoperation = "yes";
                    } else {
                        mSelectList.get(i).canoperation = "no";
                    }
                }
            });

            return v;
        }

        class ViewHolder {
            @BindView(R.id.ba_functions_moduleicon)
            ImageView mIconView;
            @BindView(R.id.ba_functions_modulename)
            TextView mNameView;
            @BindView(R.id.ba_functions_canview)
            CheckBox mCanviewView;
            @BindView(R.id.ba_functions_canoperation)
            CheckBox mCanoperationView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
