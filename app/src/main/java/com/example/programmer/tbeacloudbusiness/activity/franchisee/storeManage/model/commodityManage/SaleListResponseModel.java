package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 已出售列表
 */

public class SaleListResponseModel extends BaseResponseModel {
    public DataBean data;

    public class DataBean {
        public CommodityinfoBean commodityinfo;
        public List<SalelistBean> salelist;

        public class CommodityinfoBean {
            public String commodityid;
            public String thumbpicture;
            public String commodityname;
            public String publishtime;
            public int price;
        }

        public class SalelistBean {
            public String personthumbpicture;
            public String personname;
            public String persontypeicon;
            public int buyamount;
            public String paytime;
        }
    }
}
