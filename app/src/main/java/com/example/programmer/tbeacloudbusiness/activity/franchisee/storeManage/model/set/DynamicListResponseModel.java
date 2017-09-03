package com.example.programmer.tbeacloudbusiness.activity.franchisee.storeManage.model.set;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * -­‐店铺动态列表
 */

public class DynamicListResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public List<NewslistBean> newslist;

        public  class NewslistBean implements Serializable {
            public String newsid;
            public String thumbpicture;
            public String title;
            public String time;
        }
    }
}
