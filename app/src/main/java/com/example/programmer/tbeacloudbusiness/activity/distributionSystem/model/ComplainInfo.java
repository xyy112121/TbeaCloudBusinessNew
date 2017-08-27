package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class ComplainInfo {
    public String ComplainStatus;
    public String ContactMobile;
    public String Content;
    public String Date;
    public String ID;
    //public ReplyList;

    public static ComplainInfo initByJson(JSONObject jobj) throws JSONException {
        ComplainInfo t = new ComplainInfo();
        t.ComplainStatus = JsonUtil.getString(jobj, "ComplainStatus");
        t.ContactMobile = JsonUtil.getString(jobj, "ContactMobile");
        t.Content = JsonUtil.getString(jobj, "Content");
        t.Date = JsonUtil.getString(jobj, "Date");
        t.ID = JsonUtil.getString(jobj, "ID");
        return t;
    }
}
