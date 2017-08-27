package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class OrderListItem {
    public String CustomerName;
    public String DeliveryMoney;
    public String Id;
    public String LeftMoney;
    public String OrderCode;
    public String OrderDate;
    public String OrderMoney;
    public String ReceivedMoney;
    
	public static OrderListItem initByJson(JSONObject jobj) throws JSONException{
		OrderListItem t=new OrderListItem();
		t.CustomerName= JsonUtil.getString(jobj, "CustomerName");
		t.DeliveryMoney=JsonUtil.getString(jobj, "DeliveryMoney");
		t.Id=JsonUtil.getString(jobj, "Id");
		t.LeftMoney=JsonUtil.getString(jobj, "LeftMoney");
		t.OrderCode=JsonUtil.getString(jobj, "OrderCode");
		t.OrderDate=JsonUtil.getString(jobj, "OrderDate");
		t.OrderMoney=JsonUtil.getString(jobj, "OrderMoney");
		t.ReceivedMoney=JsonUtil.getString(jobj, "ReceivedMoney");
		return t;
	}
}
