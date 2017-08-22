package com.example.programmer.tbeacloudbusiness.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册成功
 */

public class RegisterSuccessActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);
        initTopbar("注册成功");
        mContext = this;
        findViewById(R.id.top_left).setVisibility(View.GONE);
        listener();
    }

    private void listener() {
        findViewById(R.id.completion_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.completion_data:

                break;
        }
    }

    @OnClick({R.id.completion_finish, R.id.completion_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.completion_finish:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.completion_data:
                 intent = new Intent(mContext, CompletionDataActivity.class);
                startActivity(intent);
                break;
        }
    }
}
