package com.example.programmer.tbeacloudbusiness.activity.franchisee.plumberManage.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * 水电工个人资料
 */

public class PlumberInfoResponseModel extends BaseResponseModel {

    public DataBean data;

    public class DataBean {
        public ElectricianpersonalinfoBean electricianpersonalinfo;

        public class ElectricianpersonalinfoBean {
            public String userid;
            public String personname;
            public String mobilenumber;
            public String whetheridentifyname;
            public String introduce;
            public String belongtocompany;
            public String sex;
            public String age;
            public String workyear;
        }
    }
}
