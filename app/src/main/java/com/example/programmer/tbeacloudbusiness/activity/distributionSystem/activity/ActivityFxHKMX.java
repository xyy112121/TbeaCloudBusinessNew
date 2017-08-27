package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA08010100;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;


public class ActivityFxHKMX extends Activity implements AsyncListener, OnClickListener {

    private TextView txtKFMC;
    private TextView txtDDBH;
    private TextView txtDDZE;
    private TextView txtYFHJE;
    private TextView txtYHKJE;
    private TextView txtYSKYE;
    private TextView txtCKFYMX;
    private WebView webView;


    private TBEA08010100 req;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_hkmx);
        init();
        setView();
        req = new TBEA08010100(this);
        req.OrderId = id;
        req.Page = 1;
        req.PageSize = 14;
        req.execute();
    }


    private void init() {
        txtKFMC = (TextView) findViewById(R.id.txtKFMC);
        txtDDBH = (TextView) findViewById(R.id.txtDDBH);
        txtDDZE = (TextView) findViewById(R.id.txtDDZE);
        txtYFHJE = (TextView) findViewById(R.id.txtYFHJE);
        txtYHKJE = (TextView) findViewById(R.id.txtYHKJE);
        txtYHKJE = (TextView) findViewById(R.id.txtYHKJE);
        txtYSKYE = (TextView) findViewById(R.id.txtYSKYE);
        txtCKFYMX = (TextView) findViewById(R.id.txtCKFYMX);
        webView = (WebView) findViewById(R.id.webView);
    }

    private void setView() {
        txtCKFYMX.setOnClickListener(this);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA08010100.SERVICE_CODE)) {
            if (!req.isHasError() && req.rspInfo.rspSuccess) {
                fullData();
            } else if (!req.isHasError()) {
                Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fullData() {
        txtKFMC.setText(req.orderInfo.CustomerName);
        txtDDBH.setText(req.orderInfo.OrderCode);
        txtDDZE.setText("￥" + req.orderInfo.OrderMoney);
        //txtYFHJE.setText("￥"+req.orderInfo.DeliveryMoney);
        txtYHKJE.setText("￥" + req.orderInfo.DeliveryMoney);
        //txtYSKYE.setText("￥"+req.orderInfo.DeliveryMoney);

        webView.loadDataWithBaseURL(null, req.html, "text/html", "utf-8", null);


    }

    @Override
    public void onClick(View v) {
        //if(butEvaluation==v)toEvaluation();
    }
}
