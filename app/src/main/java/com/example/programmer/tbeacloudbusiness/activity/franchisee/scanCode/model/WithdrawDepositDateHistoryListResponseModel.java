package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 提现历史列表
 */

public class WithdrawDepositDateHistoryListResponseModel extends BaseResponseModel {
    public  WithdrawDepositDateHistoryList data;

    public class WithdrawDepositDateHistoryList {
        public PayeeInfo payeeinfo;
        public List<TakeMoneyRanking> takemoneyrankinglist;
    }

    public class PayeeInfo {
        public String companyname;
        public String payeeid;
        public String personname;
        public String personorcompany;
        public String persontypeicon;
        public String thumbpicture;
        public String totlemoney;
    }

    public class TakeMoneyRanking {
        public String dataid;
        public String money;
        public String time;
    }
}
