package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.bumptech.glide.util.Util;
import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplainItem {
    public String ComplainStatus;
    public String ContactMobile;
    public String Content;
    public String Date;
    public String ID;
    //public ReplyList;

    public static ComplainItem initByJson(JSONObject jobj) throws JSONException {
        ComplainItem t = new ComplainItem();
        t.ComplainStatus = JsonUtil.getString(jobj, "ComplainStatus");
        t.ContactMobile = JsonUtil.getString(jobj, "ContactMobile");
        t.Content = JsonUtil.getString(jobj, "Content");
        t.Date = JsonUtil.getString(jobj, "Date");
        t.ID = JsonUtil.getString(jobj, "ID");
        return t;
    }
}
