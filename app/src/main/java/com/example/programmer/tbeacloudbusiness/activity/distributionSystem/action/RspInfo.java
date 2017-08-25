package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action;

import org.json.JSONException;
import org.json.JSONObject;

public class RspInfo {
    public String RspCod;
    public String RspDesc;
    public boolean rspSuccess;

    public static RspInfo initByJson(JSONObject jobj) throws JSONException {
        RspInfo t = new RspInfo();
        if (jobj.has("RspCode")) t.RspCod = jobj.getString("RspCode");
        if (jobj.has("RspDesc")) t.RspDesc = jobj.getString("RspDesc");
        t.rspSuccess = t.RspCod == null ? true : t.RspCod.equals("RC00000");
        return t;
    }
}
