package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 商品销售列表
 */

public class CommoditySalesResponseModel extends BaseResponseModel {
    public DataBean data;

    public class DataBean {
        public List<CommodityinfoBean> commoditylist;

        public class CommodityinfoBean {
            public String commodityid;//商品 Id
            public String thumbpicture;//缩略图
            public String name;//商品名称
            public String price;//价格
            public String promotion;//促销信息
            public String salenumber;//已售出数
        }
    }
}
