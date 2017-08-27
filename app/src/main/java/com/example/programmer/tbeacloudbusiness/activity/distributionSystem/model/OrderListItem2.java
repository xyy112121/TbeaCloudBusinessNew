package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class OrderListItem2 {
	public String Id;
	public String OrderCode;
	public String CustomerName;
	public String OrderDate;
	public String OrderMoney;
	public String DeliveryMoney;
	public String AllDeliveryDate;
	public String OrderStatusId;
	public String OrderStatus;
    
	public static OrderListItem2 initByJson(JSONObject jobj) throws JSONException{
		OrderListItem2 t=new OrderListItem2();
		t.Id= JsonUtil.getString(jobj, "Id");
		t.OrderCode=JsonUtil.getString(jobj, "OrderCode");
		t.CustomerName=JsonUtil.getString(jobj, "CustomerName");
		t.OrderDate=JsonUtil.getString(jobj, "OrderDate");
		t.OrderMoney=JsonUtil.getString(jobj, "OrderMoney");
		t.DeliveryMoney=JsonUtil.getString(jobj, "DeliveryMoney");
		t.AllDeliveryDate=JsonUtil.getString(jobj, "AllDeliveryDate");
		t.OrderStatusId=JsonUtil.getString(jobj, "OrderStatusId");
		t.OrderStatus=JsonUtil.getString(jobj, "OrderStatus");

		return t;
	}
}
