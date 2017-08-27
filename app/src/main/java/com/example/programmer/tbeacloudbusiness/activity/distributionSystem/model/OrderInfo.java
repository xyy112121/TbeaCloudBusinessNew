package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class OrderInfo {
    public String AllDeliveryDate;
    public String CustomerName;
    public double DeliveryMoney;
    public String Id;
    public String OrderCode;
    public String OrderDate;
    public double OrderMoney;
    public String OrderStatus;
    public int OrderStatusId;
    
	public static OrderInfo initByJson(JSONObject jobj) throws JSONException{
		OrderInfo t=new OrderInfo();
		t.AllDeliveryDate=JsonUtil.getString(jobj, "AllDeliveryDate");
		t.CustomerName= JsonUtil.getString(jobj, "CustomerName");
		t.DeliveryMoney=jobj.getDouble("DeliveryMoney");
		t.Id=JsonUtil.getString(jobj, "Id");
		t.OrderCode=JsonUtil.getString(jobj, "OrderCode");
		t.OrderDate=JsonUtil.getString(jobj, "OrderDate");
		t.OrderMoney=jobj.getDouble("OrderMoney");
		t.OrderStatus=JsonUtil.getString(jobj, "OrderStatus");
		t.OrderStatusId=jobj.getInt("OrderStatusId");
		return t;
	}
}
