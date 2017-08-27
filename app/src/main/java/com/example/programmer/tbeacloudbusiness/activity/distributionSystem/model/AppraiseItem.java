package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class AppraiseItem {
    public String Id;
    public String PersonName;
    public String PersonPicture;
    public String AppraiseTime;
    public String AppraiseLevelNumber;
    public String Appraise;

    public static AppraiseItem initByJson(JSONObject jobj) throws JSONException {
        AppraiseItem t = new AppraiseItem();
        t.Id = JsonUtil.getString(jobj, "Id");
        t.PersonName = JsonUtil.getString(jobj, "PersonName");
        t.PersonPicture = JsonUtil.getString(jobj, "PersonPicture");
        t.AppraiseTime = JsonUtil.getString(jobj, "AppraiseTime");
        t.AppraiseLevelNumber = JsonUtil.getString(jobj, "AppraiseLevelNumber");
        t.Appraise = JsonUtil.getString(jobj, "Appraise");
        return t;
    }
}
