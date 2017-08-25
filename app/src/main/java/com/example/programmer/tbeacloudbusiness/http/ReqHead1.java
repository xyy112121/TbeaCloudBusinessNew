package com.example.programmer.tbeacloudbusiness.http;


import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.main.action.MyAction;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

//用于分销系统
public class ReqHead1 {
	protected String origDomain;
	protected String protocolVar;
	protected String appVersion;
	protected String userId;
	protected String serviceCode;
	protected String actionCode;
	protected String cityId;
	protected String longitude;
	protected String latitude;
	protected String provinceName;
	protected String cityName;
	protected String tbeaBranchId;

	public ReqHead1(){
		origDomain="Android";
		protocolVar="tbea_v1";
		appVersion="V_1.0";
		userId= MyApplication.instance.getUserId();
		serviceCode="";
		actionCode="0";	
		cityId="";
		longitude=MyApplication.instance.getLongitude()+"";
		latitude=MyApplication.instance.getLatitude()+"";
		provinceName=MyApplication.instance.getProvince();
		cityName=MyApplication.instance.getCity();
		tbeaBranchId = "tbeadelan";
	}
	
	public String getOrigDomain() {
		return origDomain;
	}
	public void setOrigDomain(String origDomain) {
		this.origDomain = origDomain;
	}
	public String getProtocolVar() {
		return protocolVar;
	}
	public void setProtocolVar(String protocolVar) {
		this.protocolVar = protocolVar;
	}

	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	
	public String getCityId() {
		return cityId;
	}

	public void setCityName(String cityId) {
		this.cityId = cityId;
	}


	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public Header[] getHeaders(){
		Header[] re=new BasicHeader[12];
		re[0]=new BasicHeader("OrigDomain", origDomain);
		re[1]=new BasicHeader("ProtocolVer", protocolVar);
		re[2]=new BasicHeader("AppVersion", appVersion);
		re[3]=new BasicHeader("UserId", userId);
		re[4]=new BasicHeader("ServiceCode", serviceCode);
		re[5]=new BasicHeader("ActionCode", actionCode);
		re[6]=new BasicHeader("CityId", cityId);
		re[7]=new BasicHeader("Longitude", longitude);
		re[8]=new BasicHeader("Latitude", latitude);
		re[9]=new BasicHeader("ProvinceName", provinceName);
		re[10]=new BasicHeader("CityName", cityName);
		re[11]=new BasicHeader("TbeaBranchId", tbeaBranchId);
		return re;
	}
}
