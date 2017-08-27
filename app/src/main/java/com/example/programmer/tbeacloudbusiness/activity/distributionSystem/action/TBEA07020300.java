package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action;

import android.os.AsyncTask;
import android.util.Log;

import com.example.programmer.tbeacloudbusiness.http.ReqBase1;
import com.example.programmer.tbeacloudbusiness.http.ReqHead1;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class TBEA07020300 {
	public static final String SERVICE_CODE = "TBEA07020300";
	private final String TAG = getClass().getName();
	private WeakReference<AsyncListener> asynListener;
	private boolean hasError;
	private String errorMsg;
	
	public RspInfo rspInfo;

	public String TransitionCode;
	public String TransitionCarCode;
	
	public boolean isHasError() {
		return hasError;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public TBEA07020300(AsyncListener asynListener) {
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
			if(asynListener.get()!=null)asynListener.get().start(SERVICE_CODE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			ReqHead1 rh = new ReqHead1();
			rh.setServiceCode(SERVICE_CODE);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("TransitionCode", TransitionCode));
			pairs.add(new BasicNameValuePair("TransitionCarCode", TransitionCarCode));
			ReqBase1 req = new ReqBase1(rh, pairs);
			try {
				req.req();
				String rspContext = req.getRspContext();
				JSONObject jobj = new JSONObject(rspContext);
				rspInfo=RspInfo.initByJson(jobj);
				if(rspInfo.rspSuccess){
					
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
