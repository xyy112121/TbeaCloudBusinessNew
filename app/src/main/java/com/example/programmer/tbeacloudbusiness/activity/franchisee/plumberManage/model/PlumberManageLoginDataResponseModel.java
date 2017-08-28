package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 登录详情
 */

public class PlumberManageLoginDataResponseModel extends BaseResponseModel {

    public DataBean data;

    public  class DataBean {
        public List<LoginlistBean> loginlist;

        public  class LoginlistBean {
            public String loginid;
            public String logintime;
            public String loginplace;
            public String loginterminal;
        }
    }
}
