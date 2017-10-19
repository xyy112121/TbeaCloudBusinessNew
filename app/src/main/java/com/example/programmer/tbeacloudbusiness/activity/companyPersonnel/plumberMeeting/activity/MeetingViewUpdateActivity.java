package com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.component.PublishTextRowView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 2017/8/13.
 */

public class MeetingViewUpdateActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.top_right_text)
    TextView mRightTextView;
    @BindView(R.id.cp_meeting_prepare_update_summary)
    PublishTextRowView mSummaryView;
    @BindView(R.id.cp_meeting_prepare_update_gallery)
    PublishTextRowView mGalleryView;
    private String mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_meeting_prepare_view_update);
        ButterKnife.bind(this);
        initTopbar("数据更新", "保存", this);
        String text = getIntent().getStringExtra("text");
        String gallery = getIntent().getStringExtra("gallery");
        mId = getIntent().getStringExtra("meetingid");
        mSummaryView.setValueText(text);
        mGalleryView.setValueText(gallery);
        mRightTextView.setVisibility(View.GONE);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gallery = mGalleryView.getValueText();
                String summary = mSummaryView.getValueText();
                Intent intent = new Intent();
                intent.putExtra("gallery", gallery);
                intent.putExtra("summary", summary);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        String gallery = mGalleryView.getValueText();
        String summary = mSummaryView.getValueText();
        Intent intent = new Intent();
        intent.putExtra("gallery", gallery);
        intent.putExtra("summary", summary);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.cp_meeting_prepare_update_summary, R.id.cp_meeting_prepare_update_gallery, R.id.top_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cp_meeting_prepare_update_summary:
                String summary = mSummaryView.getValueText();
                Intent intent = new Intent(mContext, MeetingPrepareSummaryActivity.class);
                intent.putExtra("meetingid", mId);
                intent.putExtra("text", summary);
                startActivityForResult(intent, 1000);
                break;
            case R.id.cp_meeting_prepare_update_gallery:
                intent = new Intent(mContext, MeetingGalleryListActivity.class);
                intent.putExtra("meetingid", mId);
                startActivityForResult(intent, 1001);
                break;
            case R.id.top_left:
                summary = mSummaryView.getValueText();
                String gallery = mGalleryView.getValueText();
                intent = new Intent();
                intent.putExtra("gallery", gallery);
                intent.putExtra("summary", summary);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {//会议纪要
                String content = data.getStringExtra("summary");
                mSummaryView.setValueText(content);
            } else if (requestCode == 1001) {
                String content = data.getStringExtra("number");
                mGalleryView.setValueText(content);
            }
        }
    }
}
