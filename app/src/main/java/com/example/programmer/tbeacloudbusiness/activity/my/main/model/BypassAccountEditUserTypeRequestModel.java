package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * 子账户编辑时获取的用户类型
 */

public class BypassAccountEditUserTypeRequestModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public SubaccountusertypeinfoBean subaccountusertypeinfo;

        public static class SubaccountusertypeinfoBean {
            public String id;
            public String name;
        }
    }
}
