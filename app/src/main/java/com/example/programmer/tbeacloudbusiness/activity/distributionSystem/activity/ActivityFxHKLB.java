package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA08010000;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ViewFxHklbItem;

import java.util.ArrayList;

public class ActivityFxHKLB extends Activity implements AsyncListener,RefreshableView.PullToRefreshListener,OnClickListener,OnItemClickListener {
	
	private TextView txtDHZE;
	private TextView txtYFHJE;
	private TextView txtHKZJE;
	private TextView txtYSKYE;
	private ImageButton butSearch;
	private ListView lstContent;
	private RefreshableView refView;
	

	private TBEA08010000 req;
	private Adapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.instance.addActivity(this);
		setContentView(R.layout.activity_fx_hklb);
		init();
		setView();
		adapter=new Adapter();
		req=new TBEA08010000(this);
		lstContent.setAdapter(adapter);
		lstContent.setOnItemClickListener(this);
		req.execute();
	}
	
	
	
	private void init(){
		txtDHZE=(TextView)findViewById(R.id.txtDHZE);
		txtYFHJE=(TextView)findViewById(R.id.txtYFHJE);
		txtHKZJE=(TextView)findViewById(R.id.txtHKZJE);
		txtYSKYE=(TextView)findViewById(R.id.txtYSKYE);
		
		butSearch=(ImageButton)findViewById(R.id.butSearch);
		lstContent=(ListView)findViewById(R.id.lstContent);
		refView=(RefreshableView)findViewById(R.id.refView);		
	}
	
	private void setView(){
		butSearch.setOnClickListener(this);
		butSearch.setVisibility(View.INVISIBLE);
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
		if(serviceCode.equals(TBEA08010000.SERVICE_CODE)){
			if(!req.isHasError() && req.rspInfo.rspSuccess){
				fullData();
			}else if(!req.isHasError()){
				Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}		
		}
	}
	
	@Override
	public void onClick(View v) {
		if(butSearch==v)toSearch();
	}
	
	private void toSearch(){
		//Intent intent=new Intent(this, ActivityFxHKSS.class);
		//startActivity(intent);
	}
	

	private void fullData(){
		data.addAll(req.OrderList);
		adapter.notifyDataSetChanged();
		fullInfo();
	}
	
	private void fullInfo(){
		txtDHZE.setText("￥"+req.totleInfo.TotleOrderMoney);
		txtYFHJE.setText("￥"+req.totleInfo.TotleDeliveryMoney);
		txtHKZJE.setText("￥"+req.totleInfo.TotleReceivedMoney);
		txtYSKYE.setText("￥"+req.totleInfo.TotleLeftMoney);
	}



	private ArrayList<OrderListItem> data=new ArrayList<OrderListItem>();
	
	class Adapter extends BaseAdapter {
		@Override
		public int getCount() {
			return data.size();
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
			if(itemView==null){
				itemView=new ViewFxHklbItem(ActivityFxHKLB.this, null);
			}
			OrderListItem item=data.get(index);
			((ViewFxHklbItem)itemView).fullData(item);
			return itemView;
		}
	}
	
	@Override
	public void onRefresh() {
		data.clear();
		adapter.notifyDataSetInvalidated();
		req.execute();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		OrderListItem item=data.get(position);
		Intent intent = new Intent(ActivityFxHKLB.this,ActivityFxHKMX.class);
		intent.putExtra("id", item.Id);
		startActivity(intent);
	}
}
