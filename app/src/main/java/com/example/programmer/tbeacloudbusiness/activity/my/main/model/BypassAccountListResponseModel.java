package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 子账号列表
 */

public class BypassAccountListResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public List<SubaccountlistBean> subaccountlist;

        public static class SubaccountlistBean {
            public String userid;
            public String thumbpicture;
            public String name;
            public String persontypeicon;
            public String jobtitle;
        }
    }
}
