package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * 商城管理-­‐商品销售详情
 */

public class MarketInfoResponseModel extends BaseResponseModel {


    public DataBean data;

    public static class DataBean {
        public CommodityinfoBean commodityinfo;
        public SaleinfoBean saleinfo;

        public static class CommodityinfoBean {
            public String commodityid;
            public String thumbpicture;
            public String commodityname;
            public String publishtime;
            public int price;
        }

        public static class SaleinfoBean {
            public int salenumber;//已售出数
            public int evaluatenumber;//评价条数
        }
    }
}
