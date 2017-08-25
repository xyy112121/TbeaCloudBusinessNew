package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderStatusListItem {
    public String Id;
    public String Name;
    public static OrderStatusListItem initByJson(JSONObject jobj) throws JSONException {
        OrderStatusListItem t=new OrderStatusListItem();
        t.Id= JsonUtil.getString(jobj, "Id");
        t.Name=JsonUtil.getString(jobj, "Name");
        return t;
    }
}