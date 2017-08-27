package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ReferencePriceItem {
    public String CommodityCode;
    public String CommodityId;
    public String CommodityName;
    public String ReferencePrice;
    public String UnitofMeasurement;

    public static ReferencePriceItem initByJson(JSONObject jobj) throws JSONException {
        ReferencePriceItem t = new ReferencePriceItem();
        t.CommodityCode = JsonUtil.getString(jobj, "CommodityCode");
        t.CommodityId = JsonUtil.getString(jobj, "CommodityId");
        t.CommodityName = JsonUtil.getString(jobj, "CommodityName");
        t.ReferencePrice = jobj.getString("ReferencePrice");
        t.UnitofMeasurement = jobj.getString("UnitofMeasurement");
        return t;
    }

}
