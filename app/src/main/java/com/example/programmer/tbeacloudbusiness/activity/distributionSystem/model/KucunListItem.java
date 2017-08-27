package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class KucunListItem {
	public String CommodityId;
	public String CommodityCode;
	public String CommodityName;
	public String UnitofMeasurement;
	public String InventoryNumber;
	public static KucunListItem initByJson(JSONObject jobj) throws JSONException{
		KucunListItem t=new KucunListItem();
		t.CommodityId= JsonUtil.getString(jobj, "CommodityId");
		t.CommodityCode=JsonUtil.getString(jobj, "CommodityCode");
		t.CommodityName=JsonUtil.getString(jobj, "CommodityName");
		t.UnitofMeasurement=JsonUtil.getString(jobj, "UnitofMeasurement");
		t.InventoryNumber=JsonUtil.getString(jobj, "InventoryNumber");
		return t;
	}
}
