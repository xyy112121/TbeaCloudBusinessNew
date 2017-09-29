package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 提现历史列表
 */

public class WithdrawDepositDateHistoryListResponseModel extends BaseResponseModel {
    public  WithdrawDepositDateHistoryList data;

    //{"data":{"takemoneylist":[{"dataid":"rlvaep1506584837tykonl","time":"2017-09-28 15:56:13","money":10},{"dataid":"vmfcpy1506584895opnyxt","time":"","money":10},{"dataid":"wxsjqe1506584906yqefxx","time":"","money":10},{"dataid":"keejxe1506584943sutayk","time":"","money":10}],"payeeinfo":{"personorcompany":"1","payeeid":"vbspba1435310500tslnst","thumbpicture":"http:\/\/121.42.193.154:6696\/public\/static\/imagesforapp\/defaultpersonthumb.png","personname":"\u5206\u9500\u5546\u6c34\u7535\u5de501","companyname":"asfaf","persontypeicon":"http:\/\/121.42.193.154:6696\/public\/static\/imagesforapp\/ut_electrician.png","totlemoney":"40.00"}},"success":"true","msg":"\u67e5\u8be2\u6570\u636e\u6210\u529f"}
    public class WithdrawDepositDateHistoryList {
        public PayeeInfo payeeinfo;
        public List<TakeMoneyRanking> takemoneylist;
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
