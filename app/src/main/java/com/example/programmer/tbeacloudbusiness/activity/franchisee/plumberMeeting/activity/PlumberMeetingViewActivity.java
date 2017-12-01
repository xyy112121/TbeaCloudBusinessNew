package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.MeetingGalleryListActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.MeetingPreparePlanActivity;
import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity.MeetingPrepareSummaryActivity;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.action.PlumberMeetingAction;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.activity.WebViewActivity;
import com.example.programmer.tbeacloudbusiness.component.CircleImageView;
import com.example.programmer.tbeacloudbusiness.component.CustomDialog;
import com.example.programmer.tbeacloudbusiness.utils.ThreadState;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 会议详细
 */

public class PlumberMeetingViewActivity extends BaseActivity {

    @BindView(R.id.pm_view_meetingcode)
    TextView mMeetingcodeView;
    @BindView(R.id.pm_view_meetingtime)
    TextView mMeetingtimeView;
    @BindView(R.id.pm_view_organizecompanylist)
    LinearLayout mOrganizeCompanyView;
    @BindView(R.id.pm_view_meetingplace)
    TextView mMeetingplaceView;
    @BindView(R.id.pm_view_meetingstatus)
    TextView mMeetingstatusView;
    @BindView(R.id.pm_view_meetingoriginatorinfo)
    LinearLayout mMeetingoriginatorInfoView;
    @BindView(R.id.pm_view_participantlist)
    TextView mParticipantlistView;
    @BindView(R.id.pm_view_meetingitems)
    TextView mMeetingitemsView;
    @BindView(R.id.pm_view_meetingsummary)
    TextView mMeetingsummaryView;
    @BindView(R.id.pm_view_meetingpicturenumber)
    TextView mMeetingpicturenumberView;
    @BindView(R.id.pm_view_meetingsigninfo)
    TextView mMeetingsigninfoView;

    private String mId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_meeting_view);
        ButterKnife.bind(this);
        mId = getIntent().getStringExtra("id");
        initTopbar("会议详情");
        listener();
        getData();
    }

    private void listener() {
        findViewById(R.id.plumber_meeting_view_participant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlumberMeetingParticipantListActivity.class);
                intent.putExtra("meetingId", mId);
                startActivity(intent);
            }
        });

        findViewById(R.id.plumber_meeting_view_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlumberMeetingViewSignlnActivity.class);
                intent.putExtra("meetingId", mId);
                startActivity(intent);
            }
        });

        mMeetingitemsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //会议安排
                Intent intent = new Intent(mContext, MeetingPreparePlanActivity.class);
                intent.putExtra("text", mMeetingitemsView.getText() + "");
                intent.putExtra("flag", "view");
                startActivity(intent);
            }
        });

        mMeetingsummaryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MeetingPrepareSummaryActivity.class);
                intent.putExtra("flag", "view");
                intent.putExtra("text", mMeetingsummaryView.getText() + "");
                intent.putExtra("meetingid", mId);
                startActivity(intent);

//                Intent intent = new Intent(mContext, WebViewActivity.class);
//                String url = "http://121.42.193.154:6696/index.php/h5forapp/Index/meetingsummary?meetingid=" + mId;
//                intent.putExtra("url", url);
//                intent.putExtra("title", "会议纪要");
//                startActivity(intent);
            }
        });

        mMeetingpicturenumberView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MeetingGalleryListActivity.class);
                intent.putExtra("flag", "view");
                intent.putExtra("meetingid", mId);
                startActivity(intent);
            }
        });


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
                                setData(model.data);
                            } else {
                                showMessage(model.getMsg());
                            }
                            break;
                        case ThreadState.ERROR:
                            showMessage("操作失败！");
                            break;
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PlumberMeetingAction action = new PlumberMeetingAction();
                        PlumberMeetingViewResponseModel model = action.getPlumberMeetingView(mId);
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

    public void setData(PlumberMeetingViewResponseModel.DataBean obj) {
        String path = MyApplication.instance.getImgPath();
        if (obj.meetingbaseinfo != null) {
            mMeetingcodeView.setText(obj.meetingbaseinfo.meetingcode);
            mMeetingtimeView.setText(obj.meetingbaseinfo.meetingtime);
            mMeetingplaceView.setText(obj.meetingbaseinfo.meetingplace);
            mMeetingstatusView.setText(obj.meetingbaseinfo.meetingstatus);
        }

        //举办单位
        if (obj.organizecompanylist != null) {
            for (PlumberMeetingViewResponseModel.OrganizeCompany item : obj.organizecompanylist) {
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
                mOrganizeCompanyView.addView(pernsonLayout);
            }
        }

        //发起人
        if (obj.meetingoriginatorinfo != null) {
            CircleImageView mHodePersonHeadView = (CircleImageView) mMeetingoriginatorInfoView.findViewById(R.id.person_info_head);
            TextView mHodePersonNameView = (TextView) mMeetingoriginatorInfoView.findViewById(R.id.person_info_name);
            TextView mHodePersonCompanyNameView = (TextView) mMeetingoriginatorInfoView.findViewById(R.id.person_info_companyname);
            ImageView mHodePersonPersonjobtitleView = (ImageView) mMeetingoriginatorInfoView.findViewById(R.id.person_info_personjobtitle);
            ImageView mHodePersonInfoRightView = (ImageView) mMeetingoriginatorInfoView.findViewById(R.id.person_info_right);

            PlumberMeetingViewResponseModel.DataBean.MeetingoriginatorinfoBean model = obj.meetingoriginatorinfo;
            ImageLoader.getInstance().displayImage(path + model.headpicture, mHodePersonHeadView);
            ImageLoader.getInstance().displayImage(path + model.persontypeicon, mHodePersonPersonjobtitleView);
            mHodePersonNameView.setText(model.name);
            mHodePersonCompanyNameView.setText(model.company + "  " + model.jobposition);
            mHodePersonInfoRightView.setVisibility(View.GONE);
        }

        if (obj.participantlist != null) {
            mParticipantlistView.setText(obj.participantlist.participantnumber + "");
        }

        if (obj.meetinginfo != null) {
            PlumberMeetingViewResponseModel.MeetingInfo model = obj.meetinginfo;
            mMeetingitemsView.setText(model.meetingitems);
            mMeetingsummaryView.setText(model.meetingsummary);
            mMeetingpicturenumberView.setText(model.meetingpicturenumber);
        }

        if (obj.meetingsigninfo != null) {
            mMeetingsigninfoView.setText(obj.meetingsigninfo.signnumber);
        }

    }
}
