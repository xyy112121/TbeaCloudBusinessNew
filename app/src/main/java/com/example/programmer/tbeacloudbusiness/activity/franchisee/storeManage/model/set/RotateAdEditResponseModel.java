package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 获得商城的广告详细(编辑用)。
 */

public class RotateAdEditResponseModel extends BaseResponseModel {

    public DataBean data;


    public static class DataBean {
        public ShopadvertiseinfoBean shopadvertiseinfo;
        public CommodityinfoBean commodityinfo;
        public NewBean newsinfo;

        public class ShopadvertiseinfoBean {
            public String advertiseid;
            public String title;
            public String urltype;
            public String urltypename;
            public String picture;
        }

        public class CommodityinfoBean {
            public String commodityid;
            public String thumbpicture;
            public String name;
            public String salenumber;
            public String evaluatenumber;
            public String recommended;
            public String price;
        }

        public class NewBean {
            public String newsid;
            public String thumbpicture;
            public String title;
            public String time;
        }
    }
}
