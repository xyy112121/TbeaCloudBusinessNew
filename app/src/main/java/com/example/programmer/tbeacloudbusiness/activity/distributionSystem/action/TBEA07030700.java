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

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model.ProductListItem;
import com.example.programmer.tbeacloudbusiness.http.ReqBase1;
import com.example.programmer.tbeacloudbusiness.http.ReqHead1;


public class TBEA07030700 {
    public static final String SERVICE_CODE = "TBEA07030700";
    private final String TAG = getClass().getName();
    private WeakReference<AsyncListener> asynListener;
    private boolean hasError;
    private String errorMsg;


    public RspInfo rspInfo;
    public ArrayList<ProductListItem> ProductList = new ArrayList<ProductListItem>();

    public String ProductCode = "";
    public String ProductName = "";
    public String ProductDescription = "";
    public int Page;
    public int PageSize;


    public boolean isHasError() {
        return hasError;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public TBEA07030700(AsyncListener asynListener) {
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
            rspInfo = null;
            ProductList.clear();
            if (asynListener.get() != null) asynListener.get().start(SERVICE_CODE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ReqHead1 rh = new ReqHead1();
            rh.setServiceCode(SERVICE_CODE);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("ProductCode", String.valueOf(ProductCode)));
            pairs.add(new BasicNameValuePair("ProductName", String.valueOf(ProductName)));
            pairs.add(new BasicNameValuePair("ProductDescription", String.valueOf(ProductDescription)));

            pairs.add(new BasicNameValuePair("Page", String.valueOf(Page)));
            pairs.add(new BasicNameValuePair("PageSize", String.valueOf(PageSize)));
            ReqBase1 req = new ReqBase1(rh, pairs);
            try {
                req.req();
                String rspContext = req.getRspContext();
                JSONObject jobj = new JSONObject(rspContext);
                rspInfo = RspInfo.initByJson(jobj);
                if (JsonUtil.hasArray(jobj, "ProductList")) {
                    JSONArray jarray = jobj.getJSONArray("ProductList");
                    for (int i = 0; i < jarray.length(); i++) {
                        ProductList.add(ProductListItem.initByJson(jarray.getJSONObject(i)));
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
            if (asynListener.get() != null) asynListener.get().finish(SERVICE_CODE);
        }
    }

}
