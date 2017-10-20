package com.example.programmer.tbeacloudbusiness.activity.franchisee.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 提现数据列表
 */

public class WithdrawDepositDateResponseModel extends BaseResponseModel {

    public WithdrawDepositDate data;
//{"data":{"takemoneytotleinfo":{"totlemoney":"2310.00"},"takemoneylist":[{"takemoneyid":"ijhkeqw1461289970usv0997","personorcompany":"1","persontypeicon":"http:\/\/121.42.193.154:6696\/public\/static\/imagesforapp\/electrician","payeeid":"zvkeqw1461289970usvwi2","thumbpicture":"http:\/\/121.42.193.154:6696\/public\/static\/imagesforapp\/defaultpersonthumb.png","personname":"\u5b89\u65ed","companyname":"\u5fb7\u9633\u5e02 \u65cc\u9633\u533a","time":"2017-05-05","money":"385.00"},{"takemoneyid":"zvkeqw1461289970usvwi3","personorcompany":"1","persontypeicon":"http:\/\/121.42.193.154:6696\/public\/static\/imagesforapp\/electrician","payeeid":"45vkeqw1461289970usviu","thumbpicture":"http:\/\/121.42.193.154:6696\/public\/static\/imagesforapp\/defaultpersonthumb.png","personname":"\u738b\u5b89\u5728","companyname":"\u5fb7\u9633\u5e02 \u65cc\u9633\u533a","time":"2017-05-01","money":"350.00"}]},"success":"true","msg":"\u67e5\u8be2\u6570\u636e\u6210\u529f"}
    public class WithdrawDepositDate {
        public List<TakeMoney> takemoneylist;
        public TakeMoneyTotleInfo takemoneytotleinfo;
        public TakeMoneyTotleInfo totlemoneyinfo;
    }

    public class TakeMoney {
        public String companyname;
        public String payeeid;
        public String personname;
        public String thumbpicture;
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
