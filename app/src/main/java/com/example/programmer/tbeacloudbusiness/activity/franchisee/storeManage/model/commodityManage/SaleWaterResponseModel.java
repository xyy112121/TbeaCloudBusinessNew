package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * -­‐商品销售流水.
 */

public class SaleWaterResponseModel extends BaseResponseModel {
    public DataBean data;

    public class DataBean {
        public List<CommodityinfoBean> commoditylist;

        public class CommodityinfoBean {
            public String commodityid;
            public String thumbpicture;//缩略图
            public String name;//商品名称
            public String promotion;//促销信息
            public String salenumber;//已售出数
            public String price;//单价
            public String finishtime;//完成时间
        }
    }
}
