package com.example.programmer.tbeacloudbusiness.activity.plumberManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * Created by programmer on 2017/6/4.
 */

public class LoginStatisticsActivity extends TopActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_manage_login_statistics);
        initTopbar("登录统计");
        listener();
    }

    private void listener(){
        findViewById(R.id.login_number_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginStatisticsActivity.this,LoginDatailsActivity.class);
                startActivity(intent);

            }
        });
    }
}
