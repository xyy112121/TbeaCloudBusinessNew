package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA11020000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;


public class ActivityFxGgXQ extends Activity implements AsyncListener {
	
	private TextView txtBT;
	private TextView txtBH;
	private TextView txtFBRQ;
	private TextView txtZZRQ;
	private TextView txtFBR;
	private WebView webView;

	
	private String id;
	private TBEA11020000 req;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id=getIntent().getStringExtra("id");
		MyApplication.instance.addActivity(this);
		setContentView(R.layout.activity_fx_gg_xq);
		init();
		setView();
		req=new TBEA11020000(this);
		req.Id=id;
		req.execute();
	}
	
	
	
	private void init(){
		txtBT=(TextView)findViewById(R.id.txtBT);
		txtBH=(TextView)findViewById(R.id.txtBH);
		txtFBRQ=(TextView)findViewById(R.id.txtFBRQ);
		txtZZRQ=(TextView)findViewById(R.id.txtZZRQ);
		
		txtFBR=(TextView)findViewById(R.id.txtFBR);
		webView=(WebView)findViewById(R.id.webView);
			
	}
	
	private void setView(){

	}
	
	@Override
	public void start(String serviceCode) {
		PDUtil.showPD(this);
	}


	@Override
	public void finish(String serviceCode) {
		PDUtil.hidePD(this);
		if(serviceCode.equals(TBEA11020000.SERVICE_CODE)){
			if(!req.isHasError() && req.rspInfo.rspSuccess){
				fullData();
			}else if(!req.isHasError()){
				Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}		
		}
	}
	
	private void fullData(){
		txtBT.setText(req.announcementInfo.Name);
		txtBH.setText(req.announcementInfo.Code);
		txtFBRQ.setText(req.announcementInfo.PublishDate);
		txtZZRQ.setText(req.announcementInfo.EndDate);
		txtFBR.setText(req.announcementInfo.Author);
		webView.loadDataWithBaseURL(null, req.announcementInfo.Content, "text/html", "utf-8", null);
	}
}
