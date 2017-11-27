package com.example.programmer.tbeacloudbusiness.activity.my.main.action;

import com.example.programmer.tbeacloudbusiness.activity.MyApplication;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AttentionCommodityResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AttentionPersonResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.AttentionStoreResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountEditRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountEditUserTypeRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountFunctionListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.BypassAccountListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.main.model.FansListResponseModel;
import com.example.programmer.tbeacloudbusiness.http.MD5Util;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的
 */

public class MyAction extends BaseAction {
    /**
     * 我的-子账号列表
     */
    public BypassAccountListResponseModel getBypassAccountList(int page, int pagesize) throws Exception {
        BypassAccountListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN011004001001", pairs);
        model = gson.fromJson(result, BypassAccountListResponseModel.class);
        return model;
    }


    /**
     * 用户中心-­‐我的-­‐子帐号-­‐功能模块列表
     */
    public BypassAccountFunctionListResponseModel getFunctionList(String id) throws Exception {
        BypassAccountFunctionListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("personid", id));
        String result = sendRequest("TBEAYUN011004003001", pairs);
        model = gson.fromJson(result, BypassAccountFunctionListResponseModel.class);
        return model;
    }

    /**
     * 新增子账号的时候获取用户类型
     */
    public BypassAccountEditUserTypeRequestModel getUserType() throws Exception {
        BypassAccountEditUserTypeRequestModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN011004002000", pairs);
        model = gson.fromJson(result, BypassAccountEditUserTypeRequestModel.class);
        return model;
    }

    /**
     * 保存子账户
     */
    public ResponseInfo saveBypassAccount(BypassAccountEditRequestModel requestModel) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("authorizationlist", requestModel.authorizationlist));
        pairs.add(new BasicNameValuePair("thumbpicture", requestModel.thumbpicture));
        pairs.add(new BasicNameValuePair("jobtitle", requestModel.jobtitle));
        pairs.add(new BasicNameValuePair("password", MD5Util.getMD5String(requestModel.password)));
        pairs.add(new BasicNameValuePair("birthyear", requestModel.birthyear));
        pairs.add(new BasicNameValuePair("birthmonth", requestModel.birthmonth));
        pairs.add(new BasicNameValuePair("birthday", requestModel.birthday));
        pairs.add(new BasicNameValuePair("sex", requestModel.sex));
        pairs.add(new BasicNameValuePair("realname", requestModel.realname));
        pairs.add(new BasicNameValuePair("usertypeId", requestModel.usertypeId));
        pairs.add(new BasicNameValuePair("mobilenumber", requestModel.mobilenumber));
        String result = sendRequest("TBEAYUN011004002001", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    public ResponseInfo updateBypassAccount(String json) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("moduleauthlist", json));
        String result = sendRequest("TBEAYUN011004004001", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 关注的商品
     */
    public AttentionCommodityResponseModel getAttentionCommodityList
    (String userId, int page, int pagesize) throws Exception {
        AttentionCommodityResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        if (userId != null) {
            pairs.add(new BasicNameValuePair("userid", userId));
        } else {
            pairs.add(new BasicNameValuePair("userid", MyApplication.instance.getUserId()));
        }
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN011002001001", pairs);
        model = gson.fromJson(result, AttentionCommodityResponseModel.class);
        return model;
    }

    /**
     * 关注的店铺
     */
    public AttentionStoreResponseModel getAttentionStoreList
    (String userId, int page, int pagesize) throws Exception {
        AttentionStoreResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        if (userId != null) {
            pairs.add(new BasicNameValuePair("userid", userId));
        } else {
            pairs.add(new BasicNameValuePair("userid", MyApplication.instance.getUserId()));
        }
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN011002002001", pairs);
        model = gson.fromJson(result, AttentionStoreResponseModel.class);
        return model;
    }

    /**
     * 关注的个人
     */
    public AttentionPersonResponseModel getAttentionPersonList
    (String userId, int page, int pagesize) throws Exception {
        AttentionPersonResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        if (userId != null) {
            pairs.add(new BasicNameValuePair("userid", userId));
        } else {
            pairs.add(new BasicNameValuePair("userid", MyApplication.instance.getUserId()));
        }
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN011002003001", pairs);
        model = gson.fromJson(result, AttentionPersonResponseModel.class);
        return model;
    }

    /**
     * 取消关注
     */
    public ResponseInfo cancelAttention(String focusids) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("focusids", focusids));
        String result = sendRequest("TBEAYUN011002004001", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 我的粉丝
     */
    public FansListResponseModel getFansList
    (String userId, int page, int pagesize) throws Exception {
        FansListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        if (userId != null) {
            pairs.add(new BasicNameValuePair("userid", userId));
        } else {
            pairs.add(new BasicNameValuePair("userid", MyApplication.instance.getUserId()));
        }
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN011003001001", pairs);
        model = gson.fromJson(result, FansListResponseModel.class);
        return model;
    }

    /**
     * 取消关注
     */
    public ResponseInfo cancelFans(String fansids) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("fansids", fansids));
        String result = sendRequest("TBEAYUN011003002001", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

}
