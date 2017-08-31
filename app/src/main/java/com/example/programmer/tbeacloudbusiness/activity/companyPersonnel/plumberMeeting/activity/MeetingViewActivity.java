package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.ParticipantSelectlListAllResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.ParticipantSelectlListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity.PlumberMeetingViewSignlnActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.utils.DensityUtil;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.example.zhouwei.library.CustomPopWindow;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会议详情
 */

public class MeetingViewActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.cp_meeting_prepare_hold_time)
    PublishTextRowView mHoldTimeView;//举办时间
    @BindView(R.id.cp_meeting_prepare_hold_monad)
    LinearLayout mHoldMonadView;//举办单位
    @BindView(R.id.cp_meeting_prepare_hold_addr)
    PublishTextRowView mHoldAddrView;//举办地点
    @BindView(R.id.cp_meeting_prepare_state)
    PublishTextRowView mMeetingPrepareState;//会议状态
    @BindView(R.id.person_info_head)
    CircleImageView mHeadView;
    @BindView(R.id.person_info_name)
    TextView mNameView;
    @BindView(R.id.person_info_personjobtitle)
    ImageView mPersonjobtitleView;
    @BindView(R.id.person_info_right)
    ImageView mRightView;
    @BindView(R.id.person_info_companyname)
    TextView mCompanynameView;

    @BindView(R.id.cp_meeting_prepare_participant)
    PublishTextRowView mParticipantView;//参与人员
    @BindView(R.id.cp_meeting_prepare_plan)
    PublishTextRowView mPlanView;//会议安排
    @BindView(R.id.cp_meeting_prepare_code)
    PublishTextRowView mCodeView;//会议编号
    @BindView(R.id.cp_meeting_prepare_hold_company_layout)
    RelativeLayout mMeetingPrepareHoldCompanyLayout;//举办单位
    @BindView(R.id.cp_meeting_prepare_hold_company_iv)
    ImageView mHoldCompanyIv;
    @BindView(R.id.cp_meeting_prepare_sign)
    PublishTextRowView mMeetingPrepareSign;//会议签到
    @BindView(R.id.cp_meeting_prepare_gallery)
    PublishTextRowView mMeetingPrepareGallery;//现场照片
    @BindView(R.id.cp_meeting_prepare_summary)
    PublishTextRowView mMeetingPrepareSummary;//会议纪要
    @BindView(R.id.cp_meeting_prepare_finish)
    Button mSaveView;


    private CustomPopWindow mCustomPopWindow;
    private boolean mIsEdit = false;//是否可编辑 true可编辑
    private boolean mIsUpdate = false;//是否可更新 true可更新
    private boolean mIsDelete = false;//是否可删除 true可删除

    private boolean mIsEditOnClick = false;//是否已点击编辑 true可编辑
    private boolean mIsUpdateOnClick = false;//是否已点击可更新 true可更新
    private boolean mIsDeleteOnClick = false;//是否已点击可删除 true可删除

    private final int RESULT_DATE = 1000;
    private final int RESULT_COMPANY = 1001;
    private final int RESULT_ADDR = 1002;
    private final int RESULT_PARTICIPANT = 1003;
    private final int RESULT_PLAN = 1004;
    private final int RESULT_UPDATE = 1005;

    MeetingPrepareRequestModel mRequest = new MeetingPrepareRequestModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare_view);
        initTopbar("会议详情", this, R.drawable.icon_morepointwhite);
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
                            PlumberMeetingViewResponseModel model = (PlumberMeetingViewResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.meetinginfo != null) {
                                    PlumberMeetingViewResponseModel.DataBean.MeetingbaseinfoBean meetingbaseinfo = model.data.meetingbaseinfo;
                                    mCodeView.setValueText(meetingbaseinfo.meetingcode);
                                    mRequest.meetingcode = meetingbaseinfo.meetingcode;
                                    String endTime = "";
                                    if (meetingbaseinfo.meetingendtime.length() > 12) {
                                        endTime = meetingbaseinfo.meetingendtime.substring(12, meetingbaseinfo.meetingendtime.length());
                                    }
                                    mRequest.meetingstarttime = meetingbaseinfo.meetingstarttime;
                                    mRequest.meetingendtime = meetingbaseinfo.meetingendtime;
                                    mHoldTimeView.setValueText(meetingbaseinfo.meetingstarttime + "-" + endTime);
                                    mHoldAddrView.setValueText(meetingbaseinfo.meetingprovince + meetingbaseinfo.meetingcity + meetingbaseinfo.meetingzone + meetingbaseinfo.meetingaddr);
                                    mRequest.meetingprovince = meetingbaseinfo.meetingprovince;
                                    mRequest.meetingcity = meetingbaseinfo.meetingcity;
                                    mRequest.meetingzone = meetingbaseinfo.meetingzone;
                                    mRequest.meetingaddr = meetingbaseinfo.meetingaddr;

                                    mMeetingPrepareState.setValueText(meetingbaseinfo.meetingstatus);
                                    //新会议：可编辑，删除，准备中：可编辑 开会中：可更新
                                    if ("新会议".equals(meetingbaseinfo.meetingstatus)) {
                                        mIsEdit = true;
                                        mIsDelete = true;
                                        mMeetingPrepareSummary.setVisibility(View.GONE);
                                        mMeetingPrepareGallery.setVisibility(View.GONE);
                                        mMeetingPrepareSign.setVisibility(View.GONE);
                                    } else if ("准备中".equals(meetingbaseinfo.meetingstatus)) {
                                        mIsEdit = true;
                                        mMeetingPrepareSummary.setVisibility(View.GONE);
                                        mMeetingPrepareGallery.setVisibility(View.GONE);
                                    } else if ("开会中".equals(meetingbaseinfo.meetingstatus)) {
                                        mIsUpdate = true;
                                        mMeetingPrepareSummary.setVisibility(View.GONE);
                                        mMeetingPrepareGallery.setVisibility(View.GONE);
                                    }


                                }
                                //举办单位
                                if (model.data.organizecompanylist != null) {
                                    String companyIds = "";
                                    for (PlumberMeetingViewResponseModel.OrganizeCompany item : model.data.organizecompanylist) {
                                        View pernsonLayout = getLayoutInflater().inflate(R.layout.activity_person_layout2, null);
                                        CircleImageView headView = (CircleImageView) pernsonLayout.findViewById(R.id.person_info_head);
                                        ImageView typwView = (ImageView) pernsonLayout.findViewById(R.id.person_info_personjobtitle);
                                        ImageView rightView = (ImageView) pernsonLayout.findViewById(R.id.person_info_right);
                                        TextView nameView = (TextView) pernsonLayout.findViewById(R.id.person_info_name);
                                        TextView companyNameView = (TextView) pernsonLayout.findViewById(R.id.person_info_companyname);
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.masterthumbpicture, headView);
                                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.companytypeicon, typwView);
                                        nameView.setText(item.mastername);
                                        companyNameView.setText(item.name);
                                        rightView.setVisibility(View.GONE);
                                        mHoldMonadView.addView(pernsonLayout);
                                        if (companyIds.length() > 0) {
                                            companyIds += ",";
                                        }
                                        companyIds += item.id;
                                    }
                                    mRequest.organizecompanylist = companyIds;
                                }

                                if (model.data.meetingoriginatorinfo != null) {//发起人
                                    PlumberMeetingViewResponseModel.DataBean.MeetingoriginatorinfoBean obj = model.data.meetingoriginatorinfo;
                                    ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.headpicture, mHeadView);
                                    ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, mPersonjobtitleView);
                                    mNameView.setText(obj.name);
                                    mCompanynameView.setText(obj.company + " " + obj.jobposition);
                                }

                                if (model.data.participantlist != null) {
                                    mParticipantView.setValueText(model.data.participantlist.participantnumber + "");
                                    mRequest.participantlist = model.data.participantlist.participantlist;
                                }

                                if (model.data.meetinginfo != null) {
                                    mPlanView.setValueText(model.data.meetinginfo.meetingitems);
                                    mMeetingPrepareSummary.setValueText(model.data.meetinginfo.meetingsummary);
                                    mMeetingPrepareGallery.setValueText(model.data.meetinginfo.meetingpicturenumber);
                                }

                                if (model.data.meetingsigninfo != null) {
                                    mMeetingPrepareSign.setValueText(model.data.meetingsigninfo.signnumber);
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
                        PlumberMeetingAction action = new PlumberMeetingAction();
                        String id = getIntent().getStringExtra("id");
                        PlumberMeetingViewResponseModel model = action.getPlumberMeetingView(id);
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

    @OnClick({R.id.cp_meeting_prepare_sign, R.id.cp_meeting_prepare_hold_time, R.id.cp_meeting_prepare_hold_monad, R.id.cp_meeting_prepare_participant, R.id.cp_meeting_prepare_plan, R.id.cp_meeting_prepare_finish, R.id.cp_meeting_prepare_hold_addr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cp_meeting_prepare_hold_time:
                if (mIsEdit == true) {
                    startActivityForResult(new Intent(mContext, CpDateSelectActivity.class), RESULT_DATE);
                }
                break;
            case R.id.cp_meeting_prepare_hold_addr:
                if (mIsEdit == true)
                    startActivityForResult(new Intent(mContext, AddrSelectActivity.class), RESULT_ADDR);
                break;
            case R.id.cp_meeting_prepare_participant:
                Intent intent = new Intent();
                if (mIsEditOnClick == true) {//编辑状态，可更改参与人员
//                    intent.putExtra("flag", true);
                    intent.putExtra("ids", mRequest.participantlist);
                    intent.setClass(mContext, ParticipantSelectAllActivity.class);
                } else {
//                    intent.putExtra("flag", false);
                    intent.putExtra("ids", "");
                    intent.setClass(mContext, ParticipantSelectActivity.class);
                }
                intent.putExtra("meetingId", getIntent().getStringExtra("id"));
                startActivityForResult(intent, RESULT_PARTICIPANT);

                break;
            case R.id.cp_meeting_prepare_plan:
                if (mIsEdit == true) {
                    intent = new Intent(mContext, MeetingPreparePlanActivity.class);
                    intent.putExtra("text", mPlanView.getValueText());
                    startActivityForResult(intent, RESULT_PLAN);
                }

                break;
            case R.id.cp_meeting_prepare_sign:
                intent = new Intent(mContext, PlumberMeetingViewSignlnActivity.class);
                intent.putExtra("meetingId", getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
            case R.id.cp_meeting_prepare_finish:
                editMeeting();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_DATE:
                    String startTime = data.getStringExtra("startTime");
                    String endTime = data.getStringExtra("endTime");
                    String endTime2 = "";
                    if (endTime.length() > 12) {
                        endTime2 = endTime.substring(12, endTime.length());
                    }
                    mHoldTimeView.setValueText(startTime + "-" + endTime2);
                    mRequest.meetingstarttime = startTime;
                    mRequest.meetingendtime = endTime;
                    break;
                case RESULT_COMPANY://举报单位
                    List<FranchiserSelectListResponseModel.DataBean.DistributorlistBean> mSelectList = (List<FranchiserSelectListResponseModel.DataBean.DistributorlistBean>) data.getSerializableExtra("selectObj");
                    String companyIds = "";
                    for (FranchiserSelectListResponseModel.DataBean.DistributorlistBean item : mSelectList) {
                        View pernsonLayout = getLayoutInflater().inflate(R.layout.activity_person_layout2, null);
                        CircleImageView headView = (CircleImageView) pernsonLayout.findViewById(R.id.person_info_head);
                        ImageView typwView = (ImageView) pernsonLayout.findViewById(R.id.person_info_personjobtitle);
                        ImageView rightView = (ImageView) pernsonLayout.findViewById(R.id.person_info_right);
                        TextView nameView = (TextView) pernsonLayout.findViewById(R.id.person_info_name);
                        TextView companyNameView = (TextView) pernsonLayout.findViewById(R.id.person_info_companyname);
                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.masterthumbpicture, headView);
                        ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + item.companytypeicon, typwView);
                        nameView.setText(item.name);
                        companyNameView.setText(item.mastername);
                        rightView.setVisibility(View.GONE);
                        mHoldMonadView.addView(pernsonLayout);
                        if (companyIds.length() > 0) {
                            companyIds += ",";
                        }
                        companyIds += item.id;
                    }
                    mRequest.organizecompanylist = companyIds;
                    break;
                case RESULT_ADDR://举办地点
                    mRequest.meetingprovince = data.getStringExtra("province");
                    mRequest.meetingcity = data.getStringExtra("city");
                    mRequest.meetingzone = data.getStringExtra("county");
                    mRequest.meetingaddr = data.getStringExtra("addrInfo");
                    String addr = data.getStringExtra("addr");
                    mHoldAddrView.setValueText(addr);
                    break;
                case RESULT_PARTICIPANT://参与人员
                    List<ParticipantSelectlListAllResponseModel.DataBean.CompanyemployeelistBean> participants = (List<ParticipantSelectlListAllResponseModel.DataBean.CompanyemployeelistBean>) data.getSerializableExtra("selectObj");
                    String participantIds = "";
                    for (ParticipantSelectlListAllResponseModel.DataBean.CompanyemployeelistBean item : participants) {
                        if (participantIds.length() > 0) {
                            participantIds += ",";
                        }
                        participantIds += item.userid;
                    }
                    mParticipantView.setValueText(participants.size() + "");
                    mRequest.participantlist = participantIds;
                    break;
                case RESULT_PLAN://会议安排
                    String plan = data.getStringExtra("plan");
                    mPlanView.setValueText(plan);
                    mRequest.meetingitems = plan;
                    break;
                case RESULT_UPDATE://点击更新后
                    String gallery = data.getStringExtra("gallery");
                    String summary = data.getStringExtra("summary");
                    mMeetingPrepareGallery.setVisibility(View.VISIBLE);
                    mMeetingPrepareSummary.setVisibility(View.VISIBLE);
                    mMeetingPrepareGallery.setValueText(gallery);
                    mMeetingPrepareSummary.setValueText(summary);
                    break;
            }
        }
    }

    //删除会议
    private void deleteMeeting() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            BaseResponseModel model = (BaseResponseModel) msg.obj;
                            if (model.isSuccess()) {
                                finish();
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
                        CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                        String id = getIntent().getStringExtra("id");
                        BaseResponseModel model = action.deleteMeeting(id);
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


    //编辑时
    private void editMeeting() {
        final CustomDialog dialog = new CustomDialog(mContext, R.style.MyDialog, R.layout.tip_wait_dialog);
        dialog.setText("请等待...");
        dialog.show();
        try {
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    dialog.dismiss();
                    switch (msg.what) {
                        case ThreadState.SUCCESS:
                            MeetingPrepareResponseModel model = (MeetingPrepareResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                Intent intent = new Intent(mContext, MeetingSponsorSuccessActivity.class);
                                intent.putExtra("id", model.data.meetinginfo.meetingid);
                                startActivity(intent);
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
                        CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                        mRequest.originatoruserid = MyApplication.instance.getUserId();
                        MeetingPrepareResponseModel model = action.initiateMeeting(mRequest);
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
    public void onClick(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.acitivity_pop_menu, null);
        ((ImageView) contentView.findViewById(R.id.menu1_iv)).setImageResource(R.drawable.icon_edit);
        ((ImageView) contentView.findViewById(R.id.menu2_iv)).setImageResource(R.drawable.icon_delete_meting);
        ((ImageView) contentView.findViewById(R.id.menu3_iv)).setImageResource(R.drawable.icon_update);
        ((TextView) contentView.findViewById(R.id.menu1_tv)).setText("编辑");
        ((TextView) contentView.findViewById(R.id.menu2_tv)).setText("删除");
        ((TextView) contentView.findViewById(R.id.menu3_tv)).setText("更新");
        contentView.findViewById(R.id.menu3_view).setVisibility(View.VISIBLE);


        View menu1 = contentView.findViewById(R.id.menu1);
        View menu2 = contentView.findViewById(R.id.menu2);
        View menu3 = contentView.findViewById(R.id.menu3);
        menu3.setVisibility(View.VISIBLE);

        // //新会议：可编辑，删除，准备中：可编辑 开会中：可更新
        if (mIsEdit == true && mIsDelete == true) {
            menu1.setAlpha((float) 1.0);
            menu2.setAlpha((float) 1.0);
            menu3.setAlpha((float) 0.3);
        }

        if (mIsEdit == true && mIsDelete == false) {
            menu1.setAlpha((float) 1.0);
            menu2.setAlpha((float) 0.3);
            menu3.setAlpha((float) 0.3);
        }

        if (mIsUpdate == true) {
            menu1.setAlpha((float) 0.3);
            menu2.setAlpha((float) 0.3);
            menu3.setAlpha((float) 1.0);
        }

        if (mIsEdit == false && mIsDelete == false && mIsUpdate == false) {
            menu1.setAlpha((float) 0.3);
            menu2.setAlpha((float) 0.3);
            menu3.setAlpha((float) 0.3);
        }

        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()
                .showAsDropDown(view, DensityUtil.px2dip(mContext, -600), DensityUtil.px2dip(mContext, -90));
    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.menu1://编辑
                        if (mIsEdit == true) {
                            mIsEditOnClick = true;//可编辑
                            mHoldCompanyIv.setVisibility(View.VISIBLE);
                            mHoldTimeView.setEditable(true);
                            mHoldAddrView.setEditable(true);
                            mSaveView.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.menu2://删除
                        deleteMeeting();

                        break;
                    case R.id.menu3://更新
                        Intent intent = new Intent(mContext, MeetingViewUpdateActivity.class);
                        intent.putExtra("meetingid", getIntent().getStringExtra("id"));
                        startActivityForResult(intent, RESULT_UPDATE);

                        break;
                }

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
    }
}
