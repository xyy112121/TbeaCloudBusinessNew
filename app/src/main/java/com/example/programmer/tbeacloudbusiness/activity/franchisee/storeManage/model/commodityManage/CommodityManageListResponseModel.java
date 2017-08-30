package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 店铺商品列表
 */

public class CommodityManageListResponseModel extends BaseResponseModel{
    public DataBean data;

    public  class DataBean {
        public TotleinfoBean totleinfo;
        public List<CommoditylistBean> commoditylist;//店铺商品列表

        public  class TotleinfoBean {
            public Integer totlecommoditynumber;//总数
        }

        public  class CommoditylistBean {
            public String commodityid;
            public String thumbpicture;//缩略图
            public String commodityname;//商品名称
            public String salenumber;//已售出数
            public String evaluatenumber;//评价条数
            public int recommended;// 是否推荐商品 1：是 其他值：不是
            public String price;
        }
    }
}
