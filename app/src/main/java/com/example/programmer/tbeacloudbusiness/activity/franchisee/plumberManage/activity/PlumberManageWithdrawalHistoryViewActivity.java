package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PersonManageViewResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.StarBar;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 2017/8/8.
 */

public class PlumberManageWithdrawalHistoryViewActivity extends BaseActivity {
    @BindView(R.id.pm_view_personname)
    TextView mNameView;
    @BindView(R.id.pm_view_thumbpicture)
    CircleImageView mThumbpictureView;
    @BindView(R.id.pm_withdrawal_history_view_address)
    TextView mAddressView;
    @BindView(R.id.pm_withdrawal_history_view_starlevel)
    StarBar mStarlevelView;
    @BindView(R.id.pm_withdrawal_history_view_workyears)
    TextView mWorkyearsView;
    @BindView(R.id.pm_withdrawal_history_view_fansnumber)
    TextView mFansnumberView;
    @BindView(R.id.pm_withdrawal_history_view_webView)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm_withdrawal_history_view);
        StatusBarUtil.setTranslucent(this,0);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
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
                            PersonManageViewResponseModel model = (PersonManageViewResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                setData(model.data);
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
                        String id = getIntent().getStringExtra("id");
                        PersonManageViewResponseModel model = action.getPersonManageView(id);
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

    private void setData(PersonManageViewResponseModel.PersonManageView obj) {
        if (obj.electricianbaseinfo != null) {
            PersonManageViewResponseModel.ElectricianBaseInfo model = obj.electricianbaseinfo;
            mNameView.setText(model.personname);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + model.thumbpicture, mThumbpictureView);
            mAddressView.setText(model.address);
            mFansnumberView.setText("粉丝:" + model.fansnumber);
            mWorkyearsView.setText("工龄:" + model.workyears);
            mStarlevelView.setStarMark(model.starlevel);
        }
    }

    @OnClick(R.id.top_right_text)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, PersonManageViewActivity.class);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        startActivity(intent);
    }
}