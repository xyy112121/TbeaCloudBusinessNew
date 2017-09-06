package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * -­‐商品销售流水.
 */

public class SaleWaterResponseModel extends BaseResponseModel {
    public DataBean data;

    public class DataBean {
        public TotleinfoBean totleinfo;
        public List<CommoditylistBean> commoditylist;

        public class TotleinfoBean {
            public String saletotlemoney;
        }

        public class CommoditylistBean {
            public String commodityid;
            public String thumbpicture;
            public String name;
            public String commodityname;
            public String price;
            public String publishtime;
            public int salenumber;
            public String evaluatenumber;
            public String recommended;
            public String promotion;
            public String finishtime;
            public String id;
            public int createtime;
        }
    }

}
