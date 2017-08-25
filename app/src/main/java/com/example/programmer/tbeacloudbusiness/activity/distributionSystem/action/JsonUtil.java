package com.example.programmer.tbeacloudbusiness.activity.distributionSystem.action;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	public static boolean hasArray(JSONObject jobj, String key) throws JSONException {
		return jobj.has(key) && !jobj.isNull(key) && jobj.get(key) instanceof JSONArray;
	}
	
	public static String getString(JSONObject jobj, String key) throws JSONException {
		if(jobj.has(key) && (!jobj.isNull(key)))return jobj.getString(key);
		return null;
	}
}
