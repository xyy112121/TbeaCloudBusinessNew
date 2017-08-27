package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class PriceTypeItem {
    public String Id;
    public String Name;

    public static PriceTypeItem initByJson(JSONObject jobj) throws JSONException {
        PriceTypeItem t = new PriceTypeItem();
        t.Id = JsonUtil.getString(jobj, "Id");
        t.Name = JsonUtil.getString(jobj, "Name");
        return t;
    }
}
