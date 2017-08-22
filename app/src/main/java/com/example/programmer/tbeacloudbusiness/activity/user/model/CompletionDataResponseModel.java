package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/8/22.
 */

public class CompletionDataResponseModel extends BaseResponseModel {


    public DataBean data;

    public static class DataBean {
        public PersoninfoBean personinfo;

        public static class PersoninfoBean {
            public String id;
            public String mobilenumber;
            public String usertypeid;
            public String usertype;
            public String provinceid;
            public String province;
            public String cityid;
            public String city;
            public String zoneid;
            public String zone;
            public String picture;
            public FirstdistributorinfoBean firstdistributorinfo;
            public String realname;
            public String sexid;
            public String birthyear;
            public String birthmonth;
            public String birthday;

            public static class FirstdistributorinfoBean {
                public String firstdistributorid;
                public String thumbpicture;
                public String personname;
                public String companyname;
            }
        }
    }
}
