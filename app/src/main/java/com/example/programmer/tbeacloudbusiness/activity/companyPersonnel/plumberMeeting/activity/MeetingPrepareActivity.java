package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.action.CpPlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareMesResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.MeetingPrepareResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.ParticipantSelectlListAllResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.ParticipantSelectlListResponseModel;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.component.CustomPopWindow1;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;
import com.example.programmer.tbeacloudbusiness.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司人员-会议准备
 */

public class MeetingPrepareActivity extends BaseActivity {
    @BindView(R.id.cp_meeting_prepare_hold_time)
    PublishTextRowView mHoldTimeView;
    @BindView(R.id.cp_meeting_prepare_hold_monad)
    LinearLayout mHoldMonadView;
    @BindView(R.id.cp_meeting_prepare_hold_addr)
    PublishTextRowView mHoldAddrView;
    @BindView(R.id.cp_meeting_prepare_state)
    PublishTextRowView cpMeetingPrepareState;
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
    PublishTextRowView mParticipantView;
    @BindView(R.id.cp_meeting_prepare_plan)
    PublishTextRowView mPlanView;
    @BindView(R.id.cp_meeting_prepare_code)
    PublishTextRowView mCodeView;
    @BindView(R.id.cp_meeting_prepare_hold_company)
    PublishTextRowView mMeetingPrepareHoldCompany;
    @BindView(R.id.cp_meeting_prepare_hold_company_layout)
    RelativeLayout mMeetingPrepareHoldCompanyLayout;

    private final int RESULT_DATE = 1000;
    private final int RESULT_COMPANY = 1001;
    private final int RESULT_ADDR = 1002;
    private final int RESULT_PARTICIPANT = 1003;
    private final int RESULT_PLAN = 1004;

    MeetingPrepareRequestModel mRequest = new MeetingPrepareRequestModel();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare);
        ButterKnife.bind(this);
        initTopbar("会议准备");
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
                            MeetingPrepareMesResponseModel model = (MeetingPrepareMesResponseModel) msg.obj;
                            if (model.isSuccess() && model.data != null) {
                                if (model.data.meetinginfo != null) {
                                    mCodeView.setValueText(model.data.meetinginfo.meetingcode);
                                    mRequest.meetingcode = model.data.meetinginfo.meetingcode;
                                }

                                if (model.data.meetingoriginatorinfo != null) {
                                    MeetingPrepareMesResponseModel.DataBean.MeetingoriginatorinfoBean obj = model.data.meetingoriginatorinfo;
                                    ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.headpicture, mHeadView);
                                    ImageLoader.getInstance().displayImage(MyApplication.instance.getImgPath() + obj.persontypeicon, mPersonjobtitleView);
                                    mNameView.setText(obj.name);
                                    mCompanynameView.setText(obj.company);
                                    mRightView.setVisibility(View.GONE);
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
                        CpPlumberMeetingAction action = new CpPlumberMeetingAction();
                        MeetingPrepareMesResponseModel model = action.getPrepareData();
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

    @OnClick({R.id.cp_meeting_prepare_hold_time, R.id.cp_meeting_prepare_hold_company, R.id.cp_meeting_prepare_hold_monad, R.id.cp_meeting_prepare_participant, R.id.cp_meeting_prepare_plan, R.id.cp_meeting_prepare_finish, R.id.cp_meeting_prepare_hold_addr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cp_meeting_prepare_hold_time:
                Intent intent = new Intent(mContext, CpDateSelectActivity.class);
                intent.putExtra("startTime", mRequest.meetingstarttime);
                intent.putExtra("endTime", mRequest.meetingendtime);
                startActivityForResult(intent, RESULT_DATE);
                break;
            case R.id.cp_meeting_prepare_hold_monad:
            case R.id.cp_meeting_prepare_hold_company:
                intent = new Intent(mContext, FranchiserSelectListActivity.class);
                intent.putExtra("flag", true);
                intent.putExtra("ids", mRequest.organizecompanylist);
                startActivityForResult(intent, RESULT_COMPANY);
                break;
            case R.id.cp_meeting_prepare_hold_addr:
                intent = new Intent(mContext, AddrSelectActivity.class);
                intent.putExtra("province",mRequest.meetingprovince);
                intent.putExtra("city",mRequest.meetingcity);
                intent.putExtra("county",mRequest.meetingzone);
                intent.putExtra("addr",mRequest.meetingaddr);
                startActivityForResult(intent, RESULT_ADDR);
                break;
            case R.id.cp_meeting_prepare_participant:
                intent = new Intent(mContext, ParticipantSelectAllActivity.class);
//                intent.putExtra("flag",true);
                intent.putExtra("ids", mRequest.participantlist);
                startActivityForResult(intent, RESULT_PARTICIPANT);
                break;
            case R.id.cp_meeting_prepare_plan:
                intent = new Intent(mContext, MeetingPreparePlanActivity.class);
                intent.putExtra("text",mPlanView.getValueText());
                startActivityForResult(intent, RESULT_PLAN);
                break;
            case R.id.cp_meeting_prepare_finish:
                showAlert();
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
                    mHoldMonadView.removeAllViews();
                    mMeetingPrepareHoldCompany.setVisibility(View.GONE);
                    mMeetingPrepareHoldCompanyLayout.setVisibility(View.VISIBLE);
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
            }
        }
    }

    /**
     * 显示警示框
     */
    private void showAlert() {
        View parentLayout = findViewById(R.id.parentLayout);
        final CustomPopWindow1 popWindow1 = new CustomPopWindow1(mContext);
        popWindow1.init(parentLayout, R.layout.pop_window_header,
                R.layout.view_cp_meeting_prepare_alert, "提示");
        popWindow1.setItemClick(new CustomPopWindow1.ItemClick() {
            @Override
            public void onItemClick(String text) {
                initiateMeeting();

            }
        });

    }

    private void initiateMeeting() {
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

}
