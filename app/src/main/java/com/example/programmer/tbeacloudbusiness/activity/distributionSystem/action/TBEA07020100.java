package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.CommodityListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.OrderInfo;
import com.example.programmer.tbeacloudbusiness.http.ReqBase1;
import com.example.programmer.tbeacloudbusiness.http.ReqHead1;


public class TBEA07020100 {
	public static final String SERVICE_CODE = "TBEA07020100";
	private final String TAG = getClass().getName();
	private WeakReference<AsyncListener> asynListener;
	private boolean hasError;
	private String errorMsg;


	public RspInfo rspInfo;
	public OrderInfo orderInfo;
	public ArrayList<CommodityListItem> CommodityList=new ArrayList<CommodityListItem>();
	
	public String OrderId="";
	public int Page;
	public int PageSize;
	
	public boolean isHasError() {
		return hasError;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public TBEA07020100(AsyncListener asynListener) {
		this.asynListener = new WeakReference<>(asynListener);
	}

	public void execute() {
		MeAsyncTask task = new MeAsyncTask();
		task.execute();
	}


	class MeAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			hasError = false;
			errorMsg = null;
			rspInfo=null;
			CommodityList.clear();
			if(asynListener.get()!=null)asynListener.get().start(SERVICE_CODE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			ReqHead1 rh = new ReqHead1();
			rh.setServiceCode(SERVICE_CODE);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("OrderId", String.valueOf(OrderId)));
			
			pairs.add(new BasicNameValuePair("Page", String.valueOf(Page)));
			pairs.add(new BasicNameValuePair("PageSize", String.valueOf(PageSize)));
			ReqBase1 req = new ReqBase1(rh, pairs);
			try {
				req.req();
				String rspContext = req.getRspContext();
				JSONObject jobj = new JSONObject(rspContext);
				rspInfo=RspInfo.initByJson(jobj);
				orderInfo=OrderInfo.initByJson(jobj.getJSONObject("OrderInfo"));
				if(JsonUtil.hasArray(jobj,"CommodityList")){
					JSONArray jarray=jobj.getJSONArray("CommodityList");
					for(int i=0;i<jarray.length();i++){
						CommodityList.add(CommodityListItem.initByJson(jarray.getJSONObject(i)));
					}
				}
			} catch (Exception e) {
				hasError = true;
				errorMsg = e.getMessage();
				Log.e(TAG, e.getMessage(), e);
			} 
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if(asynListener.get()!=null)asynListener.get().finish(SERVICE_CODE);
		}
	}

}
