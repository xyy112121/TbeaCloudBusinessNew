package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class TransitionListItem {
	public String TransitionId;
	public String TransitionCode;
	public String TransitionCarCode;
	public String TransitionCompany;
	public String TransitionDate;
	
	public static TransitionListItem initByJson(JSONObject jobj) throws JSONException{
		TransitionListItem t=new TransitionListItem();
		t.TransitionId= JsonUtil.getString(jobj, "TransitionId");
		t.TransitionCode=JsonUtil.getString(jobj, "TransitionCode");
		t.TransitionCarCode=JsonUtil.getString(jobj, "TransitionCarCode");
		t.TransitionCompany=JsonUtil.getString(jobj, "TransitionCompany");
		t.TransitionDate=JsonUtil.getString(jobj, "TransitionDate");
		return t;
	}
}
