package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.TopActivity;

/**
 * Created by programmer on 2017/6/4.
 */

public class PersonManageActivity extends TopActivity{
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_manage_person_manage);
        mContext = this;
        listener();
    }

    private void listener(){
        findViewById(R.id.person_manage_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PersonViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.person_manage_scan_rebate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫码返利
                Intent intent = new Intent(mContext,ScanCodeDateListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.person_manage_metting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //签到历史
                Intent intent = new Intent(mContext,SignHistoryListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.person_manage_login_statistics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录统计
                Intent intent = new Intent(mContext,LoginStatisticsActivity.class);
                startActivity(intent);
            }
        });


    }
}
