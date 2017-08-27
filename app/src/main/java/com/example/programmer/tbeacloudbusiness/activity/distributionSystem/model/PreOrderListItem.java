package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class PreOrderListItem {
    public String Id;
    public String OrderCode;
    public String OrderDate;
    public String OrderStatus;
    public String OrderTotleMoney;
    public String CustomerName;
    
	public static PreOrderListItem initByJson(JSONObject jobj) throws JSONException{
		PreOrderListItem t=new PreOrderListItem();
		t.Id= JsonUtil.getString(jobj, "Id");
		t.OrderCode=JsonUtil.getString(jobj, "OrderCode");
		t.OrderDate=JsonUtil.getString(jobj, "OrderDate");
		t.OrderStatus=JsonUtil.getString(jobj, "OrderStatus");
		t.OrderTotleMoney=JsonUtil.getString(jobj, "OrderTotleMoney");
		t.CustomerName=JsonUtil.getString(jobj, "CustomerName");
		return t;
	}
}
