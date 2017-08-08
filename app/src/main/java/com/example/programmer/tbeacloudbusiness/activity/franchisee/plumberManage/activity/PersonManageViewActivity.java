package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PersonManageViewResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PersonManageItemView;
import com.example.programmer.tbeacloudbusiness.component.StarBar;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 水电工管理个人主页
 */

public class PersonManageViewActivity extends BaseActivity {
    @BindView(R.id.pm_view_personname)
    TextView mPersonnameView;
    @BindView(R.id.pm_view_thumbpicture)
    CircleImageView mThumbpictureView;
    @BindView(R.id.pm_withdrawal_history_view_address)
    TextView mAddressView;
    @BindView(R.id.pm_withdrawal_history_view_fansnumber)
    TextView mFansnumberView;
    @BindView(R.id.pm_view_rebatescaninfo)
    PersonManageItemView mRebatescaninfoView;
    @BindView(R.id.pm_view_electricianmeetingattendinfo)
    PersonManageItemView mElectricianmeetingattendinfoView;
    @BindView(R.id.pm_view_orderingserviceinfo)
    PersonManageItemView mOrderingserviceinfoView;
    @BindView(R.id.pm_view_commodityorderinfo)
    PersonManageItemView mCommodityorderinfoView;
    @BindView(R.id.pm_view_logininfo)
    PersonManageItemView mLogininfoView;
    @BindView(R.id.pm_withdrawal_history_view_starlevel)
    StarBar mStarlevelView;
    @BindView(R.id.pm_withdrawal_history_view_workyears)
    TextView mWorkyearsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_manage_person_manage);
        ButterKnife.bind(this);
        getData();
        listener();
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
            mPersonnameView.setText(model.personname);
            ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + model.thumbpicture, mThumbpictureView);
            mAddressView.setText(model.address);
            mFansnumberView.setText("粉丝:" + model.fansnumber);
            mWorkyearsView.setText("工龄:" + model.workyears);
            mStarlevelView.setStarMark(model.starlevel);;
        }

        if (obj.rebatescaninfo != null) {
            mRebatescaninfoView.setText4(obj.rebatescaninfo.takemoneyforoneyear);
            mRebatescaninfoView.setText5(obj.rebatescaninfo.takemoneyforthreemonth);
        }

        if (obj.orderingserviceinfo != null) {
            mOrderingserviceinfoView.setText4(obj.orderingserviceinfo.timesforoneyear);
            mOrderingserviceinfoView.setText5(obj.orderingserviceinfo.timesforthreemonth);
        }

        if (obj.logininfo != null) {
            mLogininfoView.setText4(obj.logininfo.logintimes);
            mLogininfoView.setText5(obj.logininfo.totlelogintime);
        }

        if (obj.electricianmeetingattendinfo != null) {
            mElectricianmeetingattendinfoView.setText4(obj.electricianmeetingattendinfo.attendtimesforoneyear);
            mElectricianmeetingattendinfoView.setText5(obj.electricianmeetingattendinfo.attendtimesforthreemonth);
        }

        if (obj.commodityorderinfo != null) {
            mCommodityorderinfoView.setText4(obj.commodityorderinfo.totlemoneyforoneyear);
            mCommodityorderinfoView.setText5(obj.commodityorderinfo.totlemoneyforthreemonth);
        }

    }

    private void listener() {
//        findViewById(R.id.person_manage_head).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, PlumberManagePersonViewActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        findViewById(R.id.person_manage_scan_rebate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        findViewById(R.id.person_manage_metting).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //签到历史
//
//            }
//        });
//
//        findViewById(R.id.person_manage_login_statistics).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }

    @OnClick({R.id.pm_view_rebatescaninfo, R.id.pm_view_electricianmeetingattendinfo, R.id.pm_view_orderingserviceinfo, R.id.pm_view_commodityorderinfo, R.id.pm_view_logininfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pm_view_rebatescaninfo:
                //扫码返利
                Intent intent = new Intent(mContext, PlumberManageScanCodeDateListActivity.class);
                startActivity(intent);
                break;
            case R.id.pm_view_electricianmeetingattendinfo:
                break;
            case R.id.pm_view_orderingserviceinfo:
                break;
            case R.id.pm_view_commodityorderinfo:
                break;
            case R.id.pm_view_logininfo:
                //登录统计
                 intent = new Intent(mContext, PlumberManageSignHistoryListActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
        }
    }
}
