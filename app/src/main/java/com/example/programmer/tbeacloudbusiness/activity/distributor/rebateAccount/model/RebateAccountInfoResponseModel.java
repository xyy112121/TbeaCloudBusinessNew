package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 * Created by programmer on 2017/7/20.
 */

public class RebateAccountInfoResponseModel extends BaseResponseModel {
    public RebateAccountInfoModel data;

    public class RebateAccountInfoModel {
        public MyMoneyInfo mymoneyinfo;
        public FirstDistriButorinfo firstdistributorinfo;
    }

    public class MyMoneyInfo {
        public int currentmoney;
        public int canexchangemoney;

    }

    public class FirstDistriButorinfo {
        public String id;
        public String personorcompany;
        public String persontypeicon;
        public String thumbpicture;
        public String personname;
        public String companyname;

    }

}
