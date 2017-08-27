package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class RuleCategoryItem {
    public String ID;
    public String Name;
    
	public static RuleCategoryItem initByJson(JSONObject jobj) throws JSONException{
		RuleCategoryItem t=new RuleCategoryItem();
		t.ID= JsonUtil.getString(jobj, "ID");
		t.Name=JsonUtil.getString(jobj, "Name");
		return t;
	}
}
