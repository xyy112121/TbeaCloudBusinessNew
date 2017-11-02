package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 客服中心
 */

public class ServiceCenterResponseModel extends BaseResponseModel {


    public DataBean data;


    public class DataBean {
        public HotlineinfoBean hotlineinfo;
        public List<MessageCategory> questionlist;

        public class HotlineinfoBean {
            public String mobilenumber;
        }
    }
}
