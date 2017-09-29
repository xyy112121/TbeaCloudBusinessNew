package com.example.programmer.tbeacloudbusiness.activity.my.main.model;


import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/9/28.
 */

public class PersonInfoResponseModel extends BaseResponseModel {


    public DataBean data;


    public class DataBean {
        public UserinfoBean userinfo;

        public class UserinfoBean {
            public String userid;
            public String username;
            public String thumbpicture;
            public String usertype;
            public String mobielnumber;
            public String companyname;
            public String provinceid;
            public String province;
            public String cityid;
            public String city;
            public String zoneid;
            public String zone;
            public String addr;
            public String identifystatusid;
            public String identifystatus;
            public String sex_id;
            public String sex;
            public String age;
        }
    }
}
