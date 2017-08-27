package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class PreOrderProductListItem {
    public String Count;
    public String FloatRatio;
    public String Price;
    public String ProductCode;
    public String ProductId;
    public String ProductName;
    public String Specification;
    public String UnitofMeasurement;

    public static PreOrderProductListItem initByJson(JSONObject jobj) throws JSONException {
        PreOrderProductListItem t = new PreOrderProductListItem();
        t.Count = JsonUtil.getString(jobj, "Count");
        t.FloatRatio = JsonUtil.getString(jobj, "FloatRatio");
        t.Price = JsonUtil.getString(jobj, "Price");
        t.ProductCode = JsonUtil.getString(jobj, "ProductCode");
        t.ProductId = JsonUtil.getString(jobj, "ProductId");
        t.ProductName = JsonUtil.getString(jobj, "ProductName");
        t.Specification = JsonUtil.getString(jobj, "Specification");
        t.UnitofMeasurement = JsonUtil.getString(jobj, "UnitofMeasurement");
        return t;
    }
}
