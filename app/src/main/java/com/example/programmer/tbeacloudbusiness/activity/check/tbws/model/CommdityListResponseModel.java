package com.example.programmer.tbeacloudbusiness.activity.check.tbws.model;



import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 获得服务凭证对应的购买产品列表
 */

public class CommdityListResponseModel extends BaseResponseModel {

    public DataBean data;


    public class DataBean {
        public List<CommoditylistBean> commoditylist;

        public class CommoditylistBean {
            public String commodityid;
            public String thumbpicture;
            public String name;
            public String modelandspecification;
            public String number;
        }
    }
}
