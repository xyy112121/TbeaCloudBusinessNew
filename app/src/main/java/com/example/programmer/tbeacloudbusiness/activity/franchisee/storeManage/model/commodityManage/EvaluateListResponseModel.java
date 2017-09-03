package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.commodityManage;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 2017/9/3.
 */

public class EvaluateListResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public CommodityinfoBean commodityinfo;
        public List<EcaluateBean> evaluatelist;

        public class CommodityinfoBean {
            public String commodityid;
            public String thumbpicture;
            public String commodityname;
            public String price;
            public String publishtime;
            public String salenumber;
            public String evaluatenumber;
            public String recommended;
        }

        public class EcaluateBean {
            public String evaluateid;
            public String personthumbpicture;//购买用户头像
            public String personname;//购买用户姓名
            public String persontypeicon;//用户类型
            public String evaluatecontent;//评价内容
            public String evaluatetime;//评价时间
        }
    }
}
