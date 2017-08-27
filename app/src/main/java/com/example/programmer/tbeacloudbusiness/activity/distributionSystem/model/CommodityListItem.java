package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class CommodityListItem {
	public String Id;
	public String CommodityCode;
	public String CommodityName;
	public String UnitofMeasurement;
	public String TotleNumber;
	public String Price;
	public String DeliveryNumber;
	
	public static CommodityListItem initByJson(JSONObject jobj) throws JSONException{
		CommodityListItem t=new CommodityListItem();
		t.Id= JsonUtil.getString(jobj, "Id");
		t.CommodityCode=JsonUtil.getString(jobj, "CommodityCode");
		t.CommodityName=JsonUtil.getString(jobj, "CommodityName");
		t.UnitofMeasurement=JsonUtil.getString(jobj, "UnitofMeasurement");
		t.TotleNumber=JsonUtil.getString(jobj, "TotleNumber");
		t.Price=JsonUtil.getString(jobj, "Price");
		t.Price=JsonUtil.getString(jobj, "Price");
		t.DeliveryNumber=JsonUtil.getString(jobj, "DeliveryNumber");
		return t;
	}
}
