package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

/**
 *提现数据详情返回数据
 */

public class WithdrawDepositDateInfoResponseModel extends BaseResponseModel {

    public WithdrawDepositDateInfo data;

    public class WithdrawDepositDateInfo {
        public OwnerInfo ownerinfo;
        public PayeeInfo payeeinfo;
        public PayMoneyInfo paymoneyinfo;
        public TakeMoneyCodeInfo takemoneycodeinfo;
    }

    public class OwnerInfo {
        public String companyname;
        public String ownerid;
        public String personname;
        public String personorcompany;
        public String persontypeicon;
        public String thumbpicture;
    }

    public class PayeeInfo {
        public String companyname;
        public String payeeid;
        public String personname;
        public String personorcompany;
        public String persontypeicon;
        public String thumbpicture;
    }

    public class PayMoneyInfo {
        public String companyname;
        public String paymoneycompanyid;
        public String paymoneyid;
        public String payplace;
        public String paytime;
        public String personname;
        public String personorcompany;
        public String persontypeicon;
        public String thumbpicture;
    }

    public class TakeMoneyCodeInfo {
        public String generatecodetime;
        public String money;
        public String takemoneycode;
    }
}
