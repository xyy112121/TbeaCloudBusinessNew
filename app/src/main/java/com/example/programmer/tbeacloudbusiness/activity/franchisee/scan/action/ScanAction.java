package com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.action;

import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scan.model.ScanCodeIndoResponseModel;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by programmer on 2017/7/21.
 */

public class ScanAction extends BaseAction{
    /**
     * 扫码获取信息
     */
    public ResponseInfo getScanCodeInfo(String code) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("qrcode", code));
        pairs.add(new BasicNameValuePair("scanaddress", MyApplication.instance.getAddrsss()));
        String result = sendRequest("TBEAYUN002003001000", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 扫码信息确认
     */
    public ResponseInfo scanCodeAffirm(String takemoneyid) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("takemoneyid", takemoneyid));
        String result = sendRequest("TBEAYUN004002010000", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

}
