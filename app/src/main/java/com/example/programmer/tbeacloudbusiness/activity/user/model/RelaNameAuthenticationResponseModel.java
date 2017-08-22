package com.example.programmer.tbeacloudbusiness.activity.user.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by programmer on 2017/8/22.
 */

public class RelaNameAuthenticationResponseModel extends BaseResponseModel {

    public DataBean data;

    public static class DataBean {
        public CompanyidentifyinfoBean companyidentifyinfo;

        public static class CompanyidentifyinfoBean {
            public String id;
            public String companyname;//公司名
            public String companylisencecode;//营业执照号
            public String businessscope;//经营范围
            public String companylisencepicture;//营业执照
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
