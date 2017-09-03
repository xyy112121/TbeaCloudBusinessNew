package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by DELL on 2017/9/3.
 */

public class CommoditySalesInfoResponseModel extends BaseResponseModel {
    public DataBean data;

    public class DataBean {
        public CommodityinfoBean commodityinfo;
        public List<SalelistBean> salelist;


        public class CommodityinfoBean {
            public String commodityid;
            public String thumbpicture;//缩略图
            public String name;//商品名称
            public String promotion;//促销信息
            public String salenumber;//商品销量
            public int price;//单价
        }

        public class SalelistBean {
            public String personthumbpicture;//购买用户头像
            public String personname;//购买用户姓名
            public String persontypeicon;//用户类型
            public int buyamount;//购买数量
            public String paytime;//支付时间
        }
    }
}
