package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityAddRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityAddResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityCategoryResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityManageListRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommodityManageListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommoditySalesInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.CommoditySalesResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.EvaluateListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.MarketInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.SaleListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage.SaleWaterResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.DynamicListRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.DynamicListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.ModelSpecListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.RotateADEditRequestModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.RotateADListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.RotateAdEditResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.StoreIntroduceResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.StoreManageMainResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set.VisualGraphResonpseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ProductPresentationListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.UserTypeResponseModel;
import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
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
     * 获得商城的广告详细(编辑用)
     */
    public RotateAdEditResponseModel getRotateAd(String advertiseId) throws Exception {
        RotateAdEditResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("advertiseid", advertiseId));
        String result = sendRequest("TBEAYUN006002002001", pairs);
        model = gson.fromJson(result, RotateAdEditResponseModel.class);
        return model;
    }

    /**
     * 删除广告详细
     */
    public ResponseInfo deleteRotateAd(String advertiseId) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("advertiseid", advertiseId));
        String result = sendRequest("TBEAYUN006002002003", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 添加和编辑店铺动态
     */
    public ResponseInfo editRotateAd(RotateADEditRequestModel request) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("advertiseid", request.advertiseid));
        pairs.add(new BasicNameValuePair("urltype", request.urltype));
        pairs.add(new BasicNameValuePair("url", request.url));
        pairs.add(new BasicNameValuePair("picture", request.picture));
        String result = sendRequest("TBEAYUN006002002002", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
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
    public ResponseInfo saveModelSpec(String name,String methodName) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("modelspecname", name));
        String result = sendRequest(methodName, pairs);
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

    /**
     * 获取添加商品时所用的商品分类
     */
    public CommodityCategoryResponseModel getCommodityType() throws Exception {
        CommodityCategoryResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN006004001005", pairs);
        model = gson.fromJson(result, CommodityCategoryResponseModel.class);
        return model;
    }

    /**
     * 添加店铺商品
     */
    public ResponseInfo addCommodity(CommodityAddRequestModel request) throws Exception {
        ResponseInfo model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", request.commodityid));
        pairs.add(new BasicNameValuePair("name", request.name));
        pairs.add(new BasicNameValuePair("categoryid", request.categoryid));
        pairs.add(new BasicNameValuePair("moditymodelid", request.moditymodelid));
        pairs.add(new BasicNameValuePair("modityspecid", request.modityspecid));
        pairs.add(new BasicNameValuePair("price", request.price));
        pairs.add(new BasicNameValuePair("discountmoney", request.discountmoney));
        pairs.add(new BasicNameValuePair("unit", request.unit));
        pairs.add(new BasicNameValuePair("stocknumber", request.stocknumber));
        pairs.add(new BasicNameValuePair("description", request.description));
        pairs.add(new BasicNameValuePair("thumblist", request.thumblist));
        pairs.add(new BasicNameValuePair("picturelist", request.picturelist));
        pairs.add(new BasicNameValuePair("recommended", request.recommended));
        String result = sendRequest("TBEAYUN006004001002", pairs);
        model = gson.fromJson(result, ResponseInfo.class);
        return model;
    }

    /**
     * 获得店铺商品(编辑用)
     */
    public CommodityAddResponseModel getCommodity(String commodityid) throws Exception {
        CommodityAddResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", commodityid));
        String result = sendRequest("TBEAYUN006004001001", pairs);
        model = gson.fromJson(result, CommodityAddResponseModel.class);
        return model;
    }

    /**
     * 删除店铺商品
     */
    public ResponseInfo deleteCommodity(String commodityid) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", commodityid));
        String result = sendRequest("TBEAYUN006004001004", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 获取店铺动态
     */
    public DynamicListResponseModel getDynamicList(DynamicListRequestModel request) throws Exception {
        DynamicListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("orderitem", request.orderitem));
        pairs.add(new BasicNameValuePair("order", request.order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(request.page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(request.pageSize)));
        String result = sendRequest("TBEAYUN006005001001", pairs);
        model = gson.fromJson(result, DynamicListResponseModel.class);
        return model;
    }

    /**
     * 商城管理-­‐添加文章
     * NewsId   新闻 Id
     * 如果不为空，则为修改操 作 如果为空，则为添加操作 2 Title   标题  3 Content   内容  4 PictureList   图片列表
     */
    public ResponseInfo addDynamic(String newsId, String title, String content, String pictureList) throws Exception {
        ResponseInfo resultModel;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("newsid", newsId));
        pairs.add(new BasicNameValuePair("picturetitle", title));
        pairs.add(new BasicNameValuePair("content", content));
        pairs.add(new BasicNameValuePair("picturelist", pictureList));
        String result = sendRequest("TBEAYUN006005001002", pairs);
        resultModel = gson.fromJson(result, ResponseInfo.class);
        return resultModel;
    }

    /**
     * 商城管理-­‐删除文章
     */
    public ResponseInfo deleteDynamic(String advertiseId) throws Exception {
        ResponseInfo rspInfo;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("newsids", advertiseId));
        String result = sendRequest("TBEAYUN006005001003", pairs);
        rspInfo = gson.fromJson(result, ResponseInfo.class);
        return rspInfo;
    }

    /**
     * 商品销售详情
     */
    public MarketInfoResponseModel getMarketInfo(String commodityId) throws Exception {
        MarketInfoResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", commodityId));
        String result = sendRequest("TBEAYUN006004002001", pairs);
        model = gson.fromJson(result, MarketInfoResponseModel.class);
        return model;
    }

    /**
     * 商城管理-­‐销售详情-­‐已出售列表
     */
    public SaleListResponseModel getSaleList(String commodityid, String orderItem, String order, int page, int pageSize) throws Exception {
        SaleListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", commodityid));
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pageSize)));
        String result = sendRequest("TBEAYUN006004002002", pairs);
        model = gson.fromJson(result, SaleListResponseModel.class);
        return model;
    }


    /**
     * 已评价列表 用户：Userpin 评价时间：Time
     */
    public EvaluateListResponseModel getEvaluateList(String commodityid, String orderItem, String order, int page, int pageSize) throws Exception {
        EvaluateListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", commodityid));
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pageSize)));
        String result = sendRequest("TBEAYUN006004002003", pairs);
        model = gson.fromJson(result, EvaluateListResponseModel.class);
        return model;
    }


    /**
     * 商城管理-­‐商品销量列表
     */
    public CommoditySalesResponseModel getCommoditySalesList(String startTime, String endTime, String orderItem, String order, int page, int pageSize) throws Exception {
        CommoditySalesResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("starttime", startTime));
        pairs.add(new BasicNameValuePair("endtime", endTime));
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pageSize)));
        String result = sendRequest("TBEAYUN006006001001", pairs);
        model = gson.fromJson(result, CommoditySalesResponseModel.class);
        return model;
    }


    /**
     * 商城管理-­‐商品销量-­‐详情
     */
    public CommoditySalesInfoResponseModel getSaleInfo(String commodityid, String orderItem, String order, int page, int pageSize) throws Exception {
        CommoditySalesInfoResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", commodityid));
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pageSize)));
        String result = sendRequest("TBEAYUN006006001002", pairs);
        model = gson.fromJson(result, CommoditySalesInfoResponseModel.class);
        return model;
    }

    /**
     * 商城管理-­‐商品销售流水
     */
    public SaleWaterResponseModel getSaleWater(String startTime, String endTime, String orderItem, String order, int page, int pageSize) throws Exception {
        SaleWaterResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("starttime", startTime));
        pairs.add(new BasicNameValuePair("endtime", endTime));
        pairs.add(new BasicNameValuePair("orderitem", orderItem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pageSize)));
        String result = sendRequest("TBEAYUN006006001003", pairs);
        model = gson.fromJson(result, SaleWaterResponseModel.class);
        return model;
    }

}
