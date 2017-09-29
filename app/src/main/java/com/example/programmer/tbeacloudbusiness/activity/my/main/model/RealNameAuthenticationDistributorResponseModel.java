package com.example.programmer.tbeacloudbusiness.activity.my.main.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * 实名认证-（经销商）
 */

public class RealNameAuthenticationDistributorResponseModel extends BaseResponseModel {


    public CompanyidentifyinfoBean companyidentifyinfo;

    public static class CompanyidentifyinfoBean {
        public String id;
        public String companyname;
        public String companyaddress;
        public String identifystatusid;
        public String identifystatus;
        public String masterperson;
        public String masterpersoncardid;
        public String companylisencecode;
    }

}
