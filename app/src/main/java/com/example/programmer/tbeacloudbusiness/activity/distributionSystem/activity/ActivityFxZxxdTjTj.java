package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07030600;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;


public class ActivityFxZxxdTjTj extends Activity implements AsyncListener, OnClickListener {

    private String OrderId;

    private TextView txtWL;
    private EditText edtSL;
    private EditText edtDJ;
    private EditText edtFDBL;
    private EditText edtBZ;
    private ImageButton butOK;


    private TBEA07030600 req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrderId = getIntent().getStringExtra("OrderId");
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_zxxd_tj_tj);
        init();
        setView();
        req = new TBEA07030600(this);
        //req.execute();
    }


    private void init() {
        txtWL = (TextView) findViewById(R.id.txtWL);
        edtSL = (EditText) findViewById(R.id.edtSL);
        edtDJ = (EditText) findViewById(R.id.edtDJ);
        edtFDBL = (EditText) findViewById(R.id.edtFDBL);
        edtBZ = (EditText) findViewById(R.id.edtBZ);
        butOK = (ImageButton) findViewById(R.id.butOK);
    }

    private void setView() {
        butOK.setOnClickListener(this);
        txtWL.setOnClickListener(this);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA07030600.SERVICE_CODE)) {
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (butOK == v) toOk();
        if (txtWL == v) toWL();
    }

    private String wlbm = "";

    private void toWL() {
        Intent intent = new Intent(this, ActivityFxZxxdTjCX.class);
        startActivityForResult(intent, 0x123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x123 && resultCode == Activity.RESULT_OK) {
            wlbm = data.getStringExtra("wlbm");
            txtWL.setText(data.getStringExtra("wlmc"));
        }
    }

    private void toOk() {
        String wl = txtWL.getText().toString().trim();
        String sl = edtSL.getText().toString().trim();
        String dj = edtDJ.getText().toString().trim();
        String fdbl = edtFDBL.getText().toString().trim();
        String bz = edtBZ.getText().toString().trim();
        if (wl.isEmpty()) {
            Toast.makeText(this, "物料，单价，数量 必须要填写", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sl.isEmpty()) {
            Toast.makeText(this, "物料，单价，数量 必须要填写", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dj.isEmpty()) {
            Toast.makeText(this, "物料，单价，数量 必须要填写", Toast.LENGTH_SHORT).show();
            return;
        }
        req.OrderId = (OrderId == null ? "" : OrderId);
        req.FloatRatio = fdbl;
        req.ProductCode = wlbm;
        req.Note = bz;
        req.Count = sl;
        req.Price = dj;
        req.execute();
    }
}
