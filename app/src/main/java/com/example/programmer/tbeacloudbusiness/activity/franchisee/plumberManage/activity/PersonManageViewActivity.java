package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.action.PlumberManageAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model.PersonManageViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.activity.order.OrderListActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MyAttentionActivity;
import com.example.programmer.tbeacloudbusiness.activity.my.main.activity.MyFansActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PersonManageItemView;
import com.example.programmer.tbeacloudbusiness.component.StarBar;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;
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
    @BindView(R.id.pm_withdrawal_history_view_starlevel)
    StarBar mStarlevelView;
    @BindView(R.id.pm_withdrawal_history_view_workyears)
    TextView mWorkyearsView;
    @BindView(R.id.pm_view_logininfo)
    TextView mLogininfoView;
    @BindView(R.id.pm_view_socialinfo)
    PersonManageItemView mSocialinfoView;
    @BindView(R.id.top_left)
    ImageButton mTopLeft;

    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_plumber_manage_person_manage);
            StatusBarUtil.setTranslucent(this, 0);
            ButterKnife.bind(this);
            findViewById(R.id.top_right_text).setVisibility(View.GONE);
            mId = getIntent().getStringExtra("id");
            getData();
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mSocialinfoView.setText4Click(new PersonManageItemView.Text4Click() {
            @Override
            public void onClick() {
                Intent intent = new Intent(mContext, MyFansActivity.class);
                intent.putExtra("id", mId);
                startActivity(intent);
            }
        });

        mSocialinfoView.setText5Click(new PersonManageItemView.Text5Click() {
            @Override
            public void onClick() {
                Intent intent = new Intent(mContext, MyAttentionActivity.class);
                intent.putExtra("id", mId);
                startActivity(intent);
            }
        });

//        mRebatescaninfoView.setText4Click(new PersonManageItemView.Text4Click() {
//            @Override
//            public void onClick() {
//                ddd
//                Intent intent = new Intent(mContext, MyFansActivity.class);
//                intent.putExtra("id",mId );
//                startActivity(intent);
//            }
//        });
//
//        mRebatescaninfoView.setText5Click(new PersonManageItemView.Text5Click() {
//            @Override
//            public void onClick() {
//                Intent intent = new Intent(mContext, MyFansActivity.class);
//                intent.putExtra("id",mId );
//                startActivity(intent);
//            }
//        });


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
                        PersonManageViewResponseModel model = action.getPersonManageView(mId);
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
            mStarlevelView.setStarMark(model.starlevel);
        }

        if (obj.rebatescaninfo != null) {
            mRebatescaninfoView.setText4(obj.rebatescaninfo.takemoneyforoneyear);
            mRebatescaninfoView.setText5(obj.rebatescaninfo.takemoneyforthreemonth);
        }

        if (obj.orderingserviceinfo != null) {
            String timesforoneyear = obj.orderingserviceinfo.timesforoneyear;
            mOrderingserviceinfoView.setText4(timesforoneyear);
            String timesforthreemonth = obj.orderingserviceinfo.timesforthreemonth;
            mOrderingserviceinfoView.setText5(timesforthreemonth);
        }

        if (obj.logininfo != null) {
            mLogininfoView.setText(obj.logininfo.lastloginaddr);
        }

        if (obj.socialinfo != null) {
            mSocialinfoView.setText4(obj.socialinfo.fansnumber);
            mSocialinfoView.setText5(obj.socialinfo.focusnumber);
        }

        if (obj.electricianmeetingattendinfo != null) {
            mElectricianmeetingattendinfoView.setText4(obj.electricianmeetingattendinfo.attendtimesforoneyear);
            mElectricianmeetingattendinfoView.setText5(obj.electricianmeetingattendinfo.attendtimesforthreemonth);
        }

        if (obj.commodityorderinfo != null) {
            mCommodityorderinfoView.setText4(obj.commodityorderinfo.ordermoneyforoneyear);
            mCommodityorderinfoView.setText5(obj.commodityorderinfo.ordermoneythreemonth);
        }

    }


    @OnClick({R.id.pm_view_thumbpicture, R.id.pm_view_rebatescaninfo, R.id.pm_view_electricianmeetingattendinfo, R.id.pm_view_orderingserviceinfo, R.id.pm_view_commodityorderinfo, R.id.pm_view_logininfo_layout, R.id.top_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pm_view_rebatescaninfo:
                //扫码返利
                Intent intent = new Intent(mContext, PlumberManageScanCodeDateListActivity.class);
                startActivity(intent);
                break;
            case R.id.pm_view_electricianmeetingattendinfo:
                //签到历史
                intent = new Intent(mContext, PlumberManageSignHistoryListActivity.class);
                startActivity(intent);

                break;
            case R.id.pm_view_orderingserviceinfo:
                break;
            case R.id.pm_view_commodityorderinfo:
                intent = new Intent(mContext, OrderListActivity.class);
                intent.putExtra("id", mId);
                startActivity(intent);
                break;
            case R.id.pm_view_logininfo_layout:
                //登录统计
                intent = new Intent(mContext, PlumberManageLoginDatailsActivity.class);
                intent.putExtra("id", mId);
                startActivity(intent);
                break;
            case R.id.top_left:
                finish();
                break;
            case R.id.pm_view_thumbpicture:
            case R.id.pm_view_personname:
                intent = new Intent(mContext, PlumberManagePersonViewActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
            case R.id.pm_view_socialinfo:
                intent = new Intent(mContext, MyAttentionActivity.class);
                intent.putExtra("id", mId);
                startActivity(intent);
                break;
        }
    }
}
