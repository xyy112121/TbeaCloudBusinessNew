package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action.StoreManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.RotateADListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.StoreIntroduceResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺介绍
 */

public class StoreIntroduceActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.store_introduce_tv)
    EditText mStoreIntroduceTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_introduce);
        ButterKnife.bind(this);
        initTopbar("店铺介绍", "保存", this);
        getDate();
    }

    /**
     * 从服务器获取数据
     */
    private void getDate() {
        try {
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("请等待...");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            StoreIntroduceResponseModel model = (StoreIntroduceResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.shopinfo != null) {
                                    mStoreIntroduceTv.setText(model.data.shopinfo.shopdescription);
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
                        StoreIntroduceResponseModel model = action.getStoreIntroduce();
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
    public void onClick(View v) {
        save();
    }

    private void save() {
        try {
            final String text = mStoreIntroduceTv.getText() + "";
            final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
            dialog.setText("请等待...");
            dialog.show();
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            ResponseInfo model = (ResponseInfo) msg.obj;
                            if (model.isSuccess()) {
                                ToastUtil.showMessage("操作成功！");
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
                        ResponseInfo model = action.setStoreIntroduce(text);
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

}
