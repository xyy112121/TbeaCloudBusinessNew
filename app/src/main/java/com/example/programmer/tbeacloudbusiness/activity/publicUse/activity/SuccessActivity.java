package com.example.programmer.tbeacloudbusiness.activity.publicUse.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;
import com.example.programmer.tbeacloudbusiness.activity.check.tbws.activity.UploadPictureListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 操作成功
 */

public class SuccessActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.success_text)
    TextView mSuccessTextView;
    private String mFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sr_task_publish_success);
        ButterKnife.bind(this);
        mFlag = getIntent().getStringExtra("flag");
        findViewById(R.id.top_left).setVisibility(View.GONE);
        if ("sendOrder".equals(mFlag)) {//发布任务成功
            initTopbar("派单成功", "完成", this);
            mSuccessTextView.setText("派单成功");
        }else if("pictureUpload".equals(mFlag)){
            initTopbar("上传成功", "查看", this);
            mSuccessTextView.setText("上传成功");
            findViewById(R.id.top_left).setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        if ("sendOrder".equals(mFlag)) {//派单成功
            finish();
        }else {
            Intent intent = getIntent();
             if("pictureUpload".equals(mFlag)){
               intent.setClass(mContext,UploadPictureListActivity.class);
            }
            startActivity(intent);
        }

    }
}
