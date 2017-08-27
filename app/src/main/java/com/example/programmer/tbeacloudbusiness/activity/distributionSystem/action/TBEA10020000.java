package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action;

import android.os.AsyncTask;
import android.util.Log;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ShouldRebateListItem;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.TotleInfo2;
import com.example.programmer.tbeacloudbusiness.http.ReqBase1;
import com.example.programmer.tbeacloudbusiness.http.ReqHead1;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class TBEA10020000 {
    public static final String SERVICE_CODE = "TBEA10020000";
    private final String TAG = getClass().getName();
    private WeakReference<AsyncListener> asynListener;
    private boolean hasError;
    private String errorMsg;

    public RspInfo rspInfo;
    public TotleInfo2 TotleInfo;
    public ArrayList<ShouldRebateListItem> ShouldRebateList=new ArrayList<ShouldRebateListItem>();



    public boolean isHasError() {
        return hasError;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public TBEA10020000(AsyncListener asynListener) {
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
            TotleInfo=null;
            ShouldRebateList.clear();
            if(asynListener.get()!=null)asynListener.get().start(SERVICE_CODE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ReqHead1 rh = new ReqHead1();
            rh.setServiceCode(SERVICE_CODE);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            ReqBase1 req = new ReqBase1(rh, pairs);
            try {
                req.req();
                String rspContext = req.getRspContext();
                JSONObject jobj = new JSONObject(rspContext);
                rspInfo=RspInfo.initByJson(jobj);
                TotleInfo= TotleInfo2.initByJson(jobj.getJSONObject("TotleInfo"));
                if(JsonUtil.hasArray(jobj,"ShouldRebateList")){
                    JSONArray jarray=jobj.getJSONArray("ShouldRebateList");
                    for(int i=0;i<jarray.length();i++){
                        ShouldRebateList.add(ShouldRebateListItem.initByJson(jarray.getJSONObject(i)));
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
