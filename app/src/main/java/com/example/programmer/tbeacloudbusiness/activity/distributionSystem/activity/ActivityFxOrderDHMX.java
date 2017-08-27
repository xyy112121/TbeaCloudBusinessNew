package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.programmer.tbeacloudbusiness.R;
import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.AsyncListener;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.TBEA07020100;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.CommodityListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ActivityFxOrderDHMXView1;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.ActivityFxOrderDHMXView3;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.PDUtil;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.view.RefreshableView;

import java.util.ArrayList;

public class ActivityFxOrderDHMX extends Activity implements AsyncListener,OnScrollListener,RefreshableView.PullToRefreshListener {
	
	private ListView lstContent;
	private RefreshableView refView;
	
	private TBEA07020100 req;
	private String id;
	
	private Adapter adapter;
	private ActivityFxOrderDHMXView1 head;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		id=getIntent().getStringExtra("id");
		MyApplication.instance.addActivity(this);
		setContentView(R.layout.activity_fx_order_dhmx);
		init();
		adapter=new Adapter();
        lstContent.setAdapter(adapter);
        lstContent.setOnScrollListener(this);
        head=new ActivityFxOrderDHMXView1(this,null);
        lstContent.addHeaderView(head);
		req=new TBEA07020100(this);
		req.Page=page;
		req.PageSize=14;
		req.OrderId=id;
		req.execute();
	}
	
	
	
	private void init(){
		lstContent=(ListView)findViewById(R.id.lstContent);
        refView=(RefreshableView)findViewById(R.id.refView);
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
		if(serviceCode.equals(TBEA07020100.SERVICE_CODE)){
			nexting=false;
			if(!req.isHasError() && req.rspInfo.rspSuccess){
				if(req.CommodityList.size()<req.PageSize)isEnd=true;
				fullData();
			}else if(!req.isHasError()){
				Toast.makeText(this, req.rspInfo.RspDesc, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
			}		
		}
	}
	
	private void fullData(){
		head.fullData(req.orderInfo);
		data.addAll(req.CommodityList);
		adapter.notifyDataSetChanged();
	}
	
	private boolean nexting;
	private boolean isEnd;
	private int lastViewItemIndex;
	private int page=1;
	
	public void nextPage(){
		if(nexting)return;
    	page++;
    	req.Page=page;
    	nexting=true;
    	req.execute();
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        lastViewItemIndex = firstVisibleItem+visibleItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            int lastItemsIndex = adapter.getCount();
            if(!isEnd && lastItemsIndex==lastViewItemIndex){
            	nextPage();
            } 
        }  
	}
	
	
	private ArrayList<CommodityListItem> data=new ArrayList<CommodityListItem>();
	
	
	
	
	
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
				itemView=new ActivityFxOrderDHMXView3(ActivityFxOrderDHMX.this, null);
			}
			CommodityListItem item=data.get(index);
			((ActivityFxOrderDHMXView3)itemView).fullData(item);
			return itemView;
		}
	}
	

	@Override
	public void onRefresh() {
		data.clear();
		adapter.notifyDataSetInvalidated();
		isEnd=false;
		page=1;
		req.Page=page;
		req.execute();

	}
	
}
