package com.example.programmer.tbeacloudbusiness.activity.my.set.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddrListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddressModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.SetResponseModel;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by programmer on 2017/8/16.
 */

public class SetAction extends BaseAction {
    /**
     * 获取设置信息
     */
    public SetResponseModel getSetInfo() throws Exception {
        SetResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN011001001001", pairs);
        model = gson.fromJson(result, SetResponseModel.class);
        return model;
    }

    /**
     * 获取收货地址
     */
    public AddrListResponseModel getAddrList() throws Exception {
        AddrListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN011001002001", pairs);
        model = gson.fromJson(result, AddrListResponseModel.class);
        return model;
    }

    /**
     * 添加收货地址
     1 RecvAddrId   收货地址 Id 不为空则为修改 为空为增加 2 ContactPerson   联系人  3 ContactMobile   联系电话  4 Province   所在省份名  5 City   所在城市名  6 Zone   所在区域名  7 Address   收货地址  8 IsDefault   是否默认
     */
    public BaseResponseModel editAddrss(AddressModel obj) throws Exception {
        BaseResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("recvaddrid", obj.recvaddrid));
        pairs.add(new BasicNameValuePair("contactperson", obj.contactperson));
        pairs.add(new BasicNameValuePair("contactmobile", obj.contactmobile));
        pairs.add(new BasicNameValuePair("address", obj.address));
        pairs.add(new BasicNameValuePair("isdefault", obj.isdefault));
        pairs.add(new BasicNameValuePair("province", obj.province));
        pairs.add(new BasicNameValuePair("city", obj.city));
        pairs.add(new BasicNameValuePair("zone", obj.zone));
        String result = sendRequest("TBEAYUN011001002002", pairs);
        rspInfo = gson.fromJson(result, BaseResponseModel.class);
        return rspInfo;
    }

}
