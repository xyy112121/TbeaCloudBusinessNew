package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class TotleInfo {
    public String TotleDeliveryMoney;
    public String TotleLeftMoney;
    public String TotleOrderMoney;
    public String TotleReceivedMoney;
    
	public static TotleInfo initByJson(JSONObject jobj) throws JSONException{
		TotleInfo t=new TotleInfo();
		t.TotleDeliveryMoney= JsonUtil.getString(jobj, "TotleDeliveryMoney");
		t.TotleLeftMoney=JsonUtil.getString(jobj, "TotleLeftMoney");
		t.TotleOrderMoney=JsonUtil.getString(jobj, "TotleOrderMoney");
		t.TotleReceivedMoney=JsonUtil.getString(jobj, "TotleReceivedMoney");
		return t;
	}
}
