package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class AnnouncementListItem {
    public String Author;
    public String Code;
    public String Description;
    public String EndDate;
    public String ID;
    public String Name;
    public String PublishDate;
    
	public static AnnouncementListItem initByJson(JSONObject jobj) throws JSONException{
		AnnouncementListItem t=new AnnouncementListItem();
		t.Author= JsonUtil.getString(jobj,"Author");
		t.Code=JsonUtil.getString(jobj,"Code");
		t.Description=JsonUtil.getString(jobj,"Description");
		t.EndDate=JsonUtil.getString(jobj,"EndDate");
		t.ID=JsonUtil.getString(jobj,"ID");
		t.Name=JsonUtil.getString(jobj,"Name");
		t.PublishDate=JsonUtil.getString(jobj,"PublishDate");
		return t;
	}
}
