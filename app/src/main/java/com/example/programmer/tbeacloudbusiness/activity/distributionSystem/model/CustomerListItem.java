package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class CustomerListItem {
	public String CustomerId;
	public String CustomerCode;
	public String CustomerName;
	public String CustomerShortName;
	public String CustomerStatus;
	
	public static CustomerListItem initByJson(JSONObject jobj) throws JSONException{
		CustomerListItem t=new CustomerListItem();
		t.CustomerId= JsonUtil.getString(jobj, "CustomerId");
		t.CustomerCode=JsonUtil.getString(jobj, "CustomerCode");
		t.CustomerName=JsonUtil.getString(jobj, "CustomerName");
		t.CustomerShortName=JsonUtil.getString(jobj, "CustomerShortName");
		t.CustomerStatus=JsonUtil.getString(jobj, "CustomerStatus");
		return t;
	}
}
