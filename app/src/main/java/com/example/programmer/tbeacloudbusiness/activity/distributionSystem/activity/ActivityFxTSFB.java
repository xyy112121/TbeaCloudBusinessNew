package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA14010000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;

public class ActivityFxTSFB extends Activity implements AsyncListener,OnClickListener {
	
	private EditText edtLXDH;
	private EditText edtNR;
	private ImageButton butOK;
	
	
	private TBEA14010000 req;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.instance.addActivity(this);
		setContentView(R.layout.activity_fx_tsfb);
		init();
		setView();
		req=new TBEA14010000(this);
		//req.execute();
	}
	
	
	
	private void init(){
		edtLXDH=(EditText)findViewById(R.id.edtLXDH);
		edtNR=(EditText)findViewById(R.id.edtNR);

		butOK=(ImageButton)findViewById(R.id.butOK);
			
	}
	
	private void setView(){
		butOK.setOnClickListener(this);
	}
	
	
	@Override
	public void start(String serviceCode) {
		PDUtil.showPD(this);
	}


	@Override
	public void finish(String serviceCode) {
		PDUtil.hidePD(this);
		if(serviceCode.equals(TBEA14010000.SERVICE_CODE)){
			if(!req.isHasError() && req.rspInfo.rspSuccess){
				com.mic.etoast2.Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
				finish();
			}else if(!req.isHasError()){
				com.mic.etoast2.Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
			}else{
				com.mic.etoast2.Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}		
		}
	}
	
	@Override
	public void onClick(View v) {
		if(butOK==v)toOK();
	}
	
	private void toOK(){

		String dhhm=edtLXDH.getText().toString();
		String nr=edtNR.getText().toString();
		
		if(dhhm.trim().length()==0){
			com.mic.etoast2.Toast.makeText(this,"联系电话不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(nr.trim().length()==0){
			com.mic.etoast2.Toast.makeText(this,"投诉内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		req.Content=nr;
		req.MobileNumber=dhhm;
		req.execute();
	}
}
