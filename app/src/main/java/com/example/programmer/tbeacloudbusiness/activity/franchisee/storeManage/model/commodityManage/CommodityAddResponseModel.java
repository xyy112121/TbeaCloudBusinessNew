package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 2017/9/3.
 */

public class CommodityAddResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public CommodityinfoBean commodityinfo;

        public class CommodityinfoBean {
            public String id;
            public String name;
            public String categoryid;
            public String categoryname;
            public String moditymodelid;
            public String moditymodelname;
            public String modityspecid;
            public String modityspecname;
            public String price;
            public String discountmoney;
            public String unit;
            public String stocknumber;
            public String description;
            public String thumblist;
            public String picturelist;
            public String recommended;
        }
    }
}
