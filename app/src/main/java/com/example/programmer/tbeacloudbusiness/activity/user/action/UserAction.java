package com.example.programmer.tbeacloudbusiness.activity.user.action;

import com.example.programmer.tbeacloudbusiness.activity.user.model.HomeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.LoginUserModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.MyMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.TbMainResponseModel;
import com.example.programmer.tbeacloudbusiness.http.MD5Util;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/7/9.
 */

public class UserAction extends BaseAction {
    /**
     * 登录
     *
     * @param phone       用户名
     * @param pwd         密码
     * @param devicetoken 手机唯一码
     */
    public LoginUserModel login(String phone, String pwd, String devicetoken) throws Exception {
        LoginUserModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobilenumber", phone));
        pairs.add(new BasicNameValuePair("userpas", MD5Util.getMD5String(pwd)));
        pairs.add(new BasicNameValuePair("devicetoken", devicetoken));
        String result = sendRequest("TBEAYUN003001004000", pairs);
        rspInfo = gson.fromJson(result, LoginUserModel.class);
        return rspInfo;
    }

    /**
     * 获取主页显示数据
     */
    public HomeMainResponseModel getMainData() throws Exception {
        HomeMainResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN002001001000", pairs);
        model = gson.fromJson(result, HomeMainResponseModel.class);
        return model;
    }

    /***
     * 获取我的界面数据
     * @return
     * @throws Exception
     */
    public MyMainResponseModel getMyMainData() throws Exception {
        MyMainResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN002001003000", pairs);
        model = gson.fromJson(result, MyMainResponseModel.class);
        return model;
    }

    /**
     * 获取特变电工首页数据
     */
    public TbMainResponseModel getTbMainData() throws Exception {
        TbMainResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN002002001000", pairs);
        model = gson.fromJson(result, TbMainResponseModel.class);
        return model;
    }

}
