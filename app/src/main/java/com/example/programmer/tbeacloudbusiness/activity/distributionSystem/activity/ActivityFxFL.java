package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA10010000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA10020000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.RebatedListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ShouldRebateListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxFlItem1;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxFlItem3;
import com.example.programmer.tbeacloudbusiness.utils.DateUtil;

import java.util.Date;

public class ActivityFxFL extends Activity implements AsyncListener,RefreshableView.PullToRefreshListener,OnClickListener {
	
	private TextView txtGSMC;
	private TextView txtSJ;
	private TextView txtYFLJE;
	private TextView txtYYFLJE;
	private LinearLayout linYFMX;
	private LinearLayout linYYFMX;
	private View viewFlag1;
	private View viewFlag2;
	private ListView lstContent;
	private RefreshableView refView;
	
	
	private TBEA10010000 req;
	private TBEA10020000 req2;
	private Adapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.instance.addActivity(this);
		setContentView(R.layout.activity_fx_fl);
		init();
		setView();
		req=new TBEA10010000(this);
		req2=new TBEA10020000(this);
		//req.execute();
		
		adapter=new Adapter();
		lstContent.setAdapter(adapter);
		toYFMX();
		

		//req.execute();
	}
	
	
	
	private void init(){
		txtGSMC=(TextView)findViewById(R.id.txtGSMC);
		txtSJ=(TextView)findViewById(R.id.txtSJ);
		txtYFLJE=(TextView)findViewById(R.id.txtYFLJE);
		txtYYFLJE=(TextView)findViewById(R.id.txtYYFLJE);
		
		linYFMX=(LinearLayout)findViewById(R.id.linYFMX);
		linYYFMX=(LinearLayout)findViewById(R.id.linYYFMX);
		
		lstContent=(ListView)findViewById(R.id.lstContent);
		refView=(RefreshableView)findViewById(R.id.refView);
		viewFlag1=(View)findViewById(R.id.viewFlag1);
		viewFlag2=(View)findViewById(R.id.viewFlag2);
	}
	
	private void setView(){
		linYFMX.setOnClickListener(this);
		linYYFMX.setOnClickListener(this);
		refView.setOnRefreshListener(this, 0x113);
	}
	
	@Override
	public void start(String serviceCode) {
		PDUtil.showPD(this);
	}


	@Override
	public void finish(String serviceCode) {
		PDUtil.hidePD(this);
		refView.finishRefreshing();
		if(serviceCode.equals(TBEA10010000.SERVICE_CODE)){
			if(!req.isHasError() && req.rspInfo.rspSuccess){
				fullData1();
			}else if(!req.isHasError()){
				Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}		
		}
		if(serviceCode.equals(TBEA10020000.SERVICE_CODE)){
			if(!req2.isHasError() && req2.rspInfo.rspSuccess){
				fullData2();
			}else if(!req2.isHasError()){
				Toast.makeText(this, req2.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}		
		}
	}
	
	private void fullData1(){
		txtGSMC.setText(req.TotleInfo.CompanyName);
		txtSJ.setText(DateUtil.formatTime3(new Date()));
		txtYFLJE.setText("￥"+req.TotleInfo.ShouldRebateFee);
		txtYYFLJE.setText("￥"+req.TotleInfo.RebatedFee);
		adapter.notifyDataSetChanged();
	}
	
	private void fullData2(){
		txtGSMC.setText(req2.TotleInfo.CompanyName);
		txtSJ.setText(DateUtil.formatTime3(new Date()));
		txtYFLJE.setText("￥"+req2.TotleInfo.ShouldRebateFee);
		txtYYFLJE.setText("￥"+req2.TotleInfo.RebatedFee);
		adapter.notifyDataSetChanged();
	}
	
	private int type=0;
	@Override
	public void onClick(View v) {
		if(linYFMX==v)toYFMX();
		if(linYYFMX==v)toYYFMX();
	}
	
	private void toYFMX(){
		type=0;
		viewFlag1.setBackgroundColor(Color.parseColor("#0068b7"));
		viewFlag2.setBackgroundColor(Color.parseColor("#00000000"));
		req.execute();
	}
	
	private void toYYFMX(){
		type=1;
		viewFlag2.setBackgroundColor(Color.parseColor("#0068b7"));
		viewFlag1.setBackgroundColor(Color.parseColor("#00000000"));
		req2.execute();
	}
	
	

	class Adapter extends BaseAdapter {
		@Override
		public int getCount() {
			if(type==0)return req.RebatedList.size();
			if(type==1)return req2.ShouldRebateList.size();
			return 0;
		}

		@Override
		public Object getItem(int index) {
			return index;
		}

				
		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View itemView, ViewGroup parent) {
			if(type==0)return getType0View(index,itemView,parent);
			if(type==1)return getType1View(index,itemView,parent);
			return null;
		}
		
		private View getType0View(int index, View itemView, ViewGroup parent){
			if(itemView==null  || !(itemView instanceof ViewFxFlItem3)){
				itemView=new ViewFxFlItem3(ActivityFxFL.this, null);
			}
			RebatedListItem item=req.RebatedList.get(index);
			((ViewFxFlItem3)itemView).fullData(item);
			return itemView;
		}
		
		private View getType1View(int index, View itemView, ViewGroup parent){
			if(itemView==null || !(itemView instanceof ViewFxFlItem1)){
				itemView=new ViewFxFlItem1(ActivityFxFL.this, null);
			}
			ShouldRebateListItem item=req2.ShouldRebateList.get(index);
			((ViewFxFlItem1)itemView).fullData(item);
			return itemView;
		}
	}
	
	@Override
	public void onRefresh() {
		if(type==0)req.execute();
		if(type==1)req2.execute();
	}
}
