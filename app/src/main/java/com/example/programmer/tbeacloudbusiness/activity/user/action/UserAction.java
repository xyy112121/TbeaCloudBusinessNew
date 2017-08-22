package com.example.programmer.tbeacloudbusiness.activity.user.action;

import com.example.programmer.tbeacloudbusiness.activity.companyPersonnel.plumberMeeting.model.FranchiserSelectListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.CompletionDataRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.CompletionDataResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RelaNameAuthenticationResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.HomeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.LoginUserModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.MyMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.TbMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RegisterRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.RelaNameAuthenticationRequestModel;
import com.example.programmer.tbeacloudbusiness.http.MD5Util;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
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

    /**
     * 发送验证码
     * serviceCode 验证码用途
     * @return
     * @throws Exception
     */
    public ResponseInfo sendCode(String phone,String serviceCode )throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobilenumber ", phone));
        pairs.add(new BasicNameValuePair("servicecode", serviceCode));
        String result = sendRequest("TBEAYUN003001001000", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 注册
     */
    public ResponseInfo register(RegisterRequestModel obj)throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobilenumber", obj.mobilenumber));
        pairs.add(new BasicNameValuePair("password", MD5Util.getMD5String(obj.password)));
        pairs.add(new BasicNameValuePair("verifycode", obj.verifycode));
        pairs.add(new BasicNameValuePair("usertypeid", obj.usertypeid));
        String result = sendRequest("TBEAYUN003001002000", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 补全资料
     */
    public ResponseInfo completionData(CompletionDataRequestModel obj)throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("mobilenumber ", obj.mobilenumber));
        pairs.add(new BasicNameValuePair("usertypeid ", obj.usertypeid));
        pairs.add(new BasicNameValuePair("province ", obj.province));
        pairs.add(new BasicNameValuePair("city ", obj.city));
        pairs.add(new BasicNameValuePair("zone ", obj.zone));
        pairs.add(new BasicNameValuePair("uplevelcompanyid ", obj.uplevelcompanyid));
        pairs.add(new BasicNameValuePair("realname ", obj.realname));
        pairs.add(new BasicNameValuePair("sexid ", obj.sexid));
        pairs.add(new BasicNameValuePair("birthyear ", obj.birthyear));
        pairs.add(new BasicNameValuePair("birthmonth ", obj.birthmonth));
        pairs.add(new BasicNameValuePair("birthday ", obj.birthday));
        pairs.add(new BasicNameValuePair("picture ", obj.picture));
        String result = sendRequest("TBEAYUN003002001000", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 获取隶属关系列表
     * @return
     * @throws Exception
     */
    public FranchiserSelectListResponseModel getAffiliationList() throws Exception {
        FranchiserSelectListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN001004002000", pairs);
        model = gson.fromJson(result, FranchiserSelectListResponseModel.class);
        return model;
    }

    /**
     * 认证
     */
    public ResponseInfo attestation(RelaNameAuthenticationRequestModel obj)throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("companyname ", obj.companyname));
        pairs.add(new BasicNameValuePair("companylisencecode ", obj.companylisencecode));
        pairs.add(new BasicNameValuePair("province ", obj.province));
        pairs.add(new BasicNameValuePair("city ", obj.city));
        pairs.add(new BasicNameValuePair("zone ", obj.zone));
        pairs.add(new BasicNameValuePair("address ", obj.address));
        pairs.add(new BasicNameValuePair("businessscope ", obj.businessscope));
        pairs.add(new BasicNameValuePair("companylisencepicture ", obj.companylisencepicture));
        pairs.add(new BasicNameValuePair("masterperson ", obj.masterperson));
        pairs.add(new BasicNameValuePair("masterpersonid ", obj.masterpersonid));
        pairs.add(new BasicNameValuePair("masterpersonidcard1 ", obj.masterpersonidcard1));
        pairs.add(new BasicNameValuePair("masterpersonidcard2 ", obj.masterpersonidcard2));
        pairs.add(new BasicNameValuePair("companyphoto ", obj.companyphoto));
        String result = sendRequest("TBEAYUN003003001000", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 获取实名认证
     */
    public RelaNameAuthenticationResponseModel getRelaNameAuthentication()throws Exception {
        RelaNameAuthenticationResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN003003002000", pairs);
        model = gson.fromJson(result, RelaNameAuthenticationResponseModel.class);
        return model;
    }

    /**
     * 获取补全资料
     */
    public CompletionDataResponseModel getCompletionData()throws Exception {
        CompletionDataResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN003002002000", pairs);
        model = gson.fromJson(result, CompletionDataResponseModel.class);
        return model;
    }




}
