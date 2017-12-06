package com.example.programmer.tbeacloudbusiness.activity.tbea.action;

import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.ScanCodeTypeSelectReponseModel;
import com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model.WithdrawDepositDateHistoryListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.publicUse.model.TbeaPictrueResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CommodityCategoryResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CommodityModelResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CommoditySpecificationResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.CompanyIntroListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ContactInfoResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.tbea.model.ProductPresentationListResponseModel;
import com.example.programmer.tbeacloudbusiness.activity.user.model.PicturelistBean;
import com.example.programmer.tbeacloudbusiness.model.ResponseInfo;
import com.example.programmer.tbeacloudbusiness.service.impl.BaseAction;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/7/22.
 */

public class TbeaAction extends BaseAction {


    //获取商品介绍里面的产品类别
    public CommodityCategoryResponseModel getCommodityCategory() throws Exception {
        CommodityCategoryResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("parentcategoryid", null));
        String result = sendRequest("TBEAYUN001002003000", pairs);
        model = gson.fromJson(result, CommodityCategoryResponseModel.class);
        return model;
    }

    /**
     * 获取产品介绍列表
     */
    public ProductPresentationListResponseModel getCommodityList(String name, String commodityspecificationid, String commoditymodelid, String orderitem, String order, int page, int pagesize) throws Exception {
        ProductPresentationListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("name", name));
        pairs.add(new BasicNameValuePair("commodityspecificationid", commodityspecificationid));
        pairs.add(new BasicNameValuePair("commoditymodelid", commoditymodelid));
        pairs.add(new BasicNameValuePair("orderitem", orderitem));
        pairs.add(new BasicNameValuePair("order", order));
        pairs.add(new BasicNameValuePair("page", String.valueOf(page)));
        pairs.add(new BasicNameValuePair("pagesize", String.valueOf(pagesize)));
        String result = sendRequest("TBEAYUN002002004000", pairs);
        model = gson.fromJson(result, ProductPresentationListResponseModel.class);
        return model;
    }

    /**
     * 获取新闻资讯里面的公司动态
     */

    public CompanyIntroListResponseModel getCompanyDynamic(String category) throws Exception {
        CompanyIntroListResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("category", category));
        String result = sendRequest("TBEAYUN002002003000", pairs);
        model = gson.fromJson(result, CompanyIntroListResponseModel.class);
        return model;
    }

    /**
     * 获取联系方式
     */

    public ContactInfoResponseModel getContactInfo() throws Exception {
        ContactInfoResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        String result = sendRequest("TBEAYUN002002005000", pairs);
        model = gson.fromJson(result, ContactInfoResponseModel.class);
        return model;
    }


    //获取商品图片大图
    public TbeaPictrueResponseModel getCommodityPicture(String id) throws Exception {
        TbeaPictrueResponseModel model;
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("commodityid", id));
        String result = sendRequest("TBEAYUN002002004003", pairs);
        model = gson.fromJson(result, TbeaPictrueResponseModel.class);
        return model;
    }
}
