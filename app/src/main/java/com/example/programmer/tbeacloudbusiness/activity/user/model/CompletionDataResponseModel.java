package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/8/22.
 */

public class CompletionDataResponseModel extends BaseResponseModel {
    public DataBean data;


    public  class DataBean {
        public CompanyidentifyinfoBean companyidentifyinfo;

        public  class CompanyidentifyinfoBean {
            public String id;
            public String companyname;
            public Object businessscope;
            public String provinceid;
            public String cityid;
            public String zoneid;
            public String address;
            public String companyaddress;
            public String whetheridentifiedid;
            public String whetheridentified;
            public String masterperson;
            public String masterpersonid;
            public String masterpersonidcard1;
            public String masterpersonidcard2;
            public String companypicture;
        }
    }
}
