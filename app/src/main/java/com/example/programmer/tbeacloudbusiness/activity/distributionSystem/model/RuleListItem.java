package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class RuleListItem {
    public String Attachment;
    public String Date;
    public String DocumentSize;
    public String DocumentTypeIcon;
    public String ID;
    public String Name;
    public String RuleCategory;
    
	public static RuleListItem initByJson(JSONObject jobj) throws JSONException{
		RuleListItem t=new RuleListItem();
		t.Attachment=JsonUtil.getString(jobj, "Attachment");
		t.Date= JsonUtil.getString(jobj, "Date");
		t.DocumentSize=JsonUtil.getString(jobj, "DocumentSize");
		t.DocumentTypeIcon=JsonUtil.getString(jobj, "DocumentTypeIcon");
		t.ID=JsonUtil.getString(jobj, "ID");
		t.Name=JsonUtil.getString(jobj, "Name");
		t.RuleCategory=JsonUtil.getString(jobj, "RuleCategory");
		return t;
	}
}
