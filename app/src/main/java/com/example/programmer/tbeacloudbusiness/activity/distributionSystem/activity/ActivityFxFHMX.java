package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA03010103;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;


public class ActivityFxFHMX extends Activity implements AsyncListener, OnClickListener {

    private TextView txtKFMC;
    private TextView txtDDBH;
    private TextView txtDDZE;
    private TextView txtYFHJE;
    private TextView txtYHKJE;
    private TextView txtYSKYE;
    private TextView txtCKKKMX;
    private WebView webView;


    private TBEA03010103 req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.instance.addActivity(this);
        setContentView(R.layout.activity_fx_fhmx);
        init();
        setView();
        req = new TBEA03010103(this);
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
        txtCKKKMX = (TextView) findViewById(R.id.txtCKKKMX);
        webView = (WebView) findViewById(R.id.webView);
    }

    private void setView() {
        txtCKKKMX.setOnClickListener(this);
    }

    @Override
    public void start(String serviceCode) {
        PDUtil.showPD(this);
    }


    @Override
    public void finish(String serviceCode) {
        PDUtil.hidePD(this);
        if (serviceCode.equals(TBEA03010103.SERVICE_CODE)) {
            /*
			nexting=false;
			if(!req.isHasError() && req.rspInfo.rspSuccess){
				if(req.AppraiseList.size()<req.PageSize)isEnd=true;
				fullData();
			}else if(!req.isHasError()){
				Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();	
			}else{
				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}*/
        }
    }

    @Override
    public void onClick(View v) {
        //if(butEvaluation==v)toEvaluation();
    }
}
