package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class ProductListItem {
    public String ProductCode;
    public String ProductDescription;
    public String ProductId;
    public String ProductName;
    public String UnitofMeasurement;

    public static ProductListItem initByJson(JSONObject jobj) throws JSONException {
        ProductListItem t = new ProductListItem();
        t.ProductCode = JsonUtil.getString(jobj, "ProductCode");
        t.ProductDescription = JsonUtil.getString(jobj, "ProductDescription");
        t.ProductId = JsonUtil.getString(jobj, "ProductId");
        t.ProductName = JsonUtil.getString(jobj, "ProductName");
        t.UnitofMeasurement = JsonUtil.getString(jobj, "UnitofMeasurement");
        return t;
    }
}
