package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberMeeting.model.PlumberMeetingListMainResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.RotateADListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.StoreIntroduceResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.StoreManageMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.VisualGraphResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.my.set.model.BackgroundInfoModel;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城管理
 */

public class StoreManageAction extends BaseAction {
    /**
     * 获取主页显示数据
     */
    public StoreManageMainResponseModel getMainData() throws Exception {
        StoreManageMainResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN006001001000", pairs);
        model = gson.fromJson(result, StoreManageMainResponseModel.class);
        return model;
    }

    /**
     * 获取形象背景图
     */
    public VisualGraphResonpseModel getBackground() throws Exception {
        VisualGraphResonpseModel rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN006002001001", pairs);
        rspInfo = gson.fromJson(result, VisualGraphResonpseModel.class);
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
        String result = sendRequest("TBEAYUN006002001003", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 删除背景图
     */
    public ResponseInfo deleteBackground() throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN006002001002", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 商城管理-­‐获得轮换广告列表
     */
    public RotateADListResponseModel getRotateADList() throws Exception {
        RotateADListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN006002002001", pairs);
        model = gson.fromJson(result, RotateADListResponseModel.class);
        return model;
    }

    /**
     * 获得店铺介绍
     */
    public StoreIntroduceResponseModel getStoreIntroduce() throws Exception {
        StoreIntroduceResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN006002004001", pairs);
        model = gson.fromJson(result, StoreIntroduceResponseModel.class);
        return model;
    }

    /**
     * 设置店铺介绍
     */
    public ResponseInfo setStoreIntroduce(String text) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("shopdescription", text));
        String result = sendRequest("TBEAYUN006002004002", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }
}
