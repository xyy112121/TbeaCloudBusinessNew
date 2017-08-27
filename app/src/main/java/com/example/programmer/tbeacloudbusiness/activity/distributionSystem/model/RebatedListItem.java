package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class RebatedListItem {
	public String Id;
	public String RebatedName;
	public String RebateFee;
	public String RebateStatus;
	public static RebatedListItem initByJson(JSONObject jobj) throws JSONException{
		RebatedListItem t=new RebatedListItem();
		t.Id= JsonUtil.getString(jobj, "Id");
		t.RebatedName=JsonUtil.getString(jobj, "RebatedName");
		t.RebateFee=JsonUtil.getString(jobj, "RebateFee");
		t.RebateStatus=JsonUtil.getString(jobj, "RebateStatus");
		return t;
	}
}
