package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class PreOrderInfo {
    public String CustomerCode;
    public String CustomerName;
    public String DeliverDate;
    public String OrderNote;
    public String OrderTotleMoney;
    public String CanModify;
    public String PriceType;
    public String PriceTypeName;

    public static PreOrderInfo initByJson(JSONObject jobj) throws JSONException {
        PreOrderInfo t = new PreOrderInfo();
        t.CustomerCode = JsonUtil.getString(jobj, "CustomerCode");
        t.CustomerName = JsonUtil.getString(jobj, "CustomerName");
        t.DeliverDate = JsonUtil.getString(jobj, "DeliverDate");
        t.OrderNote = JsonUtil.getString(jobj, "OrderNote");
        t.OrderTotleMoney = JsonUtil.getString(jobj, "OrderTotleMoney");
        t.CanModify = JsonUtil.getString(jobj, "CanModify");
        t.PriceType = JsonUtil.getString(jobj, "PriceType");
        t.PriceTypeName = JsonUtil.getString(jobj, "PriceTypeName");
        return t;
    }
}
