package com.example.programmer.tbeacloudbusiness.service.impl;

import com.example.programmer.tbeacloudbusiness.http.MD5Util;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 17/1/14.
 */

public class UserAction extends BaseAction {
    /**
     * 登录
     *
     * @param phone 用户名
     * @param pwd   密码
     *  @param  devicetoken 手机唯一码

     */
    public BaseResponseModel login(String phone, String pwd, String devicetoken) throws Exception {
        BaseResponseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobilenumber", phone));
        pairs.add(new BasicNameValuePair("userpas", MD5Util.getMD5String(pwd)));
        pairs.add(new BasicNameValuePair("devicetoken", devicetoken));
        String result = sendRequest("TBEAYUN003001004000", pairs);
        rspInfo = gson.fromJson(result,BaseResponseModel.class);
        return rspInfo;
    }

    /**
     * 获取主页显示数据
     */
    public String getMainData()throws Exception {
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN002001001000", pairs);
        return result;
    }


}
