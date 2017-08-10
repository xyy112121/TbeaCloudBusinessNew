package com.example.programmer.tbeacloudbusiness.activity.distributor.rebateAccount.activity.scanCode.model;

import com.example.programmer.tbeacloudbusiness.http.BaseResponseModel;

import java.util.List;

/**
 * 扫码返利首页
 */

public class DBScanCodeMainResponseModel extends BaseResponseModel {
    public ScanCodeMainModel data;

    public class ScanCodeMainModel{
        public List<TakeMoneyRanking> takemoneyrankinglist;
        public TakeMoneyTotleInfo takemoneytotleinfo;
    }

    public class TakeMoneyRanking{
        public String electricianid;
        public String personjobtitle;
        public String personname;
        public String sequence;
        public String thumbpicture;
        public String totlemoney;
    }

    public class TakeMoneyTotleInfo{
        public String havepayed;
        public String needpay;
    }

}
