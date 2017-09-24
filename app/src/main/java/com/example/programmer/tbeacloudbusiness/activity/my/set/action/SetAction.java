package com.example.programmer.tbeacloudbusiness.activity.my.set.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListStateResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingViewResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddrListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.AddressModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.BackgroundInfoModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.NotifyInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.PwdUpdateModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.SetResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.UserTypeResponseModel;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.example.programmer.tbeacloudbusiness.http.MD5Util;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
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
     * 1 RecvAddrId   收货地址 Id 不为空则为修改 为空为增加 2 ContactPerson   联系人  3 ContactMobile   联系电话  4 Province   所在省份名  5 City   所在城市名  6 Zone   所在区域名  7 Address   收货地址  8 IsDefault   是否默认
     */
    public ResponseInfo editAddrss(AddressModel obj) throws Exception {
        ResponseInfo rspInfo;
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
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 删除收货地址
     */
    public ResponseInfo deleteAddrss(String recvaddrid) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("recvaddrid", recvaddrid));
        String result = sendRequest("TBEAYUN011001002003", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 获取背景图
     */
    public BackgroundInfoModel getBackground() throws Exception {
        BackgroundInfoModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN011001003001", pairs);
        rspInfo = gson.fromJson(result, BackgroundInfoModel.class);
        return rspInfo;
    }

    /**
     * 设置背景图
     *
     * @param picture 上传后的背景图 名称
     */
    public ResponseInfo setBackground(String picture) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("picture", picture));
        String result = sendRequest("TBEAYUN011001003002", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 删除背景图
     */
    public ResponseInfo deleteBackground() throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN011001003003", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 用户中心-­‐我的-­‐设置-­‐通用-­‐消息通知获取
     */
    public NotifyInfoResponseModel getNotify() throws Exception {
        NotifyInfoResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN011001003004", pairs);
        rspInfo = gson.fromJson(result, NotifyInfoResponseModel.class);
        return rspInfo;
    }

    /**
     * 用户中心-­‐我的-­‐设置-­‐通用-­‐消息通知设置
     */
    public ResponseInfo setNotify(String notifyOnOrOff) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("notifyonoroff", notifyOnOrOff));
        String result = sendRequest("TBEAYUN011001003005", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 修改密码
     */
    public ResponseInfo updatePwd(String olduserpas, String newuserpas) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("olduserpas", MD5Util.getMD5String(olduserpas)));
        pairs.add(new BasicNameValuePair("newuserpas",MD5Util.getMD5String(newuserpas)));
        String result = sendRequest("TBEAYUN003001006000", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    public UserTypeResponseModel getUserType() throws Exception {
        UserTypeResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001006001000", pairs);
        model = gson.fromJson(result, UserTypeResponseModel.class);
        return model;
    }


}
