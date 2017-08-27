package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.model;

import com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class TotleInfo2 {
    public String CompanyName;
    public String ShouldRebateFee;
    public String RebatedFee;

    public static TotleInfo2 initByJson(JSONObject jobj) throws JSONException {
        TotleInfo2 t = new TotleInfo2();
        t.CompanyName = JsonUtil.getString(jobj, "CompanyName");
        t.ShouldRebateFee = JsonUtil.getString(jobj, "ShouldRebateFee");
        t.RebatedFee = JsonUtil.getString(jobj, "RebatedFee");
        return t;
    }
}
