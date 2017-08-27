package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class ContactPersonItem {
    public String EmailAddress;
    public String ID;
    public String JobTitle;
    public String MobileNumber;
    public String Name;
    
	public static ContactPersonItem initByJson(JSONObject jobj) throws JSONException{
		ContactPersonItem t=new ContactPersonItem();
		t.EmailAddress= JsonUtil.getString(jobj, "EmailAddress");
		t.ID=JsonUtil.getString(jobj, "ID");
		t.JobTitle=JsonUtil.getString(jobj, "JobTitle");
		t.MobileNumber=JsonUtil.getString(jobj, "MobileNumber");
		t.Name=JsonUtil.getString(jobj, "Name");
		return t;
	}
    
}
