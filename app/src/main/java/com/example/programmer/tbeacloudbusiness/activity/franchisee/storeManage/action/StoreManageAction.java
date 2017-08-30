package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityManageListRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityManageListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.ModelSpecListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.RotateADListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.StoreIntroduceResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.StoreManageMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.VisualGraphResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ProductPresentationListResponseModel;
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
        String result = sendRequest("TBEAYUN006002002000", pairs);
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

    /**
     * 商城管理-­‐获得规格参数列表
     */
    public ModelSpecListResponseModel getModelSpecList() throws Exception {
        ModelSpecListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN006002005001", pairs);
        model = gson.fromJson(result, ModelSpecListResponseModel.class);
        return model;
    }

    /**
     * 删除规格型号
     */
    public ResponseInfo deleteModelSpec(String id) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("modelspecid", id));
        String result = sendRequest("TBEAYUN006002005003", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 添加规格型号
     */
    public ResponseInfo saveModelSpec(String name) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("modelspecname", name));
        String result = sendRequest("TBEAYUN006002005002", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 获取店铺商品列表
     */
    public CommodityManageListResponseModel getCommodityList(CommodityManageListRequestModel request) throws Exception {
        CommodityManageListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", request.orderitem));
        pairs.add(new BasicNameValuePair("order", request.order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(request.page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(request.pageSize)));
        String result = sendRequest("TBEAYUN006004001000", pairs);
        model = gson.fromJson(result, CommodityManageListResponseModel.class);
        return model;
    }

}
