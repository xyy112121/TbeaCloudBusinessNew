package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class ShouldRebateListItem {
	public String Id;
	public String OrderCode;
	public String OrderMoney;
	public String RebateFee;
	public String RebateStatus;
	public static ShouldRebateListItem initByJson(JSONObject jobj) throws JSONException{
		ShouldRebateListItem t=new ShouldRebateListItem();
		t.Id= JsonUtil.getString(jobj, "Id");
		t.OrderCode=JsonUtil.getString(jobj, "OrderCode");
		t.OrderMoney=JsonUtil.getString(jobj, "OrderMoney");
		t.RebateFee=JsonUtil.getString(jobj, "RebateFee");
		t.RebateStatus=JsonUtil.getString(jobj, "RebateStatus");
		return t;
	}
}
