package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * Created by programmer on 2017/7/10.
 */

public class WithdrawDepositDateReponseModel extends BaseResponseModel {

    public WithdrawDepositDate data;

    public class WithdrawDepositDate {
        public List<TakeMoney> takemoneylist;
        public TakeMoneyTotleInfo takemoneytotleinfo;
    }

    public class TakeMoney {
        public String money;
        public String payeename;
        public String persontypeicon;
        public String personorcompany;
        public String takemoneyid;
        public String time;
    }

    public class TakeMoneyTotleInfo {
        public String totlemoney;
    }
}
