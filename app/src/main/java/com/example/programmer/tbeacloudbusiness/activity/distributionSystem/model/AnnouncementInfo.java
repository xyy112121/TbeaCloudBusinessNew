package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class AnnouncementInfo {
    public String Author;
    public String Code;
    public String Content;
    public String EndDate;
    public String ID;
    public String Name;
    public String PublishDate;
    public static AnnouncementInfo initByJson(JSONObject jobj) throws JSONException {
        AnnouncementInfo t=new AnnouncementInfo();
        t.Author= JsonUtil.getString(jobj,"Author");
        t.Code=JsonUtil.getString(jobj,"Code");
        t.Content=JsonUtil.getString(jobj,"Content");
        t.EndDate=JsonUtil.getString(jobj,"EndDate");
        t.ID=JsonUtil.getString(jobj,"ID");
        t.Name=JsonUtil.getString(jobj,"Name");
        t.PublishDate=JsonUtil.getString(jobj,"PublishDate");
        return t;
    }
}
