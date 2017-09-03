package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 商城管理-­‐获得产品分类列表
 */

public class CommodityCategoryResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public List<CategorylistBean> categorylist;

        public  class CategorylistBean {
            public String categoryid;
            public String categoryname;
        }
    }
}
