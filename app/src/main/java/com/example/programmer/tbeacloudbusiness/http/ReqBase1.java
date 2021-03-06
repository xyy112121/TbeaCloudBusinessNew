package com.example.programmer.tbeacloudbusiness.http;

import android.util.Log;

import com.example.programmer.tbeacloudbusiness.activity.MyApplication;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

//用于分销系统
public class ReqBase1 {
	private static final String TAG="ReqBase";
	protected ReqHead1 reqHead;
	private List<NameValuePair> params;
	protected HttpResponse rsp;


	public ReqBase1(ReqHead1 reqHead, List<NameValuePair> params){
		this.reqHead=reqHead;
		this.params=params;
	}
	
	public void req() throws  IOException, JSONException {
		String url = MyApplication.instance.getServicePath1();
		HttpPost request=new HttpPost(url);
		Header[] headers=reqHead.getHeaders();
		Log.d(TAG, "Requery Hearders:");
		for(Header header:headers){
			String value=header.getValue();
			if(value==null){
				Log.d(TAG, header.getName()+":NULL");
			}else{
				Log.d(TAG, header.getName()+":"+value);
			}
		}
		if(params != null){
			for(NameValuePair param:params){
				String value=param.getValue();
				if(value==null){
					Log.d(TAG, param.getName()+":NULL");
				}else{
					Log.d(TAG, param.getName()+":"+value);
				}
			}
		}

		request.setHeaders(headers);
		UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params, HTTP.UTF_8);
		request.setEntity(entity);
		DefaultHttpClient client=new DefaultHttpClient();
        HttpParams params = client.getParams();
        params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 20*1000);
        HttpConnectionParams.setSoTimeout(params, 20*1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		HttpClientParams.setRedirecting(params, true);
		rsp = client.execute(request);
	}
	
	public String getRspContext() throws ParseException, IOException {
		String context= EntityUtils.toString(rsp.getEntity());
		Log.d(TAG, "CONTEXT:"+context);
		return context;
	}
	
	public String getHeaderValue(String key){
		Header[] headers=rsp.getHeaders(key);
		if(headers!=null && headers.length>0)return rsp.getHeaders(key)[0].getValue();
		return null;
	}
	
}
