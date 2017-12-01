package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA14020100;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;

public class ActivityFxTSXQ extends Activity implements AsyncListener, OnClickListener {
    private TextView txtLXDH;
    private TextView txtNR;


    private String id;
    private TBEA14020100 req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_tsxq);
        init();
        setView();
        req = new TBEA14020100(this);
        req.Id = id;
        req.execute();
    }


    private void init() {
        txtLXDH = (TextView) findViewById(R.id.txtLXDH);
        txtNR = (TextView) findViewById(R.id.txtNR);


    }

    private void setView() {
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA14020100.SERVICE_CODE)) {
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                fullData();
            } else if (!req.isHasError()) {
                com.mic.etoast2.Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void fullData() {
        txtLXDH.setText(req.complainInfo.ContactMobile);
        txtNR.setText(req.complainInfo.Content);
    }


    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }

}
